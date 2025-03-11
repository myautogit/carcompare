package com.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import static com.util.AbstractDataLibrary.*;

public class TestBase {
    public static WebDriver driver;
    public static Properties properties;
    public static String browserName;

    //use for future parallel execution
    public static ThreadLocal<WebDriver> thDriver = new ThreadLocal<WebDriver>();

    public String getBrowserName() {
        return properties.getProperty("Browser");
    }
    public WebDriver wdInitialization(){

        browserName = getBrowserName();
        if(browserName.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--ignore-certificate-errors");
            driver = new ChromeDriver(options);
        } else if (browserName.equals("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        thDriver.set(driver);
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return thDriver.get();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void initializeProperties() {
        FileInputStream ipStr = null;
        if(properties==null) {
            properties = new Properties();
            try {
                ipStr = new FileInputStream(configFilePath);
                properties.load(ipStr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(ipStr!= null){
                        ipStr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }
}
