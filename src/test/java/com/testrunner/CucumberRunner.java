package com.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import static com.util.AbstractDataLibrary.*;

//@CucumberOptions(features = "src\\CarCompare.feature")
@CucumberOptions(features = "src/test/java/com/testcases/CarCompare.feature",glue = "steps",dryRun = true )
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
