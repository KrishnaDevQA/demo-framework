package base_page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import utils.BrowserUtility;


public class BasePage extends BrowserUtility {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnCard(String cardName) {
        String cardXpath = "//div[@class='card-body']//h5[text()='"+cardName+"']";
        click(driver, By.xpath(cardXpath));
    }

    public void clickOnSidebarMenu(String menuName) {
        String menuXpath = "//span[text()='"+menuName+"']";
        click(driver, By.xpath(menuXpath));
    }

}