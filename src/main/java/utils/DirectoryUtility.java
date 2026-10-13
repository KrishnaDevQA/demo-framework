package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public final class DirectoryUtility {
    protected static Logger logger = LogManager.getLogger(DirectoryUtility.class);
    public static String getPath(String... parts) {
        return System.getProperty("user.dir") + File.separator + String.join(File.separator, parts);
    }
    public static void createFolderIfNotExists(String folderPath) {
        synchronized (DirectoryUtility.class) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (created) {
                    logger.info("Created folder: " + folderPath);
                } else {
                    logger.error("Failed to create folder: " + folderPath);
                }
            }
        }
    }
}
