package utils.wapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface IWaitUtility {

    // Wait Utilities
    public static void waitForVisibility(WebDriver driver, WebElement element, int timeoutSeconds, String elementName) {}
    public static void waitForClickability(WebDriver driver, WebElement element, int timeoutSeconds, String elementName) {}
    public static void waitForPresence(WebDriver driver, By locator, int timeoutSeconds, String elementName) {}
    public static void waitForInvisibility(WebDriver driver, WebElement element, int timeoutSeconds, String elementName) {}
    public static void waitForTextToBePresent(WebDriver driver, WebElement element, String text, int timeoutSeconds, String elementName) {}
    public static void waitForTextToBeAbsent(WebDriver driver, WebElement element, String text, int timeoutSeconds, String elementName) {}
    public static void waitForAttributeToBe(WebDriver driver, WebElement element, String attribute, String value, int timeoutSeconds, String elementName) {}
    public static void waitForAttributeContains(WebDriver driver, WebElement element, String attribute, String value, int timeoutSeconds, String elementName) {}
    public static void waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, By frameLocator, int timeoutSeconds, String frameName) {}
    public static void waitForNumberOfElementsToBe(WebDriver driver, By locator, int number, int timeoutSeconds, String elementName) {}
    public static void waitForNumberOfElementsToBeMoreThan(WebDriver driver, By locator, int number, int timeoutSeconds, String elementName) {}
    public static void waitForNumberOfElementsToBeLessThan(WebDriver driver, By locator, int number, int timeoutSeconds, String elementName) {}
    public static void waitForUrlToBe(WebDriver driver, String url, int timeoutSeconds) {}
    public static void waitForUrlContains(WebDriver driver, String fraction, int timeoutSeconds) {}
    public static void waitForTitleToBe(WebDriver driver, String title, int timeoutSeconds) {}
    public static void waitForTitleContains(WebDriver driver, String titleFragment, int timeoutSeconds) {}
    public static void waitForAlertPresence(WebDriver driver, int timeoutSeconds) {}
    public static void waitForElementToBeSelected(WebDriver driver, WebElement element, int timeoutSeconds, String elementName) {}
    public static void waitForElementToBeDeselected(WebDriver driver, WebElement element, int timeoutSeconds, String elementName) {}
    public static void waitForNumberOfWindowsToBe(WebDriver driver, int expectedNumberOfWindows, int timeoutSeconds) {}
    public static void waitForWindowToBeAvailableAndSwitchToIt(WebDriver driver, String windowHandleOrTitle, int timeoutSeconds) {}
    public static void waitForWindowToClose(WebDriver driver, String windowHandle, int timeoutSeconds) {}
}
