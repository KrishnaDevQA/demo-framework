package org.example;

import base_page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextBoxPage extends BasePage {

    public TextBoxPage(WebDriver driver) {
        super(driver);
    }

    private final By fullNameInput = By.id("userName");
    private final By emailInput = By.id("userEmail");
    private final By currentAddressInput = By.cssSelector("textarea#currentAddress");
    private final By permanentAddressInput = By.cssSelector("textarea#permanentAddress");
    private final By submitButton = By.id("submit");
    private final By fullNameOutput = By.cssSelector("p#name");
    private final By emailOutput = By.cssSelector("p#email");
    private final By currentAddressOutput = By.cssSelector("p#currentAddress");
    private final By permanentAddressOutput = By.cssSelector("p#permanentAddress");

    public void enterFullName(String fullName) {
        sendKeys(driver, fullNameInput, fullName);
    }
    public void enterEmail(String email) {
        sendKeys(driver, emailInput, email);
    }
    public void enterCurrentAddress(String currentAddress) {
        sendKeys(driver, currentAddressInput, currentAddress);
    }
    public void enterPermanentAddress(String permanentAddress) {sendKeys(driver, permanentAddressInput, permanentAddress);}
    public void clickOnSubmitButton() {
        click(driver, submitButton);
    }
    public boolean isFullNameDisplayed() {
        return isDisplayed(driver, fullNameOutput);
    }
    public boolean isEmailDisplayed() {
        return isDisplayed(driver, emailOutput);
    }
    public boolean isCurrentAddressDisplayed() {
        return isDisplayed(driver, currentAddressOutput);
    }
    public boolean isPermanentAddressDisplayed() {
        return isDisplayed(driver, permanentAddressOutput);
    }
    public String getFullNameOutputText() {
        return getText(driver, fullNameOutput);
    }
    public String getEmailOutputText() {
        return getText(driver, emailOutput);
    }
    public String getCurrentAddressOutputText() {
        return getText(driver, currentAddressOutput);
    }
    public String getPermanentAddressOutputText() {
        return getText(driver, permanentAddressOutput);
    }
}
