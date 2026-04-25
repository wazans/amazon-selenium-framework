package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    // This instance variable stores all key-value pairs from config.properties.
    Properties prop;

    // Constructor loads the configuration file once when the object is created.
    public ConfigReader() {
        try {
            // "fis" is a local variable. It is needed only while reading the file.
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties");

            prop = new Properties();
            prop.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        // "key" is a method parameter, for example "browser" or "url".
        return prop.getProperty(key);
    }
}
