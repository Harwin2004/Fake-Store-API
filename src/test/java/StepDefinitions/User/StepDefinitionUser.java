package StepDefinitions.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import Constants.Endpoints;
import PojoClasses.User;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StepDefinitionUser {

    Response response;
    User userPayload;

    @Given("the FakeStore User API is available")
    public void the_fake_store_user_api_is_available() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Given("the user payload is prepared")
    public void the_user_payload_is_prepared() {
        userPayload = new User(21, "aamirfawaz", "aamir@gmail.com", "12345");
    }

    @When("the user sends a POST request to create a new user")
    public void the_user_sends_a_post_request_to_create_a_new_user() {
        response = given()
                .contentType(ContentType.JSON)
                .body(userPayload)
        .when()
                .post(Endpoints.USERS_POST);
    }

    @Then("the user should be created successfully with status code {int}")
    public void the_user_should_be_created_successfully_with_status_code(Integer statusCode) {
        response.then().log().all();
        response.then().statusCode(statusCode);

        response.then()
                .body("id", notNullValue());
    }
}