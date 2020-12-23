package com.zlutil.tools.toolpackage.freemarker;

import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.*;


/**
 * @author wansw
 * 获取当前用户下表的信息
 */
public class DbTable {

    private final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private final String DATABASE_URL = "jdbc:oracle:thin:@161.189.167.206:1521/ORCL";
    private final String DATABASE_USER = "bj_yzt";
    private final String DATABASE_PASSWORD = "pass";

    private Connection con = null;
    private DatabaseMetaData meta = null;

    private List<String> tableNameList = new ArrayList<>();
    private static List<Map<String, Object>> result = new ArrayList<>();


    // 获取数据库连接
    private void getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            Properties props = new Properties();
            props.put("user", DATABASE_USER);
            props.put("password", DATABASE_PASSWORD);
            // 加入这一句才可以获取到注释信息
            props.put("remarksReporting", "true");
            con = DriverManager.getConnection(DATABASE_URL, props);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 获取当前用户下的表名
     * @param tableNamePattern 过滤
     * @desc 查询全部 null， 查询指定前缀表：PAS_%
     */
    private void getAllTablesInCurrentUser(String tableNamePattern) {
        try {
            meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, DATABASE_USER.toUpperCase(), tableNamePattern,
                    new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tableNameList.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void getTableColumnsInfo() throws Exception {
        for (String tableName : tableNameList) {
            List<TableDefinition> list = getTableDefinition(tableName);
            Map<String, Object> temp = new HashMap<>();
            temp.put("tblName", tableName+" "+getTableComment().replace("\"",""));
            temp.put("tblData", list);
            result.add(temp);
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 获取表中定义的信息
    private List<TableDefinition> getTableDefinition(String tableName) {
        try {
            ResultSet rs = meta.getColumns(null, DATABASE_USER.toUpperCase(), tableName, "%");
            List<TableDefinition> tableDefinitionList = new ArrayList<>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String typeName = rs.getString("TYPE_NAME");
                String columnSize = rs.getString("COLUMN_SIZE");
                int nullAble = rs.getInt("NULLABLE");
                String remarks = rs.getString("REMARKS");
                TableDefinition definition = new TableDefinition.Builder()
                        .columnName(columnName)
                        .typeName(typeName)
                        .size(columnSize)
                        .isNull(nullAble)
                        .remark(remarks)
                        .build();
                tableDefinitionList.add(definition);
            }
            rs.close();

            ResultSet primaryKeysSet = meta.getPrimaryKeys(null, DATABASE_USER.toUpperCase(), tableName);
            while (primaryKeysSet.next()) {
                String primaryKeyColumnName = primaryKeysSet.getString("COLUMN_NAME");
                setPrimaryKey(primaryKeyColumnName, tableDefinitionList);
            }
            primaryKeysSet.close();
            return tableDefinitionList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 判断主键
    private void setPrimaryKey(String primaryKey, List<TableDefinition> tableDefinitionList) {
        for (TableDefinition definition : tableDefinitionList) {
            if (primaryKey.equals(definition.getColumnName())) {
                definition.setKey("True");
            }
        }
    }

    String getTableComment() throws Exception {
        //select t.comments from user_tab_comments t where t.table_name='YZT_FAVORITES';
        //String updateTableComment = "COMMENT ON TABLE %s.%s IS '%s'";
        getConnection();
        //JdbcTemplate jdbcTemplate=new JdbcTemplate();
        ResultSet resultSet = con.createStatement().executeQuery("select t.comments from user_tab_comments t where t.table_name='YZT_FAVORITES'");
        //jdbcTemplate.update(String.format(updateTableComment, schema, tableName.toUpperCase(), tableComment));
        resultSet.next();
        return JSON.toJSONString(resultSet.getObject(1));
    }

    public static void main(String[] args) throws Exception {
        DbTable table = new DbTable();
        table.getConnection();
        // 只需要获取指定前缀的表，需要获取所有的，使用 null
        table.getAllTablesInCurrentUser(null);
        table.getTableColumnsInfo();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("array", result);
        String path = UUID.randomUUID().toString() + ".doc";
        System.out.println(path);
        WordUtils.createReport(dataMap, "DbTable1.ftl", path);
    }

}