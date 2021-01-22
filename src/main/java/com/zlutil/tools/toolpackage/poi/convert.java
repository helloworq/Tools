package com.zlutil.tools.toolpackage.poi;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
public class convert {

    public static void main(String[] args) {
        HashMap a=new HashMap();

        //excel2PDF("C:\\Users\\12733\\Desktop\\aaa.xlsx","C:\\Users\\12733\\Desktop\\bbb.pdf");
    }
    
    /**
     * 转换pdf
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    public static boolean excel2PDF(String inputFile, String pdfFile) {
        try {
            long startTime = System.currentTimeMillis();
            ActiveXComponent app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
            Dispatch.call(excel, "ExportAsFixedFormat", 0, pdfFile);
            Dispatch.call(excel, "Close", false);
            app.invoke("Quit");
            long endTime = System.currentTimeMillis();
            log.info("xls文件转换为pdf格式 耗时/毫秒:" + (endTime - startTime));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

    }
}
