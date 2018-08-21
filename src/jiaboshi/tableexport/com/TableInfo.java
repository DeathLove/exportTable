package jiaboshi.tableexport.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TableInfo.class);
    private Connection connection;
    private Statement statement;

    public static void main(String[] args) throws Exception {
        TableInfo tableInfo = new TableInfo();
        tableInfo.initConnection("com.mysql.jdbc.Driver",
                "url", "use", "password");
    }

    public Map<String, Object> getTableInfo(String ip, String post, String database, String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        TableInfo tableInfo = new TableInfo();
        try {
            String url = "jdbc:mysql://" + ip + ":" + post + "/" + database + "?zeroDateTimeBehavior=convertToNull";
            tableInfo.initConnection("com.mysql.jdbc.Driver", url, username, password);

            ResultSet resultSet = tableInfo.getMetaDataTables(database);
            List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
            while (resultSet.next()) {
                Map<String, Object> table = new HashMap<String, Object>();
                String tableName = resultSet.getString("TABLE_NAME");
                table.put("table", tableName);
                table.put("encode", resultSet.getString("TABLE_COLLATION"));
                table.put("table_context", resultSet.getString("TABLE_COMMENT"));
                table.put("engine", resultSet.getString("ENGINE") == null ? "" : resultSet.getString("ENGINE"));
                tableList.add(table);
            }
            for (Map<String, Object> table : tableList) {
                table.put("columnList", displayMetaData(tableInfo, table.get("table").toString()));
                table.put("indexList", getTableIndex(tableInfo, table.get("table").toString()));
            }
            map.put("tableList", tableList);
        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public void initConnection(String driverClass, String dbUrl, String username, String password) throws Exception {
        Class.forName(driverClass);
        this.connection = DriverManager.getConnection(dbUrl, username, password);
        this.statement = this.connection.createStatement();
    }

    public ResultSet getMetaDataTables(String database) throws Exception {
        String sql = "select * from information_schema.tables where table_schema='" + database + "' and table_type='BASE TABLE'";
        ResultSet rs = this.statement.executeQuery(sql);
        return rs;
    }

    public List<Map<String, Object>> displayMetaData(TableInfo tableInfo, String tableName) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        String sql = "show full columns from " + tableName;
        ResultSet rs = tableInfo.getStatement().executeQuery(sql);
        int i = 1;
        while (rs.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", i);
            map.put("column", rs.getString("Field"));
            map.put("type", rs.getString("Type"));
            map.put("isNull", rs.getString("Null"));
            map.put("default", rs.getString("Default") == null ? "" : rs.getString("Default"));
            map.put("context", rs.getString("Comment"));
            list.add(map);
            i++;
        }
        return list;
    }
    
    public List<Map<String, Object>> getTableIndex(TableInfo tableInfo, String tableName) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        String sql = "show keys from " + tableName;
        ResultSet rs;
        try {
            rs = tableInfo.getStatement().executeQuery(sql);
            int i = 1;
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                String keyName = rs.getString("Key_name");
                map.put("id", i);
                map.put("index", keyName);
                String type = "";
                String unique = rs.getString("Non_unique");
                if ("PRIMARY".equals(keyName)) {
                    type = "PRIMARY";
                } else if ("0".equals(unique)) {
                    type = "UNIQUE";
                } else {
                    type = "NORMAL";
                }
                map.put("type", type);
                map.put("field", rs.getString("Column_name"));
                list.add(map);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
