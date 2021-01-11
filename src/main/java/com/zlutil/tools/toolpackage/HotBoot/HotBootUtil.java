package com.zlutil.tools.toolpackage.HotBoot;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class HotBootUtil {
    @Autowired
    HotBootRepositry hotBootRepositry;

    /**
     * 文件夹创建暂时不做处理
     * 文件夹改变事件库貌似未实现也暂不做处理
     * 文件夹删除事件由于有文件事件故也不做处理
     *
     * 文件创建事件
     *
     * 检测到文件夹被改变时，前往数据库查找被改变文件夹影响的字段
     * @param file
     */
    public void fileOnCreateUtil(File file) throws IOException {
        HotBootEnity hotBootEnity = new HotBootEnity();
        hotBootEnity.setModifyTime("");
        hotBootEnity.setFileName(file.getName());
        hotBootEnity.setFilePath(file.getPath());
        FileInputStream fileInputStream=new FileInputStream(file);
        hotBootEnity.setFileMd5(Arrays.toString(DigestUtils.md5(fileInputStream)));
        fileInputStream.close();
        this.hotBootRepositry.save(hotBootEnity);
    }

    /**
     * 文件删除事件
     *
     * 检测到文件夹被删除时，前往数据库查找被改变文件夹影响的字段
     * @param filePath
     */
    public void fileOnDeleteUtil(String filePath) throws IOException {
        this.hotBootRepositry.deleteByFilePath(filePath);
    }

    /**
     * 文件修改事件
     *
     * 检测到文件夹被改变时，前往数据库查找被改变文件夹影响的字段
     * @param file
     */
    public void fileOnChangeUtil(File file) throws IOException {
        HotBootEnity hotBootEnity = new HotBootEnity();
        hotBootEnity.setModifyTime(LocalDateTime.now().toString());
        hotBootEnity.setFileName(file.getName());
        hotBootEnity.setFilePath(file.getPath());
        hotBootEnity.setFileMd5(Arrays.toString(DigestUtils.md5(new FileInputStream(file))));
        this.hotBootRepositry.save(hotBootEnity);
    }

    /**
     * 初始化监控状态
     * ###此方法只允许开始监控某文件夹时调用一次初始化全部数据入库###
     */
    public List<HotBootEnity> initMonitorStatus(String filePath) throws IOException {
        List<HotBootEnity> fileList = new ArrayList<>();
        File file = new File(filePath);
        listAllFiles(file, fileList);
        log.info("文件夹数据读取完毕");
        //System.out.println(JSON.toJSONString(fileList));//文件夹数据读取完成之后才进行入库，尽量避免数据污染
        fileList.stream().forEach(fileObj -> hotBootRepositry.save(fileObj));
        return fileList;
    }

    public static void main(String[] args) throws IOException {
        new HotBootUtil().getFileStructureTree("C:\\Users\\12733\\Desktop\\HotBoot");
    }
    /**
     * 初始化监控状态
     * ###此方法只允许开始监控某文件夹时调用一次初始化全部数据入库###
     */
    public Object getFileStructureTree(String filePath) throws IOException {
        JSONObject root=new JSONObject();
        File file=new File(filePath);
        //System.out.println(root);
        return "";
    }


    public void listAllFiles(File dir, List<HotBootEnity> fileList) throws IOException {
        if (null == dir || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            HotBootEnity hotBootEnity = new HotBootEnity();
            hotBootEnity.setModifyTime("");
            hotBootEnity.setFileName(dir.getName());
            hotBootEnity.setFilePath(dir.getPath());
            FileInputStream fileInputStream=new FileInputStream(dir);
            hotBootEnity.setFileMd5(Arrays.toString(DigestUtils.md5(fileInputStream)));
            fileInputStream.close();
            hotBootEnity.setSize(String.valueOf(dir.length()));
            hotBootEnity.setFileExtension(FileUtil.getSuffix(dir));
            fileList.add(hotBootEnity);
            return;
        }
        for (File file : dir.listFiles()) {
            listAllFiles(file, fileList);
        }
    }


    public Object dir2json(String dir_path) throws IOException {
        HashMap<String, Object> dirMap = new HashMap<String, Object>();
        File root = new File(dir_path);
        dir2map(root, dirMap);
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.defaultPrettyPrintingWriter().writeValueAsString(dirMap.get(root.getName()));*/
        return dirMap;
    }

    public boolean shouldSkip(String filename) {
        return filename.startsWith(".");
    }

    /**
     * @param node   文件节点
     * @param dirMap 表示文件所在目录的map
     */
    public void dir2map(File node, HashMap<String, Object> dirMap) throws IOException {
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
            hotBootDTO.setSize(String.valueOf(node.length()));
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
