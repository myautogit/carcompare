package com.pages;

import com.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends TestBase {


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
//    @FindBy(css = )

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public void verityPageTitle(String expPageTitle) {
        Assert.assertTrue(getPageTitle(driver).contains(expPageTitle),"Page title mismatch expected : '" + expPageTitle + "' but actual is :  '" + getPageTitle(driver) + "'");
    }
}
