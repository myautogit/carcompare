package com.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import static com.util.AbstractDataLibrary.*;

public class TestBase {
    public static WebDriver driver;
    public static Properties properties;
    public static String browserName;
    public static WebDriverWait wait;

    //use for future parallel execution
    public static ThreadLocal<WebDriver> thDriver = new ThreadLocal<WebDriver>();

    public String getBrowserName() {
        return properties.getProperty("Browser");
    }

    public WebDriver wdInitialization() {
        initializeProperties();
        browserName = getBrowserName();
        if (browserName.equals("Chrome")) {
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
        if (properties == null) {
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
                    if (ipStr != null) {
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

    public static void elementClick(WebDriver driver, WebElement element) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(properties.getProperty("elementVisibilityDynamicWaitDuration"))));
            WebElement actionObject = wait.until(ExpectedConditions.elementToBeClickable(element));
            actionObject.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForPageToLoad(Duration DEFAULT_WAIT_TIME) {
        try {
            new FluentWait<WebDriver>(driver)
                    .withTimeout(DEFAULT_WAIT_TIME)
                    .pollingEvery(Duration.ofSeconds(30))
                    .until(new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            try {
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                if (js.executeScript("return document.readyState").equals("complete")) {
                                    if ((boolean) js.executeScript("return window.jQuery==undefined")) {
                                        return true;
                                    } else if ((boolean) js.executeScript("return jQuery.active==0")) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            } catch (Exception ex) {
                                return false;
                            }
                            return false;
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}