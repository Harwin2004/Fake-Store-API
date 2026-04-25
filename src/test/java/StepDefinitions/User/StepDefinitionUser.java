package StepDefinitions.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import PojoClasses.User;
import Utility.ExcelUtility;

public class StepDefinitionUser {

    private Response response;
    private User payload;
    private String userId;

    private List<Map<String, String>> excelData;
    private List<String> resultLogs = new ArrayList<>();


    @And("the user id is {int}")
    public void setUserId(int id) {
        this.userId = String.valueOf(id);
    }

    // ================= CREATE USER =================

    @Given("the user payload is:")
    public void setUserPayload(DataTable table) {

        Map<String, String> data = table.asMaps().get(0);

        payload = new User(
                Integer.parseInt(data.get("id")),
                data.get("username"),
                data.get("email"),
                data.get("password")
        );
    }

    @When("the user sends a POST request to create a new user")
    public void createUser() {

        response =
            given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/users")
            .then()
                .log().all()
                .extract().response();
    }

    @Then("validate created user response")
    public void validateCreatedUser() {
        response.then().body("id", notNullValue());
    }

    // ================= GET ALL USERS =================

    @When("the user sends a GET request to fetch all users")
    public void getAllUsers() {

        response =
            given()
            .when()
                .get("/users")
            .then()
                .log().all()
                .extract().response();
    }

    @Then("validate user list details")
    public void validateUserList() {

        response.then()
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].username", notNullValue())
                .body("[0].email", notNullValue());
    }

    // ================= GET SINGLE USER =================

    @When("the user sends a GET request to fetch the single user")
    public void getSingleUser() {

        response =
            given()
            .when()
                .get("/users/" + userId)
            .then()
                .log().all()
                .extract().response();
    }

    // ================= UPDATE USER =================

    @And("the updated user payload is:")
    public void setUpdatedUser(DataTable table) {

        Map<String, String> data = table.asMaps().get(0);

        payload = new User();
        payload.setUsername(data.get("username"));
        payload.setEmail(data.get("email"));
        payload.setPassword(data.get("password"));
    }

    @When("the user sends a PUT request to update the user")
    public void updateUser() {

        response =
            given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .put("/users/" + userId)
            .then()
                .log().all()
                .extract().response();
    }

    @Then("validate updated user response")
    public void validateUpdatedUser() {

        response.then()
                .body("username", equalTo(payload.getUsername()))
                .body("email", equalTo(payload.getEmail()));
    }

    @And("validate error response")
    public void validateErrorResponse() {
        response.then().body(notNullValue());
    }

    // ================= DELETE USER =================

    @When("the user sends a DELETE request for the user")
    public void deleteUser() {

        response =
            given()
                .log().all()
            .when()
                .delete("/users/" + userId)
            .then()
                .log().all()
                .extract().response();
    }

    // ================= EXCEL (GENERIC READER) =================

    @When("the user reads data from {string} sheet {string}")
    public void readExcelData(String filePath, String sheetName) {

        excelData = ExcelUtility.getExcelData(filePath, sheetName);

        if (excelData == null || excelData.isEmpty()) {
            throw new RuntimeException(" Excel data is empty for sheet: " + sheetName);
        }

        System.out.println(" Excel Loaded → Sheet: " + sheetName + " Rows: " + excelData.size());
    }

    // ================= NEGATIVE POST FROM EXCEL =================

    @And("the user sends POST request with excel data")
    public void postFromExcel() {

        resultLogs.clear();

        for (Map<String, String> row : excelData) {

            Map<String, Object> requestBody = new HashMap<>();

            if (row.get("id") != null && !row.get("id").isEmpty()) {
                requestBody.put("id", (int) Double.parseDouble(row.get("id")));
            }
            if (row.get("username") != null) {
                requestBody.put("username", row.get("username"));
            }
            if (row.get("email") != null) {
                requestBody.put("email", row.get("email"));
            }
            if (row.get("password") != null) {
                requestBody.put("password", row.get("password"));
            }

            int expectedStatus = (int) Double.parseDouble(row.get("expectedStatus"));

            Response res =
                given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                .when()
                    .post("/users")
                .then()
                    .extract().response();

            int actualStatus = res.getStatusCode();

            if (actualStatus != expectedStatus) {
                throw new AssertionError(
                        " POST FAILED\nExpected: " + expectedStatus +
                        "\nActual: " + actualStatus +
                        "\nResponse: " + res.asString()
                );
            }

            resultLogs.add("POST → Expected: " + expectedStatus + " | Actual: " + actualStatus);
        }
    }

    // ================= DELETE FROM EXCEL =================

    @And("the user sends DELETE request using excel data")
    public void deleteFromExcel() {

        for (Map<String, String> row : excelData) {

            String id = row.get("id");
            int expectedStatus = (int) Double.parseDouble(row.get("status"));

            Response res =
                given()
            .when()
                .delete("/users/" + id)
            .then()
                .extract().response();

            int actualStatus = res.getStatusCode();

            if (actualStatus != expectedStatus) {
                throw new AssertionError(
                        " DELETE FAILED for ID: " + id +
                        "\nExpected: " + expectedStatus +
                        "\nActual: " + actualStatus
                );
            }
        }
    }

    @Then("validate negative post execution from excel")
    public void validateExcelResults() {

        System.out.println("====== EXECUTION SUMMARY ======");
        resultLogs.forEach(System.out::println);
    }

    @Then("validate status code from excel")
    public void validateDeleteExcel() {
        System.out.println(" Excel DELETE validation completed");
    }

    // ================= STATUS CODE =================

    @Then("verify status code is {int}")
    public void verifyStatusCode(int expectedStatus) {

        int actualStatus = response.getStatusCode();

        if (actualStatus != expectedStatus) {
            throw new AssertionError(
                "\n STATUS CODE MISMATCH\n" +
                "Expected: " + expectedStatus + "\n" +
                "Actual  : " + actualStatus + "\n" +
                "Response: " + response.asString()
            );
        }
    }

    // ================= RESPONSE TIME =================

    @And("verify response time is under {int} ms")
    public void validateResponseTime(int time) {

        response.then().time(lessThan((long) time));
    }
}