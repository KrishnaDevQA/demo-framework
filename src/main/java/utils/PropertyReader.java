package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PropertyReader {
    private static final Logger logger = LogManager.getLogger(PropertyReader.class);
    public static String getProperty(String filePath, String key) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            logger.error("Error loading properties file: " + filePath, e);
            return null;
        }
        return properties.getProperty(key);
    }
}
