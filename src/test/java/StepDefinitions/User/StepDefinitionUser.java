package StepDefinitions.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import Constants.Endpoints;
import PojoClasses.User;
import Utility.ExcelUtility;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;

public class StepDefinitionUser {

    Response response;
    User userPayload;
    int userId;
    String updateBody;

    // Excel data for negative POST
    List<Map<String, String>> excelData;
    List<String> resultLogs = new ArrayList<>();

    // ================= BASE =================
    @Given("the FakeStore User API is available")
    public void apiAvailable() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    // ================= COMMON =================
    @Given("the user id is {int}")
    public void setUserId(int id) {
        this.userId = id;
    }

    @Then("verify status code is {int}")
    public void verifyStatusCode(int expectedStatus) {
        int actual = response.getStatusCode();
        System.out.println("Status Code: " + actual);

        Assert.assertEquals(actual, expectedStatus,
                "Expected: " + expectedStatus + " but got: " + actual);
    }

    @Then("verify response time is under 2000 ms")
    public void verifyResponseTime() {
        long time = response.getTime();
        System.out.println("Response Time: " + time);
        Assert.assertTrue(time < 2000, "Response time is more than 2000 ms");
    }

    // ================= CREATE =================
    @Given("the user payload is:")
    public void setUserPayload(DataTable table) {
        Map<String, String> row = table.asMaps().get(0);

        userPayload = new User(
                Integer.parseInt(row.get("id")),
                row.get("username"),
                row.get("email"),
                row.get("password")
        );
    }

    @When("the user sends a POST request to create a new user")
    public void createUser() {
        response = given()
                .contentType(ContentType.JSON)
                .body(userPayload)
        .when()
                .post(Endpoints.USERS_POST);
    }

    @Then("validate created user response")
    public void validateCreatedUser() {
        response.then().body("id", notNullValue());
    }

    // ================= GET ALL =================
    @When("the user sends a GET request to fetch all users")
    public void getAllUsers() {
        response = given()
                .when()
                .get(Endpoints.USERS_GET_ALL_PRODUCT);
    }

    @Then("validate user list details")
    public void validateUserList() {
        response.then()
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].username", notNullValue())
                .body("[0].email", notNullValue());
    }

    // ================= GET SINGLE =================
    @When("the user sends a GET request to fetch the single user")
    public void getSingleUser() {
        response = given()
                .pathParam("id", userId)
        .when()
                .get(Endpoints.USERS_GET_SINGLE_PRODUCT + "/{id}");
    }

    // ================= UPDATE =================
    @Given("the updated user payload is:")
    public void setUpdatePayload(DataTable table) {
        Map<String, String> row = table.asMaps().get(0);

        updateBody = "{\n" +
                "\"username\": \"" + row.get("username") + "\",\n" +
                "\"email\": \"" + row.get("email") + "\",\n" +
                "\"password\": \"" + row.get("password") + "\"\n" +
                "}";
    }

    @When("the user sends a PUT request to update the user")
    public void updateUser() {
        response = given()
                .contentType(ContentType.JSON)
                .body(updateBody)
        .when()
                .put(Endpoints.USERS_POST + "/" + userId);
    }

    @Then("validate updated user response")
    public void validateUpdatedUser() {
        Assert.assertEquals(response.jsonPath().getString("username"), "updatedUser");
        Assert.assertEquals(response.jsonPath().getString("email"), "updated@gmail.com");
    }

    // ================= DELETE =================
    @When("the user sends a DELETE request for the user")
    public void deleteUser() {
        response = given()
        .when()
                .delete(Endpoints.USERS_POST + "/" + userId);
    }

    // ================= NEGATIVE POST USING EXCEL =================
    @When("the user reads invalid post data from {string} sheet {string}")
    public void readInvalidPostData(String filePath, String sheetName) {
        excelData = ExcelUtility.getExcelData(filePath, sheetName);

        if (excelData == null || excelData.isEmpty()) {
            throw new RuntimeException("Excel data is empty or file not found");
        }

        System.out.println("Excel loaded successfully: " + excelData.size() + " rows");
    }

    @When("the user sends POST request with excel data")
    public void sendPostRequestWithExcelData() {

        resultLogs.clear();

        for (Map<String, String> row : excelData) {

            String testCase = row.get("testcase");
            String idValue = row.get("id");
            String username = row.get("username");
            String email = row.get("email");
            String password = row.get("password");

            int expectedStatus = (int) Double.parseDouble(row.get("expectedStatus").trim());

            Map<String, Object> requestBody = new HashMap<>();

            if (idValue != null && !idValue.trim().isEmpty()) {
                requestBody.put("id", (int) Double.parseDouble(idValue.trim()));
            }
            if (username != null && !username.trim().isEmpty()) {
                requestBody.put("username", username.trim());
            }
            if (email != null && !email.trim().isEmpty()) {
                requestBody.put("email", email.trim());
            }
            if (password != null && !password.trim().isEmpty()) {
                requestBody.put("password", password.trim());
            }

            System.out.println("======================================");
            System.out.println("Executing TestCase: " + testCase);
            System.out.println("Request Body: " + requestBody);

            response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
            .when()
                    .post(Endpoints.USERS_POST);

            int actualStatus = response.getStatusCode();

            System.out.println("Expected Status: " + expectedStatus);
            System.out.println("Actual Status  : " + actualStatus);
            System.out.println("Response Body  : " + response.getBody().asPrettyString());

            resultLogs.add("TestCase: " + testCase + " | Expected: " + expectedStatus + " | Actual: " + actualStatus);

            Assert.assertEquals(actualStatus, expectedStatus,
                    "Failed for TestCase: " + testCase);
        }
    }

    @Then("validate negative post execution from excel")
    public void validateNegativePostExecutionFromExcel() {
        System.out.println("======== FINAL RESULT ========");
        for (String log : resultLogs) {
            System.out.println(log);
        }
    }
}