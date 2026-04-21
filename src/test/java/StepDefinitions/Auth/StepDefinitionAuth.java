package StepDefinitions.Auth;
import org.testng.Assert;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
public class StepDefinitionAuth {
	
	Response response;
    PojoClasses.Auth authObj;

    
    @Given("BaseURL is set for login")
    public void setBaseURI() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

   
    @Given("I have valid login credentials")
    public void setLoginPayload() {

        authObj = new PojoClasses.Auth("mor_2314", "83r5^_");
    }

  
    @When("I send a POST request to {string} for login")
    public void sendLoginRequest(String endpoint) {

        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(authObj)   
                .post(endpoint);
    }
    
    @Given("I have login credentials with missing username")
    public void setInvalidLoginPayload() {

        
        authObj = new PojoClasses.Auth(null, "83r5^_");
    }
   
    @Given("I have login credentials with missing password")
    public void setLoginPayloadMissingPassword() {

        authObj = new PojoClasses.Auth("mor_2314", null);
    }
    
 
    @Given("I have empty login request body")
    public void setEmptyLoginPayload() {
        authObj = null;   
    }

    
    @When("I send a POST request to {string} for login with empty body")
    public void sendLoginRequest1(String endpoint) {

        if (authObj == null) {
            
            response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body("{}")
                    .post(endpoint);
        } else {
            response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body(authObj)
                    .post(endpoint);
        }
    }
    
    @Then("the response status code should be {int}")
    public void validateStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

   
    @Then("validate token is generated")
    public void validateToken() {

        String token = response.jsonPath().getString("token");

        System.out.println("Generated Token: " + token);

        Assert.assertNotNull(token);
        Assert.assertFalse(token.isEmpty());
    }

  

    @Then("the response time should be less than {int} ms in login")
    public void validateResponseTime(int time) {

        Assert.assertTrue(response.getTime() < time);
    }
}
