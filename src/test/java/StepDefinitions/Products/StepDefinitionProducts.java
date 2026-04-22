package StepDefinitions.Products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import Utility.ExcelUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitionProducts {
	

	    Response response;
	    PojoClasses.Product p;
	    RequestSpecification request;
	    List<String> productIds;
	    List<Response> responses;



	    @Given("I prepare product payload from excel {string}")
	    public void prepareProductPayload(String rowNumber) throws IOException  {

	        int row = Integer.parseInt(rowNumber);

	        String path = "src/test/resources/Data/PRODUCT_Testcases.xlsx";
	        String sheet = "Sheet1";

	        String title = ExcelUtil.getCellData(path, sheet, row, 1);
	        String price = ExcelUtil.getCellData(path, sheet, row, 2);
	        String description = ExcelUtil.getCellData(path, sheet, row, 3);
	        String category = ExcelUtil.getCellData(path, sheet, row, 4);
	        String image = ExcelUtil.getCellData(path, sheet, row, 5);

	        Map<String, Object> payload = new HashMap<>();

	        payload.put("title", title);
	        payload.put("price", Double.parseDouble(price));
	        payload.put("description", description);
	        payload.put("category", category);
	        payload.put("image", image);

	        request = RestAssured.given()
	                .header("Content-Type", "application/json")
	                .body(payload);
	    }

	    @When("I send a POST request for product")
	    public void sendPOSTRequest() {
	        response = request
	                  .when()
	                .post("/products");
	    }
	    
	    @When("I have send a GET request to retrieve all products")
	    public void getRequest() {
	        response =  RestAssured.given()
	                    .when()
	                    .get("/products");
	    }
	    
	    @Given("I have invalid product id:")
	    public void getInvalidProductIds(DataTable dataTable) {

	        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

	        productIds = new ArrayList<>();

	        for (Map<String, String> row : data) {
	            productIds.add(row.get("productId"));
	        }
	    }
	    
	    
	    @When("I send a GET request with valid product id {int}")
	    public void getSingleRequest(int productID) {
	        response =  RestAssured.given()
	                    .when()
	                    .get("/products/"+productID);
	    }
	    
	    @When("I send a GET request using invalid product id")
	    public void getRequestInvalid() {

	        responses = new ArrayList<>();

	        for (String id : productIds) {

	            Response res = RestAssured.given()
	                            .when()
	                            .get("/products/" + id);

	            responses.add(res);
	        }
	    }
	    
	    @When("I send a GET request using negative product id {int}")
	    public void getRequestNegative(int negativeID) {
	        response =  RestAssured.given()
                    .when()
                    .get("/products/"+negativeID);
	    }
	      
	    @Given("I have a valid updated product payload with title, price, description and category")
	    public void setUpdatedProductPayload() {

	        p = new PojoClasses.Product(
	                4,
	                "Wireless Headphones Pro",
	                59.99,
	                "Updated premium wireless headphones",
	                "Electronics",
	                "https://example.com/updated-image.jpg"
	        );
	    }

	    @When("I send a PUT request to {string} to update product")
	    public void sendPUTRequest(String endpoint) {

	        response = RestAssured.given()
	                .header("Content-Type", "application/json")
	                .body(p)
	                .when()
	                .put(endpoint);
	    }
	    
	    @When("I send a DELETE request to {string} to delete product")
	    public void sendDELETERequest(String endpoint) {

	        response = RestAssured.given()
	                .when()
	                .delete(endpoint);
	    }
	    
	    @When("I send a DELETE request to {string} with invalid product id")
	    public void sendDELETERequestWithInvalid(String endpoint) {

	        response = RestAssured.given()
	                .when()
	                .delete(endpoint);
	    }
	    
	    @When("I send a DELETE request to {string} with negative product id")
	    public void sendDELETERequestWithNegativeId(String endpoint) {

	        response = RestAssured.given()
	                .when()
	                .delete(endpoint);
	    }
	    
	    @Then("the response should contain updated product details")
	    public void validateUpdatedResponseBody() {

	        Assert.assertNotNull(response.jsonPath().get("id"));
	        Assert.assertNotNull(response.jsonPath().get("title"));
	        Assert.assertNotNull(response.jsonPath().get("price"));
	        Assert.assertNotNull(response.jsonPath().get("description"));
	        Assert.assertNotNull(response.jsonPath().get("category"));
	        Assert.assertNotNull(response.jsonPath().get("image"));
	    }

	    @Then("validate the updated title , price and category in product")
	    public void validateUpdatedData() {

	        Assert.assertEquals(response.jsonPath().getString("title"), "Wireless Headphones Pro");
	        Assert.assertEquals(response.jsonPath().getDouble("price"), 59.99);
	        Assert.assertEquals(response.jsonPath().getString("category"), "Electronics");
	    }
	    @Then("the response status code should be {int} for product")
	    public void validateStatusCode(int statusCode) {
	        Assert.assertEquals(statusCode, response.getStatusCode());
	    }

	    @Then("the response should contain product details")
	    public void validateResponseBody() {
	        Assert.assertNotNull(response.jsonPath().get("id"));
	        Assert.assertNotNull(response.jsonPath().get("title"));
	        Assert.assertNotNull(response.jsonPath().get("price"));
	        Assert.assertNotNull(response.jsonPath().get("description"));
	        Assert.assertNotNull(response.jsonPath().get("category"));
	        Assert.assertNotNull(response.jsonPath().get("image"));
	    }
	    
	    @Then("validate product fields from excel {string}")
	    public void validateProductFields(String rowNumber) throws IOException {

	        int row = Integer.parseInt(rowNumber);

	        String path = "src/test/resources/Data/PRODUCT_Testcases.xlsx";
	        String sheet = "Sheet1";

	        String expectedTitle = ExcelUtil.getCellData(path, sheet, row, 1);
	        String expectedPrice = ExcelUtil.getCellData(path, sheet, row, 2);
	        String expectedCategory = ExcelUtil.getCellData(path, sheet, row, 4);

	        String actualTitle = response.jsonPath().getString("title");
	        float actualPrice = response.jsonPath().getFloat("price");
	        String actualCategory = response.jsonPath().getString("category");

	        Assert.assertEquals(actualTitle, expectedTitle);
	        Assert.assertEquals(String.valueOf(actualPrice), expectedPrice);
	        Assert.assertEquals(actualCategory, expectedCategory);
	    }

	    @Then("the response time should be less than {int} ms for product")
	    public void validateResponseTime(int time) {
	        Assert.assertTrue(response.getTime() < time);
	    }
	    
	    @Then("validate product id from excel {string}")
	    public void validateProductId(String rowNumber) throws IOException {

	        int row = Integer.parseInt(rowNumber);

	        String path = System.getProperty("user.dir") + "/src/test/resources/Data/PRODUCT_Testcases.xlsx";
	        String sheet = "Sheet1";

	        String expectedId = ExcelUtil.getCellData(path, sheet, row, 6);

	        int actualId = response.jsonPath().getInt("id");

	        Assert.assertEquals(String.valueOf(actualId), expectedId);
	    }
	    @Then("the response status code should be 404 for product using datatable")
	    public void validateStatusCodeTable() {

	        for (Response res : responses) {
	            Assert.assertEquals(res.getStatusCode(), 404);
	        }
	    }
	    @Then("the response time should be less than {int} ms for product using datatable")
	    public void validateResponseTimeTable(int time) {

	        for (Response res : responses) {
	            Assert.assertTrue(res.getTime() < time);
	        }
	    }
	

}
