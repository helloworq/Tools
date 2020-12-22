package com.zlutil.tools.toolpackage.JavaBasic;

import cn.hutool.core.io.FileUtil;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.RW_File;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class EnumTest {
    public enum direction {WEST, EAST, SOUTH, NORTH}

    /**
     * 格式相对固定，只需要在id后加上primary key
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\12733\\Desktop\\SQL\\";
/*        File file = new File(filePath);
        Arrays.stream(file.list()).forEach(d->{
            try {
                new EnumTest().extractTable(filePath, 0, d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
        new EnumTest().handleSql(filePath);
    }

    /**
     * select count(*) into countTab from user_tables where table_name = upper('PAS_INDICATOR_SYSTEM');
     *   if countTab = 0
     *     then
     *       execute immediate
     *         'create table PAS_INDICATOR_SYSTEM
     *         (id                   NUMBER primary key,
     *         createuser           VARCHAR2(50),
     *         indictorrelationdata CLOB)';
     *   end if;
     * @param filePath
     */
    public void handleSql(String filePath){
        File file=new File(filePath);
        Arrays.stream(file.listFiles()).forEach(fileStream->{
            try {
                String data=RW_File.read_txt(fileStream.getPath());
                extractData(fileStream,data,0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void extractData(File file,String data,int begin) throws IOException {
        //int explainStart=data.indexOf("--",begin);
        int explainEnd=data.indexOf("create",begin);
        int tablenameStart=data.indexOf("table",begin)+5;
        int tablenameEnd=data.indexOf("(",tablenameStart);
        int tableEnd=data.indexOf(";",begin);

        //String explain=data.substring(explainStart,explainEnd);
        String tableName=data.substring(tablenameStart,tablenameEnd);
        String tableBody=data.substring(explainEnd,tableEnd);
        //开始拼接SQL
        String SQL="select count(*) into countTab from user_tables where table_name = upper('"+tableName.replace("\n","")+"');\nif countTab = 0\n" +
                "    then\n" +
                "    execute immediate '"+tableBody+"';\n" +
                "    end if;\n";
        System.out.println(SQL);
        RW_File.write_txt(file.getParent()+File.separator + file.getName().substring(0,file.getName().indexOf("."))+ "new.sql", SQL + "\n", true);
        if (data.indexOf("create table", tableEnd) != -1) {
            extractData(file,data,tableEnd+1);
        }
    }

    public void extractTable(String filePath, int begin, String fileName) throws IOException {
        //先提取出每一张表
        //String fileName = "bms";
        //String sumData = RW_File.read_txt(filePath + "bms\\bms.sql");
        String sumData = RW_File.read_txt(filePath + fileName);
        int start = sumData.indexOf("create table", begin);
        int end = sumData.indexOf(";", start);
        String prev = sumData.substring(start, end) + ";";
        //给每一段表加上primary key
        //定位not null位置
        int position = prev.indexOf("not null");
        StringBuffer stringBuffer = new StringBuffer().append(prev);
        stringBuffer.insert(position + "not null".length(), "\n                          primary key");
        System.out.println(stringBuffer.toString());
        RW_File.write_txt(filePath +  fileName + "new.sql", stringBuffer.toString() + "\n", true);
        if (sumData.indexOf("create table", end) != -1) {
            extractTable(filePath, end,fileName);
        }
    }
}
