package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features/ProductFeature.feature",
        glue = {"StepDefinitions.Products","Hooks"},
        plugin = {"pretty", "html:target/fake-report.html"},
        monochrome = true
)
public class ProductRunner extends AbstractTestNGCucumberTests {
	
}


