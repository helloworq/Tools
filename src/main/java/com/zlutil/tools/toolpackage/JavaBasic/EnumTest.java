package com.zlutil.tools.toolpackage.JavaBasic;

import com.zlutil.tools.toolpackage.JavaBasic.MyIO.RW_File;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class EnumTest {

    /**
     * 格式相对固定，只需要在id后加上primary key
     *
     * @param args
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String legendPeople =
                "丁仪（正礼）,丁奉（承渊）,丁原（建阳）,丁谧（彦靖）,丁廙（敬礼）," +
                        "于禁（文则）,士孙瑞（君荣）,山涛（巨源）,卫瓘（伯玉）,马磾（翁叔）," +
                        "马良（季常）,马忠（德信）,马超（孟起）,马谡（幼常）,马腾（寿成）," +
                        "王允（子师）,王双（子全）,王平（子均）,王匡（公节）,王戎（睿冲）," +
                        "王观（伟台）,王甫（国山）,王连（文仪）,王沈（处道）,王肃（子雍）," +
                        "王修（叔治）,王浑（玄冲）,王路（文舒）,王颀（孔硕）,王祥（休徵）," +
                        "王朗（景兴）,王基（伯舆）,王谋（元泰）,王粲（仲宣）,王睿（土治）," +
                        "韦康（元将）,太史慈（子义）,毛玠（孝先）,公孙度（升济）,公孙瓒（伯圭）," +
                        "文钦（仲若）,文聘（仲业）,尹奉（次曾）,邓艾（土载）,邓芝（伯苗）," +
                        "邓止飏（玄茂）,孔伷（公绪）,孔昱（世元）,孔融（文举）,母丘甸（子邦）," +
                        "母丘俭（仲恭）,甘宁（兴霸）,左慈（元放）,卢植（子干）,申耽（义举）," +
                        "田丰（元皓）,田畴（子泰）,田豫（国让）,史涣（公刘）,乐进（文谦）";
        Arrays.stream(legendPeople.split(",")).forEach(System.out::println);

    }

//    0
//    0a a a a a a a a a a a a a
//
//
//        0a1a a a a a a a a a a a a
//
//            a1a2a a a a a a a a a a a

    /**
     * select count(*) into countTab from user_tables where table_name = upper('PAS_INDICATOR_SYSTEM');
     * if countTab = 0
     * then
     * execute immediate
     * 'create table PAS_INDICATOR_SYSTEM
     * (id                   NUMBER primary key,
     * createuser           VARCHAR2(50),
     * indictorrelationdata CLOB)';
     * end if;
     *
     * @param filePath
     */
    public void handleSql(String filePath) {
        File file = new File(filePath);
        Arrays.stream(file.listFiles()).forEach(fileStream -> {
            try {
                String data = RW_File.read_txt(fileStream.getPath());
                extractData(fileStream, data, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void extractData(File file, String data, int begin) throws IOException {
        //int explainStart=data.indexOf("--",begin);
        int explainEnd = data.indexOf("create", begin);
        int tablenameStart = data.indexOf("table", begin) + 5;
        int tablenameEnd = data.indexOf("(", tablenameStart);
        int tableEnd = data.indexOf(";", begin);

        //String explain=data.substring(explainStart,explainEnd);
        String tableName = data.substring(tablenameStart, tablenameEnd);
        String tableBody = data.substring(explainEnd, tableEnd);
        //开始拼接SQL
        String SQL = "select count(*) into countTab from user_tables where table_name = upper('" + tableName.replace("\n", "") + "');\nif countTab = 0\n" +
                "    then\n" +
                "    execute immediate '" + tableBody + "';\n" +
                "    end if;\n";
        System.out.println(SQL);
        RW_File.write_txt(file.getParent() + File.separator + file.getName().substring(0, file.getName().indexOf(".")) + "new.sql", SQL + "\n", true);
        if (data.indexOf("create table", tableEnd) != -1) {
            extractData(file, data, tableEnd + 1);
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
        RW_File.write_txt(filePath + fileName + "new.sql", stringBuffer.toString() + "\n", true);
        if (sumData.indexOf("create table", end) != -1) {
            extractTable(filePath, end, fileName);
        }
    }
}
