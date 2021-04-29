package com.zlutil.tools.toolpackage.JavaBasic;

import com.zlutil.tools.toolpackage.JavaBasic.MyIO.RW_File;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class EnumTest {

    public static String offlinePermissionConfigurationFileV2 = "c://移动一张图//离线权限配置文件V2//用户离线配置文件统计信息.txt";

    /**
     * 格式相对固定，只需要在id后加上primary key
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("sda", "das");
        for (Map.Entry m:map.entrySet()){
            m.getKey();
        }
    }

    public String result(String username) throws Exception {

        //在配置文件的统计目录创建一个txt文件记录每个用户
        int position = offlinePermissionConfigurationFileV2.lastIndexOf("//");
        String recordFilePath = offlinePermissionConfigurationFileV2.substring(0, position);
        String recordFileFullPath = recordFilePath + File.separator + "用户离线配置文件统计信息" + ".txt";
        File file = new File(recordFileFullPath);
        if (!file.exists()) {
            file.createNewFile();
            //第一次创建将
            return "true";
        }
        //获取文件内的指定用户最近一次修改文件的时间
        String lastModfiy = cn.hutool.core.io.FileUtil.readUtf8Lines(file)
                .stream()
                .filter(ele -> getOriginName(ele).contains(username))
                .map(ele -> ele.split("-")[1])
                .findFirst().orElse(null);

        if (Objects.isNull(lastModfiy)) {
            //为空代表还未存储或者用户名非法
            //从当前目录获取指定用户的配置文件信息
            File[] files = file.getParentFile().listFiles();

            if (Objects.nonNull(files)) {
                File filePrev = Arrays.stream(files)
                        .filter(ele -> getOriginName(ele.getName()).equals(username))
                        .findFirst().orElse(null);
                if (Objects.isNull(filePrev)) {
                    return "当前用户名不存在离线配置文件";
                }
                //写入修改时间
                cn.hutool.core.io.FileUtil.writeLines(
                        Collections.singletonList(username + "-" + filePrev.lastModified()), file, StandardCharsets.UTF_8, true);
            }
            return "true";
        }
        //获取文件最后修改时间与保存的信息进行比较
        File[] files = file.getParentFile().listFiles();

        if (Objects.nonNull(files)) {
            File filePrev = Arrays.stream(files)
                    .filter(ele -> getOriginName(ele.getName()).equals(username))
                    .findFirst().orElse(null);
            if (Objects.isNull(filePrev)) {
                return "当前用户名不存在离线配置文件";
            }
            //更新修改时间
            List<String> originStringList = cn.hutool.core.io.FileUtil.readUtf8Lines(file);
            //获取文件修改时间再比较
            String originTime = originStringList
                    .stream().filter(ele -> getOriginName(ele).equals(username))
                    .findFirst().orElse(null);

            if (Long.parseLong(originTime.split("-")[1]) >= filePrev.lastModified()) {
                return "false";
            }
            //更新
            System.out.println(filePrev.lastModified());
            List<String> handle = originStringList.stream().map(ele -> {
                if (getOriginName(ele).equals(username)) {
                    return (username + "-" + filePrev.lastModified());
                }
                return ele;
            }).collect(Collectors.toList());
            cn.hutool.core.io.FileUtil.writeLines(handle, file, StandardCharsets.UTF_8, false);
        }
        return "true";
    }

    private String getOriginName(String name) {
        //返回分隔符前的全名
        return name.split("-")[0];
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
