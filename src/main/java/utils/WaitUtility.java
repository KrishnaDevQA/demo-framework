package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class WaitUtility {
    private static final Logger logger = LogManager.getLogger(WaitUtility.class);
    private WebDriverWait getWait(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    private final int RETRY_COUNT = 5;

    // ================= THREAD WAIT =================

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
    }

    public void waitForSeconds(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
    }

    public void waitForPageLoad(WebDriver driver, int timeoutSeconds) {
        getWait(driver, timeoutSeconds).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    // ================= BASIC ELEMENT WAITS =================

    public WebElement waitForVisibility(WebDriver driver, By locator, int timeoutSeconds) {
        return retryingWait(driver, timeoutSeconds, ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickability(WebDriver driver, By locator, int timeoutSeconds) {
        return retryingWait(driver, timeoutSeconds, ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForPresence(WebDriver driver, By locator, int timeoutSeconds) {
        return retryingWait(driver, timeoutSeconds, ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementToBeSelected(WebDriver driver, By locator, int timeoutSeconds) {
        return retryingWait(driver, timeoutSeconds, ExpectedConditions.elementToBeClickable(locator));
    }

    // ================= LIST WAITS =================

    public List<WebElement> waitForAllVisible(WebDriver driver, By locator, int timeoutSeconds) {
        return getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ================= INVISIBLE =================

    public void waitForInvisibility(WebDriver driver, By locator, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementToBeDeselected(WebDriver driver, By locator, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.not(
                        ExpectedConditions.elementToBeSelected(locator)));
    }

    // ================= TEXT =================

    public WebElement waitForTextToBePresent(WebDriver driver, By locator, String text, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        return driver.findElement(locator);
    }

    public void waitForTextToBeAbsent(WebDriver driver, By locator, String text, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElementLocated(locator, text)));
    }

    // ================= ATTRIBUTE =================

    public WebElement waitForAttributeToBe(WebDriver driver, By locator, String attribute, String value,
                                           int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.attributeToBe(locator, attribute, value));
        return driver.findElement(locator);
    }

    public WebElement waitForAttributeContains(WebDriver driver, By locator, String attribute, String value,
                                               int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.attributeContains(locator, attribute, value));
        return driver.findElement(locator);
    }

    // ================= FRAME =================

    public void waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, By frameLocator, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    // ================= ELEMENT COUNT =================

    public void waitForNumberOfElementsToBe(WebDriver driver, By locator, int number, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.numberOfElementsToBe(locator, number));
    }

    // ================= URL =================

    public void waitForUrlToBe(WebDriver driver, String url, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.urlToBe(url));
    }

    public void waitForUrlContains(WebDriver driver, String fraction, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.urlContains(fraction));
    }

    // ================= TITLE =================

    public void waitForTitleToBe(WebDriver driver, String title, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.titleIs(title));
    }

    public void waitForTitleContains(WebDriver driver, String titleFragment, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.titleContains(titleFragment));
    }

    // ================= ALERT =================

    public Alert waitForAlertPresence(WebDriver driver, int timeoutSeconds) {
        return getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.alertIsPresent());
    }

    // ================= WINDOW =================

    public void waitForNumberOfWindowsToBe(WebDriver driver, int expectedNumberOfWindows, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
    }

    public void waitForWindowToBeAvailableAndSwitchToIt(WebDriver driver, String windowHandle, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.numberOfWindowsToBe(2));
        driver.switchTo().window(windowHandle);
    }

    public void waitForWindowToClose(WebDriver driver, String windowHandle, int timeoutSeconds) {
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.not(
                        ExpectedConditions.numberOfWindowsToBe(
                                driver.getWindowHandles().size())));
    }

    // ================= RETRY WRAPPER =================

    private WebElement retryingWait(WebDriver driver, int timeoutSeconds, ExpectedCondition<WebElement> condition) {
        int attempts = 0;
        while (attempts < RETRY_COUNT) {
            try {
                return getWait(driver, timeoutSeconds).until(condition);
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= RETRY_COUNT) {
                    throw new RuntimeException("Element remained stale after retries", e);
                }
            }
        }
        throw new RuntimeException("Wait failed unexpectedly");
    }
    public WebElement waitForAttributePresent(WebDriver driver, By locator, String attribute, int timeoutSeconds) {
        WebElement element = driver.findElement(locator);
        getWait(driver, timeoutSeconds)
                .until(ExpectedConditions.attributeToBeNotEmpty(element, attribute));
        return element;
    }

//    public static void waitFileToBeDownloaded(String filePath, int timeoutSeconds) {
//        //logger.info("Waiting for file to be downloaded at path: {}", filePath);
//        File file = new File(filePath);
//        int waited = 0;
//        int interval = 1000;
//        try {
//            while (!file.exists() && waited < timeoutSeconds * 1000) {
//                Thread.sleep(interval);
//                waited += interval;
//            }
//            if (file.exists()) {
//                //logger.info("File downloaded successfully: {}", filePath);
//            } else {
//                throw new TimeoutException("Timed out waiting for file to be downloaded: " + filePath);
//            }
//        } catch (Exception e) {
//            logger.error("Error waiting for file download '{}': {}", filePath, e.getMessage());
//            throw new RuntimeException("Failed to wait for file download", e);
//        }
//    }
    public static void waitFileToBeDownloaded(String filePath, int timeoutSeconds) {

        File file = new File(filePath);
        File tempFile = new File(filePath + ".crdownload");

        int waited = 0;
        int interval = 1000;

        try {
            while ((tempFile.exists() || !file.exists()) && waited < timeoutSeconds * 1000) {
                Thread.sleep(interval);
                waited += interval;
            }

            if (file.exists() && !tempFile.exists()) {
                System.out.println("File downloaded successfully: " + filePath);
            } else {
                throw new TimeoutException(" Timed out waiting for file download: " + filePath);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to wait for file download", e);
        }
    }
}