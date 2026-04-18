package StepDefinitions.Products;

import org.testng.Assert;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StepDefinitionProducts {
	

	    Response response;
	    String requestBody;
	    


	    @Given("BaseURL is set for product")
	    public void apiIsUp() {
	        RestAssured.baseURI = "https://fakestoreapi.com";
	    }
	    
	  

	    @Given("I have a valid product payload with title, price, description and category in product")
	    public void setProductPayload() {
	    	requestBody = "{\n" +
	    	        "\"id\": 2,\n" +
	    	        "\"title\": \"Mens Casual Premium Slim Fit T-Shirt\",\n" +
	    	        "\"price\": 100,\n" +
	    	        "\"description\": \"Comfortable and stylish slim fit t-shirt made from high-quality cotton, perfect for everyday wear.\",\n" +
	    	        "\"category\": \"men's clothing\",\n" +
	    	        "\"image\": \"https://urturms.com/cdn/shop/files/02_ae9d1db6-d9c9-48e7-b266-08f4bb4ee33c.jpg?v=1733376000\"\n" +
	    	        "}";
	    }

	    @When("I send a POST request to {string} in product")
	    public void sendPOSTRequest(String endpoint) {
	        response = RestAssured.given()
	                .header("Content-Type", "application/json")
	                .auth().basic(endpoint, endpoint)
	                .body(requestBody)
	                .post(endpoint);
	    }

	    @Then("the response status code should be {int}")
	    public void validateStatusCode(int statusCode) {
	        Assert.assertEquals(statusCode, response.getStatusCode());
	    }

	    @Then("the response should contain created product details")
	    public void validateResponseBody() {
	        Assert.assertNotNull(response.jsonPath().get("id"));
	        Assert.assertNotNull(response.jsonPath().get("title"));
	        Assert.assertNotNull(response.jsonPath().get("price"));
	        Assert.assertNotNull(response.jsonPath().get("description"));
	        Assert.assertNotNull(response.jsonPath().get("category"));
	        Assert.assertNotNull(response.jsonPath().get("image"));
	    }
	    
	    @Then("validate the title , price and category in product")
	    public void validateResponseBodyData() {

	        Assert.assertEquals(response.jsonPath().getString("title"), "Mens Casual Premium Slim Fit T-Shirt");
	        Assert.assertEquals(response.jsonPath().getInt("price"), 100);
	        Assert.assertEquals(response.jsonPath().getString("category"), "men's clothing");

	    }

	    @Then("the response time should be less than {int} ms in product")
	    public void validateResponseTime(int time) {
	        Assert.assertTrue(response.getTime() < time);
	    }
	

}
