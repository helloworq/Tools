package com.zlutil.tools.toolpackage.SycFile;

import com.mongodb.client.gridfs.GridFSBucket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PicServerImpl implements PicService {

    public static final String PIC_FILE_PATH = "D:\\picBed\\Sync\\Pic\\";
    public static final String PIC_FILE_PATH_Ori = "C:\\Users\\12733\\Pictures\\Saved Pictures";

    public static final String prefix = "http://localhost:8888/upload/";

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    public static void main(String[] args) throws IOException {

//        long start = System.currentTimeMillis();
//        Arrays.stream(pic.split("\n")).forEach(e -> new PicServerImpl().getPicRelativePath(e));
//        System.out.println(System.currentTimeMillis() - start);

//        Files.walk(Paths.get("D:\\picBed\\Sync\\Pic"))
//                .filter(e->Files.isRegularFile(e))
//                .map(e->e.getFileName().toString().split("\\.")[0])
//                .forEach(System.out::println);
        Files.walk(Paths.get(PIC_FILE_PATH_Ori))
                .filter(e -> Files.isRegularFile(e))
                .forEach(e -> {
                    try {
                        String md5 = SyncFile.getFileMd5HexString(e.toString());
                        System.out.println(md5);
                        new PicServerImpl().savePic(md5, "jpg", Files.readAllBytes(e));
                    } catch (IOException ex) {
                        System.out.println(e);
                        ex.printStackTrace();
                    }
                });
    }

    public String getPicUrl(String md5) {
        return null;
    }

    /**
     * 根据层级获取路径
     *
     * @param md5
     * @return
     */
    @Override
    public String getPicRelativePath(String md5) {
        Integer saveLevel = saveLevel(md5);
        if (saveLevel != -1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i <= saveLevel; i++) {
                stringBuilder.append(md5, 0, i).append("/");
            }
            return stringBuilder.append(md5).append(".jpg").toString();
        }
        return "";
    }

    /**
     * 根据文件md5前缀将文件分类整理
     *
     * @param md5         文件md5值
     * @param contentType 文件类型
     * @param bytes       数据
     * @throws IOException
     */
    public void savePic(String md5, String contentType, byte[] bytes) throws IOException {
        if (!isCurrentImgSupport(contentType)) {
            return;
        }
        //获取存储层级
        int level = saveLevel(md5);
        //例如md5是123f2t422，level是3，则需要生成的路径是 1/12/123/
        //获取到level之后先检测，当前level目录是否有文件夹，有的话则创建文件夹将此文件放入，没有的话
        //监测此层级数据量，超过限制则触发创建文件夹机制，没有则将数据放入
        String prefix = md5.substring(0, level);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= level; i++) {
            stringBuilder.append(prefix, 0, i).append("/");
        }
        Path path = Paths.get(PIC_FILE_PATH + stringBuilder.toString());
        //检测是否有文件夹
        int directorySize = (int) Files.list(path.getParent()).filter(e -> Files.isDirectory(e)).count();
        if (directorySize == 0) {
            //没有文件夹情况
            //先将文件直接写入
            Files.write(Paths.get(path.getParent() + File.separator + md5 + "." + contentType), bytes, StandardOpenOption.CREATE);
            int fileSize = (int) Files.list(path.getParent()).filter(e -> Files.isRegularFile(e)).count();
            //检测当前层级的文件数量是否超过限制
            if (fileSize > 2) {
                List<String> list = new ArrayList<>();//储存已创建目录
                //文件数量超过限制情况,超过限制则创建文件夹
                Files.list(path.getParent()).forEach(e -> {
                    //根据已存在文件按需创建文件夹
                    String dirPathName = e.getFileName().toString().substring(0, level);
                    if (!list.contains(dirPathName)) {
                        try {
                            Files.createDirectories(Paths.get(path.getParent() + File.separator + dirPathName));
                            list.add(dirPathName);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        Files.move(e, Paths.get(path.getParent() + File.separator + dirPathName).resolve(e.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        //有文件夹情况
        //检测是否有此level文件夹
        else {
            int specficSize = (int) Files.list(path.getParent())
                    .filter(e -> e.toFile().isDirectory() && e.startsWith(md5.substring(0, level)))
                    .count();
            if (specficSize != 0) {
                Files.write(Paths.get(path.getParent() + File.separator + md5 + "." + contentType), bytes);
            } else {
                Files.createDirectories(path);
                Files.write(Paths.get(path + File.separator + md5 + "." + contentType), bytes);
            }
        }
    }

    public void save(File file) throws IOException {
        //获取后缀
        String suffix = getFileSuffix(file.getName());
        //获取字节
        Path path = Files.createTempFile(Paths.get("C:\\Users\\12733\\Desktop\\新建文件夹"), "temp", suffix);
        byte[] fileBytes = Files.readAllBytes(path);
        Files.write(path, fileBytes, StandardOpenOption.WRITE);
        //获取存储时的必要数据
        String contentType = getContentType(path);
        String md5 = getFileMd5HexString(path.toString());

        savePic(md5, contentType, fileBytes);
    }

    //监测当前文件md5在第几个层级
    private static Integer saveLevel(String md5) {

        StringBuilder prefix = new StringBuilder();

        for (int i = 1; i <= md5.length(); i++) {
            prefix.append(md5, 0, i).append("\\");
            if (!new File(PIC_FILE_PATH + prefix).exists()) {
                return i;
            }
        }
        return -1;
    }

    private static String getFileSuffix(String path) {
        int splitPosition = path.lastIndexOf(".");
        return path.substring(splitPosition + 1);
    }

    private static boolean isCurrentImgSupport(String contentType) {
        List<String> list = Arrays.asList("jpg", "jpeg", "gif", "png", "bmp");
        return list.stream().anyMatch(contentType::contains);
    }

    public static String getContentType(Path origin) throws IOException {
        String path = Files.probeContentType(origin);
        int splitPosition = path.lastIndexOf("/");
        return "." + path.substring(splitPosition + 1);
    }

    /**
     * 获取文件md5
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileMd5HexString(String filePath) throws IOException {

        if (Objects.isNull(filePath) || filePath.equals("") || Files.isDirectory(Paths.get(filePath))) {
            return null;
        }

        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

        if (fileBytes.length == 0) {
            return null;
        }

        return DigestUtils.md5Hex(fileBytes);
    }
}
