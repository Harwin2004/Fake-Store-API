package StepDefinitions.Carts;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.*;

import PojoClasses.Cart;
import PojoClasses.Prod;
import Utility.ExcelUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

public class StepDefinitionCarts {

    Response response;
    Cart requestBody;

    // For Excel multiple rows
    List<Cart> excelCartList = new ArrayList<>();


    @And("user prepares cart payload with following products")
    public void prepareCartPayload(DataTable table) {

        List<Map<String, String>> data = table.asMaps(String.class, String.class);

        List<Prod> productList = new ArrayList<>();

        for (Map<String, String> row : data) {
            int productId = Integer.parseInt(row.get("productId"));
            int quantity = Integer.parseInt(row.get("quantity"));

            productList.add(new Prod(productId, quantity));
        }

        Prod[] products = productList.toArray(new Prod[0]);

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


    @When("user sends GET request to fetch all carts")
    public void sendGetAllCartsRequest() {

        response = given()
                .when()
                .get("/carts");

        response.prettyPrint();
    }


    @When("user sends GET request for cart with id {int}")
    public void sendGetRequest(int cartId) {

        response = given()
                .when()
                .get("/carts/" + cartId);
    }


    @When("user deletes cart with id {int}")
    public void deleteCart(int cartId) {

        response = given()
                .when()
                .delete("/carts/" + cartId);
    }


    @And("user reads updated cart data from Excel file {string}")
    public void readUpdatedCartDataFromExcel(String fileName) throws IOException {

        String path = System.getProperty("user.dir") + "/src/test/resources/Data/" + fileName;
        String sheetName = "Cart";

        excelCartList.clear();

        // Assume 3 rows (1,2,3)
        for (int i = 1; i <= 3; i++) {

            int userId = Integer.parseInt(ExcelUtil.getCellData(path, sheetName, i, 0));
            String date = ExcelUtil.getCellData(path, sheetName, i, 1);
            int productId = Integer.parseInt(ExcelUtil.getCellData(path, sheetName, i, 2));
            int quantity = Integer.parseInt(ExcelUtil.getCellData(path, sheetName, i, 3));

            Prod product = new Prod(productId, quantity);
            Prod[] products = new Prod[]{product};

            Cart cart = new Cart(userId, date, products);

            excelCartList.add(cart);
        }
    }


    @When("user sends PUT request to update cart with id {int}")
    public void sendPutRequest(int cartId) {

        for (Cart cart : excelCartList) {

            response = given()
                    .header("Content-Type", "application/json")
                    .body(cart)
                    .when()
                    .put("/carts/" + cartId);

            response.prettyPrint();

            // Validate each update
            response.then()
                    .statusCode(200)
                    .time(lessThan(2000L))
                    .body("id", notNullValue())
                    .body("products", not(empty()));
        }
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
    public void validateCreateResponse() {

        response.then()
                .body("id", notNullValue())
                .body("userId", notNullValue())
                .body("date", notNullValue())
                .body("products", not(empty()))
                .body("products.productId", everyItem(notNullValue()))
                .body("products.quantity", everyItem(greaterThan(0)));
    }

    @And("response should contain updated cart details")
    public void validateUpdatedResponse() {

        response.then()
                .body("id", notNullValue())
                .body("userId", notNullValue())
                .body("date", notNullValue())
                .body("products", not(empty()));
    }

    @And("response should contain cart list")
    public void validateCartList() {

        response.then()
                .body("$", not(empty()))
                .body("id", everyItem(notNullValue()))
                .body("userId", everyItem(notNullValue()))
                .body("date", everyItem(notNullValue()))
                .body("products", everyItem(not(empty())))
                .body("products.productId.flatten()", everyItem(notNullValue()))
                .body("products.quantity.flatten()", everyItem(greaterThan(0)));
    }
}