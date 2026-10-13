package utils.wapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface IBrowserUtility {

    // Basic Element Actions
    public static void click(WebDriver driver, WebElement element, String elementName) {}
    public static void doubleClick(WebDriver driver, WebElement element, String elementName) {}
    public static void rightClick(WebDriver driver, WebElement element, String elementName) {}
    public static void sendKeys(WebDriver driver, WebElement element, String value, String elementName) {}
    public static String getText(WebDriver driver, WebElement element, String elementName) { return null; }
    public static void clear(WebDriver driver, WebElement element, String elementName) {}
    public static boolean isDisplayed(WebDriver driver, WebElement element, String elementName) { return false; }
    public static boolean isEnabled(WebDriver driver, WebElement element, String elementName) { return false; }
    public static boolean isDisabled(WebDriver driver, WebElement element, String elementName) { return false; }
    public static void checkIfNotSelected(WebDriver driver, WebElement element, String elementName) {}
    public static void uncheckIfSelected(WebDriver driver, WebElement element, String elementName) {}
    public static boolean isChecked(WebDriver driver, WebElement element, String elementName){return false;}

    // Select options
    public static void selectByVisibleText(WebDriver driver, WebElement dropdownElement, String visibleText, String elementName) {}
    public static void selectByValue(WebDriver driver, WebElement dropdownElement, String value, String elementName) {}
    public static void selectByIndex(WebDriver driver, WebElement dropdownElement, int index, String elementName) {}
    public static void selectRandomOption(WebDriver driver, WebElement dropdownElement, String elementName){}
    public static boolean isOptionSelected(WebDriver driver, WebElement dropdownElement, String expectedOption, String elementName){return false;}
    public static boolean isSelected(WebDriver driver, WebElement element, String elementName) { return false; }
    public static String getSelectedOptionText(WebDriver driver, WebElement dropdownElement, String elementName) { return null; }
    public static List<String> getAllDropdownOptions(WebDriver driver, WebElement dropdownElement, String elementName) { return null; }
    public static boolean isOptionPresent(WebDriver driver, WebElement dropdownElement, String expectedOption, String elementName) { return false; }
    public static int getDropdownSize(WebDriver driver, WebElement dropdownElement, String elementName) { return 0; }
    public static boolean isMultiple(WebDriver driver, WebElement dropdownElement, String elementName) { return false; }
    public static void deselectByVisibleText(WebDriver driver, WebElement dropdownElement, String visibleText, String elementName) {}
    public static void deselectByValue(WebDriver driver, WebElement dropdownElement, String value, String elementName) {}
    public static void deselectByIndex(WebDriver driver, WebElement dropdownElement, int index, String elementName) {}
    public static void deselectAll(WebDriver driver, WebElement dropdownElement, String elementName) {}

    // JavaScript Actions
    public static void clickUsingJS(WebDriver driver, WebElement element, String elementName) {}
    public static void scrollToElement(WebDriver driver, WebElement element, String elementName) {}
    public static void scrollToBottomOfPage(WebDriver driver) {}
    public static void scrollToTopOfPage(WebDriver driver) {}
    public static void setValueUsingJS(WebDriver driver, WebElement element, String value, String elementName) {}
    public static void removeAttributeUsingJS(WebDriver driver, WebElement element, String attribute, String elementName) {}
    public static void highlightElement(WebDriver driver, WebElement element, String elementName) {}
    public static void refreshPageUsingJS(WebDriver driver) {}
    public static void focusElementUsingJS(WebDriver driver, WebElement element, String elementName) {}
    public static void blurElementUsingJS(WebDriver driver, WebElement element, String elementName) {}
    public static boolean isElementInViewport(WebDriver driver, WebElement element, String elementName) { return false; }
    public static void clearInputUsingJS(WebDriver driver, WebElement element, String elementName) {}
    public static boolean isPageLoaded(WebDriver driver) { return false; }
    public static void executeCustomJS(WebDriver driver, String script, Object... args) {}

    // Mouse Actions
    public static void hoverOverElement(WebDriver driver, WebElement element, String elementName) {}
    public static void dragAndDrop(WebDriver driver, WebElement source, WebElement target, String sourceName, String targetName) {}

    // Alerts
    public static void acceptAlert(WebDriver driver) {}
    public static void dismissAlert(WebDriver driver) {}
    public static String getAlertText(WebDriver driver) { return null; }
    public static void sendTextToAlert(WebDriver driver, String text) {}

    // Frames
    public static void switchToFrameByElement(WebDriver driver, WebElement frameElement) {}
    public static void switchToFrameByIndex(WebDriver driver, int index) {}
    public static void switchToDefaultContent(WebDriver driver) {}

    // Window & Tab Management
    public static void switchToWindowByTitle(WebDriver driver, String windowTitle) {}
    public static void switchToWindowByIndex(WebDriver driver, int index) {}
    public static void closeCurrentWindow(WebDriver driver) {}
    public static int getNumberOfOpenWindows(WebDriver driver) { return 0; }

    // File Input
    public static void uploadFile(WebDriver driver, WebElement fileInputElement, String absoluteFilePath, String elementName) {}
    public static boolean isFileUploaded(WebDriver driver, WebElement confirmationElement, String expectedText, String elementName) { return false; }

    // Style / Attribute / CSS
    public static String getCssColor(WebDriver driver, WebElement element, String cssProperty, String elementName) { return null; }
    public static String getCssValue(WebDriver driver, WebElement element, String cssProperty, String elementName) { return null; }
    public static String getAttribute(WebDriver driver, WebElement element, String attribute, String elementName) { return null; }
    public static boolean isAttributePresent(WebDriver driver, WebElement element, String attribute, String elementName) { return false; }
    public static boolean hasClass(WebDriver driver, WebElement element, String className, String elementName) { return false; }

    // Page Checks
    public static String getPageTitle(WebDriver driver) { return null; }
    public static String getCurrentUrl(WebDriver driver) { return null; }
    public static boolean verifyPageTitle(WebDriver driver, String expectedTitle) { return false; }
    public static boolean verifyCurrentUrl(WebDriver driver, String expectedUrl) { return false; }
    public static boolean doesPageTitleContain(WebDriver driver, String partialTitle) { return false; }
    public static boolean doesUrlContain(WebDriver driver, String partialUrl) { return false; }

    // Navigation
    // Cookies
}
