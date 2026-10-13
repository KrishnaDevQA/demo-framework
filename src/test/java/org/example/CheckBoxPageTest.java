package org.example;

import base_test.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExtentReportListener;
import utils.driver.DriverManager;

public class CheckBoxPageTest extends BaseTest {

    @BeforeClass
    public void openBrowser(){
        setUpBrowser();
    }

    @AfterClass
    public void closeBrowser(){
        tearDownBrowser();
    }

    @Test(priority = 0)
    public void verifyCheckBox() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(DriverManager.getDriver());
        checkBoxPage.clickOnCard("Elements");
        checkBoxPage.clickOnSidebarMenu("Check Box");
        checkBoxPage.clickOnCheckBox("Home");
        ExtentReportListener.getExtentTest().info("Click on the checkbox 'Home'");
        checkBoxPage.clickOnSwitcher("Home");
        ExtentReportListener.getExtentTest().info("Click on the switcher 'Home'");
    }

}
