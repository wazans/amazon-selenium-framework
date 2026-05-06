package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_PATH =
            System.getProperty("user.dir") + "/src/test/resources/config.properties";

    private final Properties prop = new Properties();

    public ConfigReader() {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config file: " + CONFIG_PATH, e);
        }
    }

    public String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return systemValue.trim();
        }

        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue.trim();
        }

        return prop.getProperty(key);
    }
}
