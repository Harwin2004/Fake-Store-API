package Hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static io.restassured.RestAssured.*;

import Utility.PropertiesUtil;

public class Hooks {

    @Before
    public void setup(Scenario scenario) {

        try {
            baseURI = PropertiesUtil.getProperty("BASE_URI");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load BaseURI from config.properties", e);
        }

    }
}