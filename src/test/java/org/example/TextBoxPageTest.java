package org.example;

import base_test.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.AssertionUtility;
import utils.ExtentReportListener;
import utils.driver.DriverManager;

public class TextBoxPageTest extends BaseTest {
    @BeforeClass
    public void openBrowser(){
        setUpBrowser();
    }
    @AfterClass
    public void closeBrowser(){
        tearDownBrowser();
    }

    @DataProvider(name = "textBoxFormData")
    public Object[][] textBoxFormData() {
        return new Object[][] {
                {
                        "John Doe",
                        "john.doe@test.com",
                        "Current Address 1",
                        "Permanent Address 1"
                },
                {
                        "Jane Smith",
                        "jane.smith@test.com",
                        "Current Address 2",
                        "Permanent Address 2"
                },
                {
                        "David Kumar",
                        "david.kumar@test.com",
                        "Current Address 3",
                        "Permanent Address 3"
                },
                {
                        "Priya Sharma",
                        "priya.sharma@test.com",
                        "Current Address 4",
                        "Permanent Address 4"
                },
                {
                        "Alex Wilson",
                        "alex.wilson@test.com",
                        "Current Address 5",
                        "Permanent Address 5"
                }
        };
    }

    @Test(priority = 0)
    public void verifyTextBoxFormSubmission1() {

        String fullName = "John Doe";
        String email = "john.doe@test.com";
        String currentAddress = "Current Address";
        String permanentAddress = "Permanent Address";

        TextBoxPage textBoxPage = new TextBoxPage(DriverManager.getDriver());
        textBoxPage.clickOnCard("Elements");
        textBoxPage.clickOnSidebarMenu("Text Box");
        textBoxPage.enterFullName(fullName);
        ExtentReportListener.getExtentTest().info("Full Name entered: " + fullName);
        textBoxPage.enterEmail(email);
        ExtentReportListener.getExtentTest().info("Email entered: " + email);
        textBoxPage.enterCurrentAddress(currentAddress);
        ExtentReportListener.getExtentTest().info("Current Address entered: " + currentAddress);
        textBoxPage.enterPermanentAddress(permanentAddress);
        ExtentReportListener.getExtentTest().info("Permanent Address entered: " + permanentAddress);

        textBoxPage.clickOnSubmitButton();
        ExtentReportListener.getExtentTest().info("Clicked submit button");

        String fullNameOutput = textBoxPage.getFullNameOutputText();
        AssertionUtility.assertWithHtmlLogging(fullNameOutput.contains(fullName),true,"Full name output verification");
    }

    @Test(priority = 1,dataProvider = "textBoxFormData")
    public void verifyTextBoxFormSubmission2(
            String fullName,
            String email,
            String currentAddress,
            String permanentAddress) {

        TextBoxPage textBoxPage = new TextBoxPage(DriverManager.getDriver());

        textBoxPage.enterFullName(fullName);
        ExtentReportListener.getExtentTest().info("Full Name entered: " + fullName);

        textBoxPage.enterEmail(email);
        ExtentReportListener.getExtentTest().info("Email entered: " + email);

        textBoxPage.enterCurrentAddress(currentAddress);
        ExtentReportListener.getExtentTest().info("Current Address entered: " + currentAddress);

        textBoxPage.enterPermanentAddress(permanentAddress);
        ExtentReportListener.getExtentTest().info("Permanent Address entered: " + permanentAddress);

        textBoxPage.clickOnSubmitButton();
        ExtentReportListener.getExtentTest().info("Clicked submit button");

        String fullNameOutput = textBoxPage.getFullNameOutputText();
        String emailOutput = textBoxPage.getEmailOutputText();
        String currentAddressOutput = textBoxPage.getCurrentAddressOutputText();
        String permanentAddressOutput = textBoxPage.getPermanentAddressOutputText();

        AssertionUtility.assertWithHtmlLogging(
                fullNameOutput.contains(fullName),
                true,
                "Full name output verification"
        );

        AssertionUtility.assertWithHtmlLogging(
                emailOutput.contains(email),
                true,
                "Email output verification"
        );

        AssertionUtility.assertWithHtmlLogging(
                currentAddressOutput.contains(currentAddress),
                true,
                "Current address output verification"
        );

        AssertionUtility.assertWithHtmlLogging(
                permanentAddressOutput.contains(permanentAddress),
                true,
                "Permanent address output verification"
        );
    }
}
