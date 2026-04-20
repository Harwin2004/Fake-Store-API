Feature: Product API

Background:
    Given BaseURL is set for product
    
  Scenario: Create product with valid details
    Given I have a valid product payload with title, price, description and category in product
    When I send a POST request to "/products" in product
    Then the response status code should be 201 for product
    And the response should contain product details
    And validate the title , price and category in product
    And the response time should be less than 5000 ms for product
    
    
  Scenario: Retrieve all products with valid request 

    When I have send a GET request to "/products" retrieve all products
    Then the response status code should be 200 for product
    And the response should contain product details 
    And the response time should be less than 3000 ms for product
    

  Scenario: Retrieve single product using valid product id
   
    When I send a GET request to "/products/4" with valid product id
    Then the response status code should be 200 for product
    And the response should contain product details
    And the product ID in response should match the requested ID
    
   
   Scenario: Retrieve product with non-existing product id
 
    When I send a GET request to "/products/9999" with invalid product id 
    Then the response status code should be 404 for product
    And the response time should be less than 2000 ms for product
    
    
    Scenario: Retrieve product with negative product id
 
    When I send a GET request to "/products/-100" with invalid product id 
    Then the response status code should be 400 for product
    And the response time should be less than 2000 ms for product
    
    Scenario: Update product with valid id and modify fields
    Given I have a valid updated product payload with title, price, description and category
    When I send a PUT request to "/products/4" to update product
    Then the response status code should be 200 for product
    And the response should contain updated product details
    And validate the updated title , price and category in product
    And the response time should be less than 5000 ms for product
    
    Scenario: Attempt to delete product using valid or existing product id
    When I send a DELETE request to "/products/4" to delete product
    Then the response status code should be 200 for product
    And the response time should be less than 3000 ms for product
    
    Scenario: Attempt to delete product using invalid or non-existing product id
    When I send a DELETE request to "/products/9999" with invalid product id
    Then the response status code should be 404 for product
    And the response time should be less than 3000 ms for product
    
    Scenario: Attempt to delete product using negative product id
    When I send a DELETE request to "/products/-1" with negative product id
    Then the response status code should be 400 for product 
    And the response time should be less than 3000 ms for product
    
    
    