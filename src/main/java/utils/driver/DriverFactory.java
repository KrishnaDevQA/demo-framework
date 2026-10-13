package utils.driver;

import contants.TestConstants;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverFactory {

    public static WebDriver createInstance(String browser, boolean headless) {
        WebDriver driver;

        boolean acceptInsecureCerts = TestConstants.ACCEPT_INSECURE_CERTS;
        boolean useIncognito = TestConstants.USE_INCognito_MODE;
        boolean enableFileDownload = TestConstants.ENABLE_FILE_DOWNLOAD;
        String pageLoadStrategy = TestConstants.PAGELOADSTRATEGY;
        String downloadDir = System.getProperty("user.dir")+TestConstants.DOWNLOAD_DIRECTORY_PATH;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));
                chromeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                if (headless) chromeOptions.addArguments("--headless=new");
                if (useIncognito) chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--remote-allow-origins=*");

                if (enableFileDownload) {
                    String absoluteDownloadPath = Paths.get(downloadDir).toAbsolutePath().toString();
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("download.default_directory", absoluteDownloadPath);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("safebrowsing.enabled", true);
                    prefs.put("autofill.profile_enabled", false);
                    prefs.put("autofill.credit_card_enabled", false);
                    prefs.put("plugins.always_open_pdf_externally", true);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                }

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));
                firefoxOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                if (headless) firefoxOptions.addArguments("--headless");
                if (useIncognito) firefoxOptions.addArguments("-private");

                if (enableFileDownload) {
                    String absoluteDownloadPath = Paths.get(downloadDir).toAbsolutePath().toString();
                    firefoxOptions.addPreference("browser.download.folderList", 2); // 2 = use custom location
                    firefoxOptions.addPreference("browser.download.dir", absoluteDownloadPath);
                    firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
                            "application/pdf,application/octet-stream,application/vnd.ms-excel,text/csv");
                    firefoxOptions.addPreference("pdfjs.disabled", true); // disable built-in PDF viewer
                    firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
                }

                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));
                edgeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                if (headless) edgeOptions.addArguments("--headless=new");
                if (useIncognito) edgeOptions.addArguments("-inprivate");
                edgeOptions.addArguments("--remote-allow-origins=*");

                if (enableFileDownload) {
                    String absoluteDownloadPath = Paths.get(downloadDir).toAbsolutePath().toString();
                    Map<String, Object> edgePrefs = new HashMap<>();
                    edgePrefs.put("download.default_directory", absoluteDownloadPath);
                    edgePrefs.put("download.prompt_for_download", false);
                    edgePrefs.put("safebrowsing.enabled", true);
                    edgePrefs.put("plugins.always_open_pdf_externally", true);
                    edgeOptions.setExperimentalOption("prefs", edgePrefs);
                }

                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        return driver;
    }
}

