package com.zlutil.tools.toolpackage.freemarker;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

/**
 * @author wsw
 * 2018/6/23 - 10:05
 * word工具
 */
@Slf4j
public class WordUtils {

    /**
     * 生成 DOC 格式报告
     * @param dataMap 数据集
     * @param templateName 模板名字
     * @param path 存储位置
     */
    public static void createReport(Map dataMap, String templateName, String path) {
        try {
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(WordUtils.class, "/lib/");
            Template template = configuration.getTemplate(templateName);
            File file = new File(path);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath    word目录
     * @param xmlFilePath 生成xml存放路径
     */
    public static void wordToXml(String filePath, String xmlFilePath) {
        try {
            //启动word
            ActiveXComponent app = new ActiveXComponent("Word.Application");
            //为false时设置word不可见，为true时是可见要不然看不到Word打打开文件的过程
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();

            //打开编辑器
            Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[]{legalPath(filePath), new Variant(false), new Variant(true)}, new int[1]).toDispatch(); //打开word文档
            //xml文件格式宏11
            Dispatch.call(doc, "SaveAs", legalPath(xmlFilePath), 11);
            Dispatch.call(doc, "Close", false);
            app.invoke("Quit", 0);
            log.info("---word文档转换成功！---");
        } catch (Exception e) {
            log.warn("wordToXml失败！");
            e.printStackTrace();
        }
    }

    /**
     * 路径要合法  “/”
     * @param path  “D:/doc/DbTable.doc”
     */
    private static String legalPath(String path) {
        return path.replace('\\', '/');
    }


    public static void main(String[] args) {

        //word转xml
        wordToXml("C:\\Users\\12733\\Desktop\\DbTable.doc", "C:\\Users\\12733\\Desktop\\a.xml");

        //修改xml文件后缀为ftl用做 freemarker模板
        /*File file=new File("D:/doc/test.xml");
        file.renameTo(new File("D:/doc/wordReportTemplate.ftl"));*/
    }
}
