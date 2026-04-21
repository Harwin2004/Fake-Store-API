package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features/AuthFeature.feature",
        glue = "StepDefinitions.Auth",
        plugin = {"pretty", "html:target/fake-report.html"},
        monochrome = true
)
public class AuthRunner extends AbstractTestNGCucumberTests {
	
}