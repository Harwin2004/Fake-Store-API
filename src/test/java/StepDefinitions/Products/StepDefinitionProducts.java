package StepDefinitions.Products;

import org.testng.Assert;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StepDefinitionProducts {
	

	    Response response;
	    PojoClasses.Product p;
	    


	    @Given("BaseURL is set for product")
	    public void apiIsUp() {
	        RestAssured.baseURI = "https://fakestoreapi.com";
	    }
	    
	  

	    @Given("I have a valid product payload with title, price, description and category in product")
	    public void setProductPayload() {
	    	
	    	p=new PojoClasses.Product(2,"Mens Casual Premium Slim Fit T-Shirt" , 100, "Comfortable and stylish slim fit t-shirt made from high-quality cotton", "men's clothing", "https://urturms.com/cdn/shop/files/02_ae9d1db6-d9c9-48e7-b266-08f4bb4ee33c.jpg?v=1733376000");	
    	
	    }

	    @When("I send a POST request to {string} in product")
	    public void sendPOSTRequest(String endpoint) {
	        response = RestAssured.given()
	                .header("Content-Type", "application/json")
	                .auth().basic(endpoint, endpoint)
	                .body(p)
	                .post(endpoint);
	    }
	    
	    @When("I have send a GET request to {string} retrieve all products")
	    public void get_request(String endpoint) {
	        response =  RestAssured.given()
	                    .when()
	                    .get(endpoint);
	    }
	    
	    @When("I send a GET request to {string} with valid product id")
	    public void getSingleRequest(String endpoint) {
	        response =  RestAssured.given()
	                    .when()
	                    .get(endpoint);
	    }
	    
	    @When("I send a GET request to {string} with invalid product id")
	    public void getRequestInvalid(String endpoint) {
	        response =  RestAssured.given()
                    .when()
                    .get(endpoint);
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
	    
	    @Then("validate the title , price and category in product")
	    public void validateResponseBodyData() {

	        Assert.assertEquals(response.jsonPath().getString("title"), "Mens Casual Premium Slim Fit T-Shirt");
	        Assert.assertEquals(response.jsonPath().getInt("price"), 100);
	        Assert.assertEquals(response.jsonPath().getString("category"), "men's clothing");

	    }

	    @Then("the response time should be less than {int} ms for product")
	    public void validateResponseTime(int time) {
	        Assert.assertTrue(response.getTime() < time);
	    }
	    
	    @Then("the product ID in response should match the requested ID")
	    public void validateID() {
	        Assert.assertEquals(response.jsonPath().getInt("id"), 4);
	
	    }
	

}
