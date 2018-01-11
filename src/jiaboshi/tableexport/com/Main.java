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
            System.out.println(">>>>>>>>>>>>>>开始导出数据库表结构");
            long start = System.currentTimeMillis();
            /*in = new FileInputStream(args[0]);
            props.load(in);
            String ip = props.getProperty("jdbc.ip");
            String post = props.getProperty("jdbc.post");
            String database = props.getProperty("jdbc.database");
            String username = props.getProperty("jdbc.user");
            String password = props.getProperty("jdbc.password");*/
            TableInfo tableInfo = new TableInfo();
            Map<String, Object> map = tableInfo.getTableInfo("10.6.11.25", "3306", "lse_home_dev", "root", "Hangzhou@123");

            ExportDatabaseInfo info = new ExportDatabaseInfo();
            info.createDoc(map, "testDoc", "E:\\table.doc");
            System.out.println("导出文件为: ");
            long end = System.currentTimeMillis();
            System.out.println(">>>>>>>>>>>>>导出结束，用时 : " + (end - start)+ " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
