package com.steps;

import com.base.TestBase;
import com.pages.HomePage;
import com.util.ReusableLibrary;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;


public class CarCompare extends TestBase{

    private static TestBase testBase = new TestBase();
    private static HomePage homePage;
    private static ReusableLibrary reusableLibrary;

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
        homePage = new HomePage(driver);
        reusableLibrary = new ReusableLibrary(driver);
        System.out.println("user open browser executed");
    }

    @When("Navigate to url {string}")
    public void navigateToUrl(String url) {
        testBase.navigateToUrl(url);
    }

    @Then("Verify page title contains {string}")
    public void verifyPageTitleContains(String pageTitle) {
        homePage.verityPageTitle(pageTitle);
        System.out.println("Verify page title contains : "+pageTitle);

    }




    @When("Read Car registration from {string} file and store")
    public void readCarRegistrationFromFileAndStore(String fileName) {
        System.out.println(reusableLibrary.getcarRegListArray(fileName));

    }

    @And("User make search for Cars registration and capture details")
    public void userMakeSearchForCarsRegistrationAndCaptureDetails() {
        homePage.getCarsDetail(reusableLibrary.carRegList);
        System.out.println("User make search for Cars registration and capture details");

    }

    @Then("Verify Car details captured match with {string} file")
    public void verifyCarDetailsCapturedMatchWithFile(String fileName) {
        reusableLibrary.getCarOutputDataAsString(fileName);
        reusableLibrary.compareCarData();
        System.out.println("Verify Car details captured match with Caroutput file executed");

    }
}
