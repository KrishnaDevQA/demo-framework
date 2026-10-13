package contants;

import utils.PropertyReader;

public class TestConstants {
    private static final String filePath = System.getProperty("user.dir")+"/config/config.properties";
    // ##### Constants #####
    public static final String BASE_URL = PropertyReader.getProperty(filePath,"BASE_URL").trim();
    public static final String BROWSER = PropertyReader.getProperty(filePath,"BROWSER").trim();
    public static final String HEADLESS = PropertyReader.getProperty(filePath,"HEADLESS").trim();
    public static final String REPORT_THEME = PropertyReader.getProperty(filePath,"REPORT_THEME").trim();
    public static final String REPORT_TITLE = PropertyReader.getProperty(filePath,"REPORT_TITLE").trim();
    public static final String REPORT_NAME = PropertyReader.getProperty(filePath,"REPORT_NAME").trim();
    public static final String HOST_NAME = PropertyReader.getProperty(filePath,"HOST_NAME").trim();
    public static final String ENVIRONMENT = PropertyReader.getProperty(filePath,"ENVIRONMENT").trim();
    public static final String USER = PropertyReader.getProperty(filePath,"USER").trim();
    public static final String SCREENSHOT_FORMAT = PropertyReader.getProperty(filePath,"SCREENSHOT_FORMAT").trim();
    public static final int TIMEOUT = Integer.parseInt(PropertyReader.getProperty(filePath,"TIMEOUT"));
    public static final String  PAGELOADSTRATEGY = PropertyReader.getProperty(filePath,"PAGELOADSTRATEGY").trim();
    public static final boolean ACCEPT_INSECURE_CERTS = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"ACCEPT_INSECURE_CERTS"));
    public static final boolean USE_INCognito_MODE = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"USE_INCognito_MODE"));
    public static final boolean ENABLE_FILE_DOWNLOAD = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"ENABLE_FILE_DOWNLOAD"));
    public static final String DOWNLOAD_DIRECTORY_PATH = PropertyReader.getProperty(filePath,"DOWNLOAD_DIRECTORY_PATH").trim();
    public static final boolean ARCHIVE_LOGS = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"ARCHIVE_LOGS"));
    public static final boolean ARCHIVE_REPORTS = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"ARCHIVE_REPORTS"));
    public static final boolean TAKE_SCREENSHOT_ON_PASS = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"TAKE_SCREENSHOT_ON_PASS"));
    public static final boolean TAKE_SCREENSHOT_ON_FAILURE = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"TAKE_SCREENSHOT_ON_FAILURE"));
    public static final boolean TAKE_SCREENSHOT_ON_SKIP = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"TAKE_SCREENSHOT_ON_SKIP"));
    public static final boolean ADD_CLASS_NAME = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"ADD_CLASS_NAME"));
    public static final int SCROLL_DELAY = Integer.parseInt(PropertyReader.getProperty(filePath,"SCROLL_DELAY"));
    public static final boolean TAKE_FULL_PAGE_SCREENSHOT = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"TAKE_FULL_PAGE_SCREENSHOT"));
    public static final int STALE_EXCEPTION_RETRY_ATTEMPTS_COUNT = Integer.parseInt(PropertyReader.getProperty(filePath,"STALE_EXCEPTION_RETRY_ATTEMPTS_COUNT"));
    public static final boolean IS_ACCESSIONING_EXCEL_TO_BE_DELETED = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"IS_ACCESSIONING_EXCEL_TO_BE_DELETED"));
    public static final boolean IS_EXPORTED_CSV_TO_BE_DELETED = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"IS_EXPORTED_CSV_TO_BE_DELETED"));
    public static final boolean IS_PROCESSED_CSV_TO_BE_DELETED = Boolean.parseBoolean(PropertyReader.getProperty(filePath,"IS_PROCESSED_CSV_TO_BE_DELETED"));
}
