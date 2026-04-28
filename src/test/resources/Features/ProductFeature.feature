Feature: Product API
  
  Scenario Outline: Create product using Excel data

    Given I prepare product payload from excel "<rowNumber>"
    When I send a POST request for product
    Then the response status code should be 201 for product
    And validate product fields from excel "<rowNumber>"
    And the response time should be less than 5000 ms for product
    And response should match product schema

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
    Then the response status code should be 404 for product
    And the response time should be less than 2000 ms for product
    
    
    Scenario Outline: Retrieve product with negative product id
    
    When I send a GET request using negative product id <negativeId>
    Then the response status code should be 400 for product
    And the response time should be less than 2000 ms for product

		Examples:
		    | negativeId |
		    | -100       |
		    | -1         |
		    | -50        |





Scenario: Update product with valid id and modify fields from Excel

    Given I read product data from Excel row "<rowNumber>"
    And I have a valid updated product payload
    When I send a PUT request to update product
    Then the response status code should be 200 for product
    And the response should contain updated product details
    And validate the updated title , price and category in product
    And the response time should be less than 5000 ms for product

Examples:
    | rowNumber |
    | 1         |
    | 2         |
    | 3         |

Scenario Outline: Attempt to delete product using valid product id

    When I send a DELETE request to delete product "<PRODUCT_VALID_ID>"
    Then the response status code should be 200 for product
    And the response time should be less than 3000 ms for product

Examples:
    | PRODUCT_VALID_ID |
    | 1                |
    | 2                |
    | 3                |

Scenario: Attempt to delete product using invalid or non-existing product id

    When I send a DELETE request with invalid product id
        | PRODUCT_INVALID_ID |
        | 9999               |
        | 8888               |
        | 7777               |
    Then the response status code should be 404 for product using datatable
    And the response time should be less than 3000 ms for product using datatable

Scenario Outline: Attempt to delete product using negative product id

    Given I read negative product data from Excel row "<rowNumber>"
    When I send a DELETE request with negative product id
    Then the response status code should be 400 for product 
    And the response time should be less than 3000 ms for product

Examples:
    | rowNumber |
    | 1         |
    | 2         |
    | 3         |
    
#For request chaining
Scenario: Update product price using request chaining

  Given I send GET request to fetch product with id 1
  And I store the product response body
  When I update product price randomly
  And I send PUT request to update product
  Then the updated price should be reflected