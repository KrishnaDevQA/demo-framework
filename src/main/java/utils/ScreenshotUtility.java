package utils;

import contants.TestConstants;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import org.openqa.selenium.WebDriver;
public class ScreenshotUtility {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtility.class);
    public static String takeScreenshotAsPNG(WebDriver driver, String subDir, String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String sanitizedFileName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String relativeDir = "screenshots" + File.separator + subDir;
        String relativePath = relativeDir + File.separator + sanitizedFileName + "_" + timestamp + ".png";
        String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
        try {
            File dir = new File(System.getProperty("user.dir") + File.separator + relativeDir);
            if (!dir.exists()) dir.mkdirs();
            FileUtils.copyFile(src, new File(absolutePath));
            //logger.info("Screenshot saved successfully: " + absolutePath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot to path: " + absolutePath, e);
            throw e;
        }
        return "../" + relativePath.replace("\\", "/");
    }
    public static String takeScreenshotAsBase64(WebDriver driver) throws WebDriverException {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String base64 = ts.getScreenshotAs(OutputType.BASE64);
            //logger.info("Screenshot captured successfully as Base64");
            return base64;
        } catch (WebDriverException e) {
            logger.error("Failed to take screenshot as Base64", e);
            throw e;
        }
    }
    public static String takeElementScreenshotAsPNG(WebElement element, String subDir, String screenshotName) throws IOException {
        File src = element.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String sanitizedFileName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String relativeDir = "screenshots" + File.separator + subDir;
        String relativePath = relativeDir + File.separator + sanitizedFileName + "_" + timestamp + ".png";
        String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
        try {
            File dir = new File(System.getProperty("user.dir") + File.separator + relativeDir);
            if (!dir.exists()) dir.mkdirs();
            FileUtils.copyFile(src, new File(absolutePath));
            //logger.info("Element screenshot saved successfully: " + absolutePath);
        } catch (IOException e) {
            logger.error("Failed to save element screenshot to path: " + absolutePath, e);
            throw e;
        }
        return "../" + relativePath.replace("\\", "/");
    }
    public static String takeElementScreenshotAsBase64(WebElement element) throws WebDriverException {
        try {
            String base64 = element.getScreenshotAs(OutputType.BASE64);
            //logger.info("Element screenshot captured successfully as Base64");
            return base64;
        } catch (WebDriverException e) {
            logger.error("Failed to take element screenshot as Base64", e);
            throw e;
        }
    }
    private static BufferedImage captureFullElementImage(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Thread interrupted during scroll delay", e);
        }
        Screenshot screenshot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .shootingStrategy(ShootingStrategies.viewportPasting(TestConstants.SCROLL_DELAY))
                .takeScreenshot(driver, element);
        return screenshot.getImage();
    }
    public static String takeFullElementScreenshotAsPNG(WebDriver driver, WebElement element, String subDir, String screenshotName) throws IOException {
        //logger.info("Capturing full screenshot for element: {}", screenshotName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500); // Allow scroll to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Thread interrupted during scroll delay", e);
        }
        Screenshot screenshot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .shootingStrategy(ShootingStrategies.viewportPasting(100))  // hardcoded scroll delay
                .takeScreenshot(driver, element);
        BufferedImage image = screenshot.getImage();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String sanitizedFileName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String relativeDir = "screenshots" + File.separator + subDir;
        String relativePath = relativeDir + File.separator + sanitizedFileName + "_" + timestamp + ".png";
        String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
        File dir = new File(System.getProperty("user.dir") + File.separator + relativeDir);
        if (!dir.exists()) dir.mkdirs();
        try {
            ImageIO.write(image, "PNG", new File(absolutePath));
            //logger.info("Element screenshot saved successfully: {}", absolutePath);
        } catch (IOException e) {
            logger.error("Failed to save element screenshot to path: {}", absolutePath, e);
            throw e;
        }
        return "../" + relativePath.replace("\\", "/");
    }
    public static String takeFullElementScreenshotAsBase64(WebDriver driver, WebElement element) throws IOException {
        //logger.info("Capturing Base64 screenshot for element");
        BufferedImage image = captureFullElementImage(driver, element);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
    private static BufferedImage captureFullPageImage(WebDriver driver) {
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            return screenshot.getImage();
        } catch (Exception e) {
            logger.error("Failed to capture full page screenshot", e);
            return null;
        }
    }
    public static String takeFullPageScreenshotAsPNG(WebDriver driver, String subDir, String screenshotName) throws IOException {
        //logger.info("Capturing full page screenshot: {}", screenshotName);
        BufferedImage image = captureFullPageImage(driver);
        if (image == null) {
            throw new IOException("Failed to capture full page screenshot");
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String sanitizedFileName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String relativeDir = "screenshots" + File.separator + subDir;
        String relativePath = relativeDir + File.separator + sanitizedFileName + "_" + timestamp + ".png";
        String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

        File dir = new File(System.getProperty("user.dir") + File.separator + relativeDir);
        if (!dir.exists()) dir.mkdirs();

        try {
            ImageIO.write(image, "PNG", new File(absolutePath));
            //logger.info("Full page screenshot saved successfully: {}", absolutePath);
        } catch (IOException e) {
            logger.error("Failed to save full page screenshot to path: {}", absolutePath, e);
            throw e;
        }
        return "../" + relativePath.replace("\\", "/");
    }
    public static String takeFullPageScreenshotAsBase64(WebDriver driver) throws IOException {
        //logger.info("Capturing full page screenshot as Base64");
        BufferedImage image = captureFullPageImage(driver);
        if (image == null) {
            throw new IOException("Failed to capture full page screenshot");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
