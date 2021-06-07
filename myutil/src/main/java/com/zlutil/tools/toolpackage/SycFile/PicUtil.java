package com.zlutil.tools.toolpackage.SycFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zlutil.tools.toolpackage.JavaBasic.NetTools.SimpleClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PicUtil {

    //private static final String PIC_FILE_PATH = "D:\\picBed\\Sync\\Pic\\";
    private static final String PIC_FILE_PATH = "D:\\picBed\\Sync\\PicOrigin\\";

    private static final String PIC_SYNC_PATH = "D:\\picBed\\Sync\\PicOrigin\\";

    private ExecutorService executors = Executors.newCachedThreadPool();

    private Map<String, Map<String, String>> cachedPool = new ConcurrentHashMap<>();

    private static final Integer poolSize = 1000;

    public void getPicUrl() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("C:\\Users\\12733\\Desktop\\s.txt"));

        long start = System.currentTimeMillis();
        list.forEach(this::getPicRelativePath);
        System.out.print(System.currentTimeMillis() - start);

        System.out.print("    ");

        long start1 = System.currentTimeMillis();
        list.forEach(this::getPicRelativePathWithoutCache);
        System.out.println(System.currentTimeMillis() - start1);
    }

    public String getPicRelativePathWithoutCache(String md5) {

        Integer saveLevel = saveLevel(md5);
        if (saveLevel != -1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i <= saveLevel; i++) {
                stringBuilder.append(md5, 0, i).append("/");
            }
            String res = stringBuilder.append(md5).append(".jpg").toString();
            if (!new File(PIC_FILE_PATH + res).exists()) {
                return "";
            }
            return res;
        }
        return "";
    }

    /**
     * 根据层级获取路径
     *
     * @param md5
     * @return
     */
    public String getPicRelativePath(String md5) {

        //System.out.println("池内数据量: " + cachedPool.size());
        //检测缓存池里是否有数据
        //System.out.println("cachedPool.size(): " + cachedPool.size());
        Map<String, String> map = cachedPool.get(md5);
        if (Objects.nonNull(map)) {
            //缓存内的times自增
            executors.execute(() -> {
                Integer times = Integer.parseInt(map.get("times"));
                times++;
                map.put("times", String.valueOf(times));
                cachedPool.put(md5, map);
                checkPoolSize(poolSize);
            });
            return map.get("url");
        }

        Integer saveLevel = saveLevel(md5);
        saveLevel--;
        if (saveLevel != -1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i <= saveLevel; i++) {
                stringBuilder.append(md5, 0, i).append("/");
            }
            String res = stringBuilder.append(md5).append(".jpg").toString();
            if (!new File(PIC_FILE_PATH + res).exists()) {
                return "";
            }
            //结果写入缓存
            executors.execute(() -> {
                Map<String, String> mapPrev = new HashMap<>();
                mapPrev.put("url", res);
                mapPrev.put("times", "1");
                cachedPool.put(md5, mapPrev);
            });
            return res;
        }
        return "";
    }

    /**
     * 检测缓存池大]
     */
    public void checkPoolSize(int poolSize) {
        //当缓存池大于规定大小，则去除部分元素
        if (cachedPool.size() > poolSize) {
            cachedPool = cachedPool.entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getValue().get("times")))
                    .skip((long) (poolSize * 0.3))
                    .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
            System.out.println("当前池内数据量超过限制,剔除部分数据,当前池内数据量: " + cachedPool.size());
        }
    }

    /**
     * 保存图片
     *
     * @param file
     * @throws IOException
     */
    public String save(File file) throws IOException {
        //获取后缀
        String suffix = getFileSuffix(file.getName());
        //获取字节
        FileInputStream fileInputStream = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(fileInputStream);

        savePic(md5, suffix, Files.readAllBytes(Paths.get(file.getPath())));

        fileInputStream.close();
        return md5;
    }

    public String save(MultipartFile file) throws IOException {
        //获取后缀
        String contentType = getFileSuffix(file.getOriginalFilename());
        //获取存储时的必要数据
        String md5 = DigestUtils.md5Hex(file.getBytes());
        savePic(md5, contentType, file.getBytes());
        System.out.println(md5);
        return md5;
    }

    public static void inputStreamWriteToOutputStream(InputStream input, OutputStream output) throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        while ((index = input.read(bytes)) != -1) {
            output.write(bytes, 0, index);
            output.flush();
        }
        input.close();
        output.close();
    }

    /**
     * 根据文件md5前缀将文件分类整理
     *
     * @param md5         文件md5值
     * @param contentType 文件类型
     * @param bytes       数据
     * @throws IOException
     */
    private void savePic(String md5, String contentType, byte[] bytes) throws IOException {
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

    //监测当前文件md5在第几个层级
    private Integer saveLevel(String md5) {

        StringBuilder prefix = new StringBuilder();

        for (int i = 1; i <= md5.length(); i++) {
            prefix.append(md5, 0, i).append("\\");
            if (!new File(PIC_FILE_PATH + prefix).exists()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 同步文件-分两种情况
     * 客户端同步远程文件: 比较两处文件，获取自己缺少的文件
     * 服务端同步本地文件:
     */
    public static void syncFile(String remotePath, String localPath) throws IOException {
        Files.walk(Paths.get(remotePath))
                .forEach(e -> {
                    //获取远程目录的绝对和相对路径,使用相对路径进行处理屏蔽路径不同造成的问题
                    String remoteAbsloutPath = e.toString();
                    String relativePath = remoteAbsloutPath.replace(remotePath, "");
                    if (!relativePath.equals("")) {
                        //获取本地目录的绝对路径
                        String localAbsloustPath = localPath + relativePath;
                        File file = new File(localAbsloustPath);
                        if (isDirectory(localAbsloustPath)) {
                            file.mkdirs();
                        } else {
                            if (!file.exists()) {
                                file.getParentFile().mkdirs();
                                try {
                                    file.createNewFile();
                                    Files.copy(
                                            Paths.get(remoteAbsloutPath),
                                            Paths.get(localAbsloustPath),
                                            StandardCopyOption.REPLACE_EXISTING//覆盖文件
                                    );
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    public static boolean isDirectory(String path) {
        if (Objects.isNull(path) || path.equals("")) {
            return false;
        }
        if (path.contains(".")) {
            return false;
        }
        return true;
    }

    private String getFileSuffix(String path) {
        int splitPosition = path.lastIndexOf(".");
        return path.substring(splitPosition + 1);
    }

    private boolean isCurrentImgSupport(String contentType) {
        List<String> list = Arrays.asList("jpg", "jpeg", "gif", "png", "bmp");
        return list.stream().anyMatch(contentType::contains);
    }

    private String getContentType(Path origin) throws IOException {
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
    private String getFileMd5HexString(String filePath) throws IOException {

        if (Objects.isNull(filePath) || filePath.equals("") || Files.isDirectory(Paths.get(filePath))) {
            return null;
        }

        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

        if (fileBytes.length == 0) {
            return null;
        }

        return DigestUtils.md5Hex(fileBytes);
    }

    /**
     * 全盘同步
     */
//    public static void main(String[] args) throws IOException {
//        //syncRemoteFile("s");
//        downloadPic("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1418315404,2308746069&fm=26&gp=0.jpg");
//    }
    public void syncRemoteFile(String remote) throws IOException, URISyntaxException {
        List<String> localFileList = Files.walk(Paths.get(PIC_FILE_PATH))
                .filter(e -> Files.isRegularFile(e))
                .map(e -> {
                    int position = e.toString().lastIndexOf("\\") + 1;
                    return e.toString().substring(position).replace(".jpg", "");
                }).collect(Collectors.toList());

        SimpleClient simpleClient = new SimpleClient();
        URI uri = new URIBuilder(remote)
                .setParameter("picIds", JSON.toJSONString(localFileList)).build();
        String res = simpleClient.sendPost(uri);//返回图片url

        //开始下载
        JSONArray jsonArray = JSON.parseObject(res).getJSONArray("data");

        List<String> stringList = jsonArray.toJavaList(String.class);
        stringList.forEach(e -> {
            Path path = downloadPic(e);
            try {
                save(path.toFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public Path downloadPic(String url) {
        URL uri;
        Path path = null;
        try {
            uri = new URL(url);
            DataInputStream dataInputStream = new DataInputStream(uri.openStream());
            //将下载的文件的名字截取设置为文件名
            path = Files.createTempFile(Paths.get("C:\\Users\\12733\\Desktop\\新建文件夹"), "tmp", ".jpg");
            OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.WRITE);

            inputStreamWriteToOutputStream(dataInputStream, outputStream);
            //将下载信息写入1.txt
            System.out.println("---写入成功！" + path.toFile().getPath());
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    //TODO 增量同步

}
