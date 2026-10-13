package utils;

import contants.TestConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BrowserUtility extends WaitUtility {

    private final int DEFAULT_TIMEOUT = TestConstants.TIMEOUT;

    // ================= BASIC ELEMENT ACTIONS =================

    public void click(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        element = waitForClickability(driver, locator, DEFAULT_TIMEOUT);
        element.click();
    }

    public void doubleClick(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
    }

    public void rightClick(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();
    }

    public void sendKeys(WebDriver driver, By locator, String value) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        element.clear();
        element.sendKeys(value);
    }
    public void sendKeys(WebDriver driver, By locator, Keys key) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        element.sendKeys(key);
    }

    public void sendKeysWithoutClear(WebDriver driver, By locator, String value) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        click(driver,locator);
        element.sendKeys(value);
    }

    public String getText(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        return element.getText().trim();
    }

    public String getValue(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        return element.getAttribute("value").trim();
    }

    public void clear(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        element.clear();
    }

    public boolean isDisplayed(WebDriver driver, By locator) {
        try {
            WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDisplayed(WebDriver driver, By locator,int timeOut) {
        try {
            WebElement element = waitForVisibility(driver, locator, timeOut);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEnabled(WebDriver driver, By locator) {
        try {
            WebElement element = waitForPresence(driver, locator, DEFAULT_TIMEOUT);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDisabled(WebDriver driver, By locator) {
        return !isEnabled(driver, locator);
    }

    public void checkIfNotSelected(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void uncheckIfSelected(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isChecked(WebDriver driver, By locator) {
        try {
            WebElement element = waitForPresence(driver, locator, DEFAULT_TIMEOUT);
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    // ================== SELECT OPTIONS ==================

    public void waitForDropdownOptions(WebDriver driver, By dropdownLocator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(driver1 -> {
            WebElement dropdown = driver1.findElement(dropdownLocator);
            Select select = new Select(dropdown);
            return select.getOptions().size() > 1;
        });
    }

    public void selectByVisibleText(WebDriver driver, By dropdownLocator, String visibleText) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }
    public void selectByVisibleText(WebDriver driver, WebElement element, String visibleText) {
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public void selectByValue(WebDriver driver, By dropdownLocator, String value) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectByIndex(WebDriver driver, By dropdownLocator, int index) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public void selectRandomOption(WebDriver driver, By dropdownLocator) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        if (!options.isEmpty()) {
            int randomIndex = new Random().nextInt(options.size());
            select.selectByIndex(randomIndex);
        }
    }

    public boolean isOptionSelected(WebDriver driver, By dropdownLocator, String expectedOption) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        return select.getAllSelectedOptions()
                .stream()
                .anyMatch(opt -> opt.getText().trim().equals(expectedOption));
    }

    public boolean isSelected(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        return element.isSelected();
    }

    public String getSelectedOptionText(WebDriver driver, By dropdownLocator) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText().trim();
    }

    public List<String> getAllDropdownOptions(WebDriver driver, By dropdownLocator) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        scrollToElement(driver, dropdownLocator);
        Select select = new Select(element);
        List<String> allOptions = new ArrayList<>();
        for (WebElement opt : select.getOptions()) {
            allOptions.add(opt.getText().trim());
        }
        return allOptions;
    }

    public boolean isOptionPresent(WebDriver driver, By dropdownLocator, String expectedOption) {
        List<String> allOptions = getAllDropdownOptions(driver, dropdownLocator);
        return allOptions.contains(expectedOption);
    }

    public int getDropdownSize(WebDriver driver, By dropdownLocator) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        return select.getOptions().size();
    }

    public boolean isMultiple(WebDriver driver, By dropdownLocator) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        return select.isMultiple();
    }

    public void deselectByVisibleText(WebDriver driver, By dropdownLocator, String visibleText) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.deselectByVisibleText(visibleText);
    }

    public void deselectByValue(WebDriver driver, By dropdownLocator, String value) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.deselectByValue(value);
    }

    public void deselectByIndex(WebDriver driver, By dropdownLocator, int index) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.deselectByIndex(index);
    }

    public void deselectAll(WebDriver driver, By dropdownLocator) {
        WebElement element = waitForVisibility(driver, dropdownLocator, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.deselectAll();
    }

    // ================== MOUSE ACTIONS ==================

    public void hoverOverElement(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void dragAndDrop(WebDriver driver, By source, By target) {
        WebElement sourceEle = waitForVisibility(driver, source, DEFAULT_TIMEOUT);
        WebElement targetEle = waitForVisibility(driver, target, DEFAULT_TIMEOUT);
        Actions actions = new Actions(driver);
        actions.dragAndDrop(sourceEle, targetEle).perform();
    }

    // ================== ALERT METHODS ==================

    public boolean isAlertPresent(WebDriver driver) {
        try {
            Alert alert = waitForAlertPresence(driver, DEFAULT_TIMEOUT);
            return alert != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isAlertPresent(WebDriver driver,int timeOut) {
        try {
            Alert alert = waitForAlertPresence(driver, timeOut);
            return alert != null;
        } catch (TimeoutException e) {
            return false;
        }
    }


    public void acceptAlert(WebDriver driver) {
        Alert alert = waitForAlertPresence(driver, DEFAULT_TIMEOUT);
        alert.accept();
    }

    public void dismissAlert(WebDriver driver) {
        Alert alert = waitForAlertPresence(driver, DEFAULT_TIMEOUT);
        alert.dismiss();
    }

    public String getAlertText(WebDriver driver) {
        Alert alert = waitForAlertPresence(driver, DEFAULT_TIMEOUT);
        return alert.getText().trim();
    }

    public void sendTextToAlert(WebDriver driver, String text) {
        Alert alert = waitForAlertPresence(driver, DEFAULT_TIMEOUT);
        alert.sendKeys(text);
    }

    // ================== STYLE / ATTRIBUTE / CSS ==================

    public String getCssColor(WebDriver driver, By locator, String cssProperty) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        return element.getCssValue(cssProperty).trim();
    }

    public String getCssValue(WebDriver driver, By locator, String cssProperty) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        return element.getCssValue(cssProperty).trim();
    }

    public String getAttribute(WebDriver driver, By locator, String attribute) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        return element.getAttribute(attribute);
    }

    public boolean isAttributePresent(WebDriver driver, By locator, String attribute) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        return element.getAttribute(attribute) != null;
    }

    public boolean hasClass(WebDriver driver, By locator, String className) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        String classes = element.getAttribute("class");
        return classes != null && classes.contains(className);
    }

    // ================== PAGE CHECKS ==================

    public String getPageTitle(WebDriver driver) {
        waitForTitleContains(driver, "", DEFAULT_TIMEOUT); // wait for page title to be present
        return driver.getTitle().trim();
    }

    public String getCurrentUrl(WebDriver driver) {
        waitForUrlContains(driver, "", DEFAULT_TIMEOUT); // wait for URL to load
        return driver.getCurrentUrl().trim();
    }

    public boolean verifyPageTitle(WebDriver driver, String expectedTitle) {
        try {
            waitForTitleToBe(driver, expectedTitle, DEFAULT_TIMEOUT);
            return driver.getTitle().equals(expectedTitle);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean verifyCurrentUrl(WebDriver driver, String expectedUrl) {
        try {
            waitForUrlToBe(driver, expectedUrl, DEFAULT_TIMEOUT);
            return driver.getCurrentUrl().equals(expectedUrl);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean doesPageTitleContain(WebDriver driver, String partialTitle) {
        try {
            waitForTitleContains(driver, partialTitle, DEFAULT_TIMEOUT);
            return driver.getTitle().contains(partialTitle);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean doesUrlContain(WebDriver driver, String partialUrl) {
        try {
            waitForUrlContains(driver, partialUrl, DEFAULT_TIMEOUT);
            return driver.getCurrentUrl().contains(partialUrl);
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================== JAVASCRIPT ACTIONS ==================

    public void clickUsingJS(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        scrollToElement(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollToBottomOfPage(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollToTopOfPage(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public void setValueUsingJS(WebDriver driver, By locator, String value) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", element, value);
    }

    public void removeAttributeUsingJS(WebDriver driver, By locator, String attribute) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute(arguments[1]);", element, attribute);
    }

    public void highlightElement(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1]);",
                element, "border: 2px solid red; border-style: dashed;");
        // Optional: revert after a short pause
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
                originalStyle);
    }

    public void refreshPageUsingJS(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("history.go(0);");
    }

    public void focusElementUsingJS(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
    }

    public void blurElementUsingJS(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
    }

    public boolean isElementInViewport(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "const rect = arguments[0].getBoundingClientRect();" +
                        "return (" +
                        "rect.top >= 0 && rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                element);
    }

    public void clearInputUsingJS(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", element);
    }

    public boolean isPageLoaded(WebDriver driver) {
        Object readyState = ((JavascriptExecutor) driver).executeScript("return document.readyState;");
        return Objects.equals(readyState, "complete");
    }

    public void executeCustomJS(WebDriver driver, String script, Object... args) {
        ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public void scrollToElement(WebDriver driver, By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= 3) {
                    throw new RuntimeException("Element remained stale after retries", e);
                }
            }
        }
    }
}