package StepDefinitions.Carts;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import PojoClasses.Cart;
import PojoClasses.Prod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepDefinitionCarts {

    Response response;
    Cart requestBody;

    @Given("user sets the base URI")
    public void setBaseURI() {
        baseURI = "https://fakestoreapi.com";
    }

    @And("user prepares valid cart payload")
    public void preparePayload() {

        Prod product1 = new Prod(1, 2);
        Prod[] products = new Prod[]{product1};

        requestBody = new Cart(1, "2020-03-02", products);
    }

    @When("user sends POST request to create cart")
    public void sendPostRequest() {
        response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/carts");
        response.prettyPrint();
    }

    @Then("user should receive status code {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("response time should be less than {int} ms")
    public void verifyResponseTime(int time) {
        response.then().time(lessThan((long) time));
    }

    @And("response should contain created cart details")
    public void validateResponse() {

        response.then()
                .body("id", notNullValue())
                .body("userId", notNullValue())
                .body("date", notNullValue())
                .body("products", not(empty()))
                .body("products.productId", everyItem(notNullValue()))
                .body("products.quantity", everyItem(greaterThan(0)));
    }
}