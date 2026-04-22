Feature: Product API


    
  Scenario Outline: Create product using Excel data

    Given I prepare product payload from excel "<rowNumber>"
    When I send a POST request for product
    Then the response status code should be 201 for product
    And validate product fields from excel "<rowNumber>"
    And the response time should be less than 5000 ms for product

		Examples:
		    | rowNumber |
		    | 1         |
		    | 2         |
		    | 3         |
    
  
  Scenario: Retrieve all products with valid request 

    When I have send a GET request to retrieve all products
    Then the response status code should be 200 for product
    And the response should contain product details 
    And the response time should be less than 3000 ms for product
    


    Scenario Outline: Retrieve single product using valid product id

    When I send a GET request with valid product id <PRODUCT_VALID_ID>
    Then the response status code should be 200 for product
    And validate product id from excel "<rowNumber>"

		Examples:
		    | rowNumber | PRODUCT_VALID_ID |
		    | 1         | 1                |
		    | 2         | 2                |
		    | 3         | 3                |
		    
   
   Scenario: Retrieve product with non-existing product id

    Given I have invalid product id:
        | productId |
        | 9999      |
        | 10267     |
        | 7864      |
    When I send a GET request using invalid product id
    Then the response status code should be 404 for product using datatable
    And the response time should be less than 2000 ms for product using datatable
    
    
    Scenario Outline: Retrieve product with negative product id
    
    When I send a GET request using negative product id <negativeId>
    Then the response status code should be 400 for product
    And the response time should be less than 2000 ms for product

		Examples:
		    | negativeId |
		    | -100       |
		    | -1         |
		    | -50        |
		    
    
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
    
    
    