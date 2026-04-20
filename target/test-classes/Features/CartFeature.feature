Feature: Cart API Functional Testing

  Scenario: Create cart with valid details
    Given user sets the base URI
    And user prepares valid cart payload
    When user sends POST request to create cart
    Then user should receive status code 201
    And response time should be less than 2000 ms
    And response should contain created cart details

  Scenario: Retrieve all carts and validate response
    Given user sets the base URI
    When user sends GET request to fetch all carts
    Then user should receive status code 200
    And response time should be less than 2000 ms
    And response should contain cart list

  Scenario Outline: Retrieve cart with different cart IDs and validate response
    Given user sets the base URI
    When user sends GET request for cart with id <cartId>
    Then user should receive status code <statusCode>
    And response time should be less than <time> ms

  Examples:
    | cartId | statusCode | time |
    | 1      | 200        | 2000  |
    | 9999   | 404        | 2000  |
    | -1     | 400        | 2000  |
    
  Scenario: Update cart with valid id
    Given user sets the base URI
    And user prepares updated cart payload
    When user sends PUT request to update cart with id 1
    Then user should receive status code 200
    And response time should be less than 2000 ms  
    And response should contain updated cart details
    
  Scenario Outline: Delete cart with different cart IDs
    Given user sets the base URI
    When user deletes cart with id <cartId>
    Then user should receive status code <statusCode>
    And response time should be less than <time> ms  

  Examples:
    | cartId | statusCode | time |
    | 1      | 200        | 2000 |
    | 9999   | 404        | 2000 |
    | -1     | 400        | 2000 |