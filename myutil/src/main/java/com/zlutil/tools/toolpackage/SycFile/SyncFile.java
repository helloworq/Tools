package com.zlutil.tools.toolpackage.SycFile;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.list.TreeList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件同步
 * <p>
 * 文件夹下文件全部使用
 */
public class SyncFile {

    public static final String LOCAL_FILE_PATH = "D:\\picBed\\Sync\\Local";
    public static final String REMOTE_FILE_PATH = "D:\\picBed\\Sync\\Remote";
    public static final String PIC_FILE_PATH = "D:\\picBed\\Sync\\Pic";

    public static void main(String[] args) throws IOException {
        //syncFile(LOCAL_FILE_PATH, REMOTE_FILE_PATH);
        //syncFile(REMOTE_FILE_PATH, LOCAL_FILE_PATH);
        //filename2Md5Hex("D:\\picBed\\Sync\\Pic\\");
        //测试查找速度
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            Files.exists(Paths.get("D:\\picBed\\Sync\\Pic\\f3bf27af-8085-4849-b713-029e3338dd3e.jpeg"));
            System.out.println("处理时间: " + (System.currentTimeMillis() - start));
        }
        //将指定目录下的文件按首字母分类存入文件夹
        //分类规则 0-9-a-z
        //每集目录递增，数字排前
        //根据文件名存储，第一个层级取文件名第一位，依次类推
        //某一层级的图片超过指定数量时自动创建下一层级
        //检测到当前层级有文件夹时，再要存储图片时如无符合文件夹则需要创建对应文件夹

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

    /**
     * 将指定目录下的文件名修改为其md5值
     *
     * @param filePath
     */
    public static void filename2Md5Hex(String filePath) throws IOException {
        if (Objects.isNull(filePath) || filePath.equals("") || !Files.isDirectory(Paths.get(filePath))) {
            return;
        }
        //处理前先剔除重复文件
        //removeDupilcateFile(filePath);
        Files.walk(Paths.get(filePath))
                .filter(e -> Files.isRegularFile(Paths.get(String.valueOf(e))))
                .forEach(e -> {
                    try {
                        String fileMd5 = getFileMd5HexString(e.toString());
                        assert fileMd5 != null;
                        if (!e.toString().contains(fileMd5)) {
                            Files.move(
                                    e,
                                    Paths.get(e.getParent()
                                            + File.separator
                                            + UUID.randomUUID()
                                            + getContentType(e)
                                    ),
                                    StandardCopyOption.ATOMIC_MOVE
                            );
                            System.out.println("已处理一个文件");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("跳过一个文件");
                });
    }

    public static String getContentType(Path origin) throws IOException {
        String path = Files.probeContentType(origin);
        int splitPosition = path.lastIndexOf("/");
        return "." + path.substring(splitPosition + 1);
    }

    /**
     * 去除目录下重复文件
     */
    public static void removeDupilcateFile(String filePath) throws IOException {

        if (Objects.isNull(filePath) || filePath.equals("") || !Files.isDirectory(Paths.get(filePath))) {
            return;
        }
        List<String> fileList = new TreeList<>();
        Files.walk(Paths.get(filePath))
                .filter(e -> Files.isRegularFile(Paths.get(String.valueOf(e))))
                .forEach(e -> {
                    try {
                        String md5 = getFileMd5HexString(e.toFile().getPath());
                        if (!fileList.contains(md5)) {
                            fileList.add(md5);
                        } else {
                            e.toFile().delete();
                            System.out.println("已删除一个文件");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
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
