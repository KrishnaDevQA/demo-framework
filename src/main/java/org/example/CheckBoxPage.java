package org.example;

import base_page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckBoxPage extends BasePage {

    public CheckBoxPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnCheckBox(String title){
        String xpath = "//span[@title='"+title+"']//preceding-sibling::span[@role='checkbox']";
        click(driver, By.xpath(xpath));
    }

    public void clickOnSwitcher(String title){
        String xpath = "//span[@title='"+title+"']//preceding-sibling::span[contains(@class,'rc-tree-switcher')]";
        click(driver, By.xpath(xpath));
    }

}
