package utils;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertionUtility {

    public static void assertWithHtmlLogging(Object actual, Object expected, String contextDescription) {
        try {
            Assert.assertEquals(actual, expected, "Mismatch for " + contextDescription + ", Expected: " + expected + ", Actual: " + actual);
            ExtentReportListener.getExtentTest().log(
                    Status.PASS,
                    "<b><span style='color:green;'>Assertion Passed:</span></b><br>" +
                            "<b>Description:</b> " + contextDescription + "<br>" +
                            "<b><span style='color:green;'>Expected:</span></b> " + expected + "<br>" +
                            "<b><span style='color:blue;'>Actual:</span></b> " + actual
            );
        } catch (AssertionError e) {
            ExtentReportListener.getExtentTest().log(
                    Status.FAIL,
                    "<b><span style='color:red;'>Assertion Failed:</span></b><br>" +
                            "<b>Description:</b> " + contextDescription + "<br>" +
                            "<b><span style='color:green;'>Expected:</span></b> " + expected + "<br>" +
                            "<b><span style='color:red;'>Actual:</span></b> " + actual
            );
            ExtentReportListener.logStepFailWithFullPageScreenshot();
            throw e;
        }
    }

    public static void assertWithHtmlLogging(SoftAssert softAssert, Object actual, Object expected, String contextDescription) {
        boolean isEqual = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        softAssert.assertEquals(actual, expected, "Mismatch for " + contextDescription + " for sample " + ", Expected: " + expected + ", Actual: " + actual);
        if (isEqual) {
            String logMessage = "<b><span style='color:green;'>Assertion Passed:</span></b><br>" +
                    "<b>Description:</b> " + contextDescription + "<br>" +
                    "<b><span style='color:green;'>Expected:</span></b> " + expected + "<br>" +
                    "<b><span style='color:blue;'>Actual:</span></b> " + actual;
            ExtentReportListener.getExtentTest().log(Status.PASS, logMessage);
        } else {
            String logMessage = "<b><span style='color:red;'>Assertion Failed:</span></b><br>" +
                    "<b>Description:</b> " + contextDescription + "<br>" +
                    "<b><span style='color:green;'>Expected:</span></b> " + expected + "<br>" +
                    "<b><span style='color:red;'>Actual:</span></b> " + actual;
            ExtentReportListener.logStepFailWithFullPageScreenshot(logMessage);
        }
    }


}
