package com.pages;

import com.base.TestBase;
import com.util.AbstractDataLibrary;
import com.util.ReusableLibrary;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.lang.ref.SoftReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import static com.util.AbstractDataLibrary.carDetailsMap;

public class HomePage extends TestBase {


    SoftAssert softAssert = new SoftAssert();

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[placeholder='Enter reg']")
    WebElement enterRegTextBox;

    @FindBy(css = "button[type='submit']")
    WebElement checkNowButton;

    @FindBy(xpath = "//div[contains(text(),'license plate number is not recognised')]")
    WebElement licensePlateNumberNotRecognized;

    public By carGeneralInfoValue(String labelName) {
        return By.xpath("//tr/td[@class='td-left'][text()='" + labelName + "']/following-sibling::*");
    }

    public String getPageTitle(WebDriver driver) {
        if (driver != null) {
            return driver.getTitle();
        } else {
            throw new NullPointerException("WebDriver is not initialized.");
        }
    }

    //this method will verify the page title contains the expected title
    public void verityPageTitle(String expPageTitle) {
        waitForPageToLoad(Duration.ofSeconds(30));
        Assert.assertTrue(getPageTitle(driver).contains(expPageTitle), "Page title mismatch expected : '" + expPageTitle + "' but actual is :  '" + getPageTitle(driver) + "'");
    }

    public void getCarsDetail(ArrayList<String> carRegLst) {
        if (carRegLst != null && !carRegLst.isEmpty()) {

            for (String carReg : carRegLst) {
                enterRegTextBox.sendKeys(carReg);
                waitForPageToLoad(Duration.ofSeconds(60));
                elementClick(driver,checkNowButton);
//                verityPageTitle("Car-Checking");
                try {
                    if (licensePlateNumberNotRecognized.isDisplayed()) {
                        softAssert.fail("License plate number is not recognized: " + carReg);
                        System.out.println("License plate number is not recognized: " + carReg);
                        navigateToUrl(properties.getProperty("App_URL"));
                        continue;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("License plate number is recognized: " + carReg);
                }
                HashMap<AbstractDataLibrary.CarData, String> carDataMap = new HashMap<>();
                /* Get all Car parameters to check, from AbstractDataLibrary, future additional parameters can be added to the enum.
                 it works as long as it is present on website.*/
                for (AbstractDataLibrary.CarData carData : AbstractDataLibrary.CarData.values()) {
                    String label = carData.getLabel();
                    String value = driver.findElement(carGeneralInfoValue(label)).getText();
                    carDataMap.put(carData, value);
                }
                carDetailsMap.put(carReg, carDataMap);
                navigateToUrl(properties.getProperty("App_URL"));
            }

            // You can now use carDetailsMap as needed
        } else {
            System.out.println("Car registration list is empty or null.");
        }
    }
}