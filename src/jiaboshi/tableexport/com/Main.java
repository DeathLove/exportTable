package jiaboshi.tableexport.com;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties props = new Properties();
        InputStream in;
        try {
            in = new FileInputStream(args[0]);
            props.load(in);
            String ip = props.getProperty("jdbc.ip");
            String post = props.getProperty("jdbc.post");
            String database = props.getProperty("jdbc.database");
            String username = props.getProperty("jdbc.user");
            String password = props.getProperty("jdbc.password");
            TableInfo tableInfo = new TableInfo();
            Map<String, Object> map = tableInfo.getTableInfo(ip, post, database, username, password);

            ExportDatabaseInfo info = new ExportDatabaseInfo();
            info.createDoc(map, "testDoc", "D:\\table.doc");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
