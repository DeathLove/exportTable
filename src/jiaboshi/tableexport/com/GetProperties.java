package jiaboshi.tableexport.com;

import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    static {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/info.properties");
    }
}
