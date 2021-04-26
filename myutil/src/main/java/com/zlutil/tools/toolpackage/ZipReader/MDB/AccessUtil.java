package com.zlutil.tools.toolpackage.ZipReader.MDB;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author yangmin
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @date 2019/9/10 17:08
 * @email yangmin@dist.com.cn
 * @desc
 */
public class AccessUtil {

    public static Database getDatabase(File file) {
        try {
            return DatabaseBuilder.open(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<JSONObject> getTableData(Database database, String tableName, Map<String, String> fields) {
        Optional.ofNullable(database).orElseThrow(() -> new ArgumentException("database"));
        Optional.ofNullable(tableName).orElseThrow(() -> new ArgumentException("tableName"));
        Optional.ofNullable(fields).orElseThrow(() -> new ArgumentException("fields"));
        List<JSONObject> items = null;
        try {
            Table table = database.getTable(tableName);
            if (table != null && table.getRowCount() >= 1) {
                items = new ArrayList<>(table.getRowCount());
                // 读取数据
                for (Map row : table) {
                    JSONObject item = new JSONObject();
                    // 表格字段过滤
                    row.keySet().stream().forEach(k -> {
                        if (fields.keySet().contains(k)) {
                            item.put(fields.get(k), row.get(k));
                        }
                    });
                    if (fields.size() == item.size()) {
                        items.add(item);
                    } else {
                        List<String> fs = new ArrayList<>(fields.values());
                        item.keySet().forEach(key -> fs.remove(key));
                        throw new MessageException("表格【" + tableName + "】数据缺失【" + StringUtils.join(fs, ",") + "】信息");
                    }
                }
            } else {
                throw new MessageException("表格【" + tableName + "】数据缺失");
            }
            return items;
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessageException("表格【" + tableName + "】数据解析报错：" + e.getMessage());
        }
    }

    public static Object getTableData(String filePath) throws IOException {
        Database database = AccessUtil.getDatabase(new File(filePath));
        HashMap<String, Object> map = new HashMap<>();

        for (String tableName : database.getTableNames()) {
            Table table = database.getTable(tableName);
            boolean flag = true;

            List<Row> list = new ArrayList<>();
            while (flag) {
                Row row = table.getNextRow();
                list.add(row);

                if (Objects.isNull(row) && list.size() > 0) {
                    map.put(table.getName(), list);
                    flag = false;
                }
            }
        }
        return map;
    }

    public static void rowToExecl(String mdbFilePath) throws IOException {
        File file = new File(mdbFilePath);
        Database database = AccessUtil.getDatabase(file);
        System.out.println(database.getTableNames().size());
        for (String tableName : database.getTableNames()) {
            //获取当前table对象
            Table table = database.getTable(tableName);
            boolean flag = true;

            ArrayList<Map<String, Object>> rows = CollUtil.newArrayList();

            while (flag) {
                //遍历当前表全部行数据
                Row row = table.getNextRow();
                if (Objects.isNull(row)) {
                    break;
                }
                //组装数据
                Map<String, Object> rowInfo = new LinkedHashMap<>();
                row.keySet().stream().forEach(element -> {
                    rowInfo.put(element, row.get(element));
                });
                //接收数据
                rows.add(rowInfo);
                if (rows.size() == table.getRowCount()) {
                    System.out.println("计算");
                    String excel=file.getParent() + File.separator + tableName + ".xlsx";

                    ExcelWriter writer=ExcelUtil.getWriter(excel,tableName);

                    writer.passCurrentRow();
                    writer.write(rows, true);
                    writer.close();
                    flag = false;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "C:\\Users\\12733\\Desktop\\新建文件夹\\310000上海市国土空间总体规划表格.mdb";
        rowToExecl(path);
    }
}
