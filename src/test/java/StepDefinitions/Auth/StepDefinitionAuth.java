package StepDefinitions.Auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import Constants.Endpoints;
import Utility.ExcelUtil;

public class StepDefinitionAuth {

    Response response;
    Map<String, Object> requestMap;   

   

    @Given("I read login data from excel row {string}")
    public void readLoginData(String rowNumber) throws IOException {

        int rowNum = Integer.parseInt(rowNumber);

        String path = System.getProperty("user.dir") + "/src/test/resources/Data/AUTH_TestCases.xlsx";
        String sheet = "Sheet1";  
        String username = ExcelUtil.getCellData(path, sheet, rowNum, 0);
        String password = ExcelUtil.getCellData(path, sheet, rowNum, 1);

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        requestMap = new HashMap<>();

        if (username != null && !username.isEmpty()) {
            requestMap.put("username", username);
        }

        if (password != null && !password.isEmpty()) {
            requestMap.put("password", password);
        }
    }


    @Given("I have login credentials")
    public void setLoginData(DataTable table) {

        List<Map<String, String>> data = table.asMaps();

        String username = data.get(0).get("username");
        String password = data.get(0).get("password");

        requestMap = new HashMap<>();

        if (username != null && !username.isEmpty()) {
            requestMap.put("username", username);
        }

        if (password != null && !password.isEmpty()) {
            requestMap.put("password", password);
        }
    }

    @Given("I have valid login credentials")
    public void setValidLogin() {

        requestMap = new HashMap<>();
        requestMap.put("username", "mor_2314");
        requestMap.put("password", "83r5^_");
    }

    @Given("I have username but no password")
    public void setUsernameOnly() {

        requestMap = new HashMap<>();
        requestMap.put("username", "john_doe");
    }

    @Given("I have username {string} and password {string}")
    public void setCredentials(String username, String password) {

        requestMap = new HashMap<>();

        if (!username.isEmpty()) {
            requestMap.put("username", username);
        }

        if (!password.isEmpty()) {
            requestMap.put("password", password);
        }
    }


    @When("I send a POST request for login")
    public void sendLoginRequest() {

        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestMap)
                .when()
                .post(Endpoints.AUTH_POST);

        System.out.println("Response: " + response.asString());
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }


    @Then("validate token is generated")
    public void validateToken() {

        String token = response.jsonPath().getString("token");

        System.out.println("Generated Token: " + token);

        Assert.assertNotNull(token, "Token is null");
        Assert.assertFalse(token.isEmpty(), "Token is empty");
    }


    @Then("the response time should be less than {int} ms in login")
    public void validateResponseTime(int time) {

        long responseTime = response.getTime();

        System.out.println("Response Time: " + responseTime);

        Assert.assertTrue(responseTime < time,
                "Response time is greater than expected: " + responseTime);
    }
}