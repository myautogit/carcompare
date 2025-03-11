package com.steps;

import com.base.TestBase;
import com.pages.HomePage;
import com.util.ReusableLibrary;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class CarCompare extends TestBase{

    private static TestBase testBase = new TestBase();
    private static HomePage homePage = new HomePage(driver);
    private static ReusableLibrary reusableLibrary = new ReusableLibrary(driver);

    @BeforeAll
    public static void setUp() {
        testBase.initializeProperties();
        System.out.println("setup executed");
    }

    @After
    public static void cleanUp() {
        if(driver!=null) {
            driver.quit();
        }

    }
    @Given("User open browser")
    public void userOpenBrowser() {
        driver = testBase.wdInitialization();

        System.out.println("user open browser executed");

    }

    @When("Navigate to url {string}")
    public void navigateToUrl(String url) {
        testBase.navigateToUrl(url);
    }

    @Then("Verify page title contains {string}")
    public void verifyPageTitleContains(String pageTitle) {
        homePage.verityPageTitle(pageTitle);

    }

    @When("Read Car registration from {string} file")
    public void readCarRegistrationFromFile(String inputFileName) {
        System.out.println(reusableLibrary.carRegList);
    }
}
