package com.zlutil.tools.toolpackage.poi;/*
package com.zlutil.tools.toolpackage.poi;


import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.interfaces.ITable;

import java.io.File;
import java.util.UUID;

public class Runner {
    public static void main(String[] args) {
        //根据目标文件夹克隆文件夹结构
        String path = "C:\\Users\\12733\\Desktop\\handle";
        String pathName = "clone";
        batchDeleteHeader(path, path, pathName);
        //cloneFolderStructure(path,path,newPlace);
        //创建实例


    }

    public static void batchDeleteHeader(
比对用
String filePath, String newPath, String pathName) {
        File file = new File(newPath);
        if (file.isDirectory()) {
            for (File fileObj : file.listFiles()) {
                if (fileObj.isDirectory()) {
                    batchDeleteHeader(filePath, fileObj.getAbsolutePath(), pathName);
                } else {
                    System.out.println("完成一个");
                    deleteHeader(fileObj.getAbsolutePath(),
                            filePath + File.separator + pathName + fileObj.getAbsolutePath().substring(filePath.length()));
                }
            }
        } else {
            System.out.println("完成一个");
            deleteHeader(file.getAbsolutePath(),
                    filePath + File.separator + pathName + file.getAbsolutePath().substring(filePath.length()));
        }
    }

    public static void deleteHeader(String originPath, String newPath) {
        //加载测试文档
        Document doc = new Document();
        doc.loadFromFile(originPath);
        //获取第一节
        Section sec = doc.getSections().get(0);
        //删除页眉
        sec.getHeadersFooters().getHeader().getChildObjects().clear();
        //删除页脚
        sec.getHeadersFooters().getFooter().getChildObjects().clear();
        //获取表格
        if (sec.getTables().getCount() > 0) {
            ITable table = sec.getTables().get(0);
            //删除表格
            sec.getTables().remove(table);
        }
        //保存文档
        File file = new File(newPath);
        if (!file.exists())
            file.mkdirs();
        doc.saveToFile(newPath, FileFormat.Docx_2013);
        doc.dispose();
    }

    public static void deleteHeader(String originPath) {
        //加载测试文档
        Document doc = new Document();
        doc.loadFromFile(originPath);
        //获取第一节
        Section sec = doc.getSections().get(0);
        //删除页眉
        sec.getHeadersFooters().getHeader().getChildObjects().clear();
        //删除页脚
        sec.getHeadersFooters().getFooter().getChildObjects().clear();
        //获取表格
        ITable table = sec.getTables().get(0);
        //删除表格
        sec.getTables().remove(table);
        //保存文档
        doc.saveToFile(UUID.randomUUID().toString() + ".docx", FileFormat.Docx_2013);
        doc.dispose();
    }
}
*/
