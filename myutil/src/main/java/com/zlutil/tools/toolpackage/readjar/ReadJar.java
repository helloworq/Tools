package com.zlutil.tools.toolpackage.readjar;

import cn.hutool.poi.word.WordUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Test;

import java.io.*;
import java.util.Map;

@Slf4j
public class ReadJar {
    public static void main(String[] args) {
        InputStream in= Test.class.getClass().getResourceAsStream("/idcheck-file.properties");//读jar包根目录下的idcheck-file.properties文件
    }
    /**
     * 生成word文件
     * @param dataMap word中需要展示的动态数据，用map集合来保存
     * @param templatePath word模板路径，例如：/templates  必须在资源文件目录下
     * @param templateName word模板名称，例如：test.ftl
     * @param filePath 文件生成的目标路径，例如：D:/wordFile/
     * @param fileName 生成的文件名称，例如：test.doc
     */
    @SuppressWarnings("unchecked")
    public static void createWord(Map dataMap, String templatePath, String templateName, String filePath, String fileName) {
        try {
            //创建配置实例
            Configuration configuration = new Configuration();
            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            //ftl模板文件
            configuration.setClassForTemplateLoading(WordUtil.class, "/" + templatePath);
            //获取模板
            Template template = configuration.getTemplate(templateName);
            //输出文件
            File outFile = new File(filePath + File.separator + fileName);
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            //将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            //生成文件
            template.process(dataMap, out);
            //关闭流
            out.flush();
            out.close();
            log.debug("ftl模板生成word成功，文档路径={}", outFile);
        } catch (Exception e) {
            log.warn("ftl模板生成word失败，模板名称=", templateName);
            e.printStackTrace();
        }
    }

}
