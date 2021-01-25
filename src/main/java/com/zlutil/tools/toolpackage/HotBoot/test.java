package com.zlutil.tools.toolpackage.HotBoot;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by fangjian on 14-3-20.
 */
public class test {
    public static void main(String[] args) throws IOException {
        String static_root = "C:\\Users\\12733\\Desktop\\HotBoot";
        Object json = dir2json(static_root);
        System.out.println(JSON.toJSONString(json));
    }

    public static Object dir2json(String dir_path) throws IOException {
        HashMap<String, Object> dirMap = new HashMap<String, Object>();
        File root = new File(dir_path);
        dir2map(root, dirMap);
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.defaultPrettyPrintingWriter().writeValueAsString(dirMap.get(root.getName()));*/
        return dirMap;
    }

    public static boolean shouldSkip(String filename) {
        return filename.startsWith(".");
    }

    /**
     * @param node   文件节点
     * @param dirMap 表示文件所在目录的map
     */
    public static void dir2map(File node, HashMap<String, Object> dirMap) throws IOException {
        //跳过隐藏文件等
        if (shouldSkip(node.getName())) {
            return;
        }
        //是文件，保存文件名和最后修改时间戳
        if (node.isFile()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(node.lastModified());


            HotBootDTO hotBootDTO = new HotBootDTO();
            hotBootDTO.setFilePath(node.getPath());
            hotBootDTO.setFileName(node.getName());
            hotBootDTO.setModifyTime(simpleDateFormat.format(date));
            hotBootDTO.setFileExtension(FileUtil.getSuffix(node));
            hotBootDTO.setType("file");
            hotBootDTO.setFileSize(String.valueOf(node.length()));
            hotBootDTO.setNodeLength("String.valueOf(dirMap.keySet().size())");

            FileInputStream fileInputStream = new FileInputStream(node);
            hotBootDTO.setFileMd5(Arrays.toString(DigestUtils.md5(fileInputStream)));
            fileInputStream.close();

            dirMap.put(node.getName(), hotBootDTO);
            //System.out.println(JSON.toJSONString(dirMap));
        }
        //是目录，建立下一层map，并填充
        if (node.isDirectory()) {
            HashMap<String, Object> subDir = new HashMap<String, Object>();
            dirMap.put(node.getName(), subDir);
            for (String filename : node.list()) {
                dir2map(new File(node, filename), subDir);//填充
            }
        }
    }
}