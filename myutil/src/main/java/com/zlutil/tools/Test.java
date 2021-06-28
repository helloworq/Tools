package com.zlutil.tools;

import java.io.IOException;

public class Test {

    public <My> void get() {
        My a = null;
    }

    public static void main(String[] args) throws IOException {
        String fileName="产品目录导入模板20210430.xlsx";

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            System.out.println("上传文件格式不正确");
        }
    }
}
