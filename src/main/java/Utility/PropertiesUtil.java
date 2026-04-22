package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties prop;

    public static Properties loadProperties() throws IOException {

        if (prop == null) {
            prop = new Properties();

            String path = System.getProperty("user.dir") 
                        + "/config/config_fakeStoreAPI.properties";

            FileInputStream fis = new FileInputStream(path);
            prop.load(fis);
        }

        return prop;
    }

    public static String getProperty(String key) throws IOException {
        return loadProperties().getProperty(key);
    }
}