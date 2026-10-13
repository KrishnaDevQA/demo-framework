package utils.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver() {return driver.get();}
    public static void setDriver(WebDriver driverInstance) {driver.set(driverInstance);}
    public static void unload() {driver.remove();}
}
