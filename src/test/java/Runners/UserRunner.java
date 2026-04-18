package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features/UserFeature.feature",
        glue = "StepDefinitions.User",
        plugin = {"pretty"},
        monochrome = true
)
public class UserRunner extends AbstractTestNGCucumberTests {
}