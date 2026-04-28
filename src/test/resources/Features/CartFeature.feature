Feature: Cart API Functional Testing



  Scenario: Create cart with multiple products using DataTable
    And user prepares cart payload with following products
      | productId | quantity |
      | 1         | 2        |
      | 2         | 3        |
      | 3         | 1        |
    When user sends POST request to create cart
    Then user should receive status code 201
    And response time should be less than 3000 ms
    And response should contain created cart details
	And response should match cart schema

  Scenario: Retrieve all carts and validate response
    When user sends GET request to fetch all carts
    Then user should receive status code 200
    And response time should be less than 2000 ms
    And response should contain cart list


  Scenario Outline: Retrieve cart with different cart IDs
    When user sends GET request for cart with id <cartId>
    Then user should receive status code <statusCode>
    And response time should be less than <time> ms

  Examples:
    | cartId | statusCode | time |
    | 1      | 200        | 2000 |
    | 9999   | 404        | 2000 |
    | -1     | 400        | 2000 |


  Scenario: Update cart using Excel data
    And user reads updated cart data from Excel file "Cart_TestCases.xlsx"
    When user sends PUT request to update cart with id 1
    Then user should receive status code 200
    And response time should be less than 2000 ms
    And response should contain updated cart details


  Scenario Outline: Delete cart with different cart IDs
    When user deletes cart with id <cartId>
    Then user should receive status code <statusCode>
    And response time should be less than <time> ms

  Examples:
    | cartId | statusCode | time |
    | 1      | 200        | 2000 |
    | 9999   | 404        | 2000 |
    | -1     | 400        | 2000 |

#For request chainig    
Scenario: Update cart userId using request chaining

  Given I send GET request to fetch cart with id 1
  And I store the cart response body
  When I update cart userId with random number
  And I send PUT request to update cart with id 1
  And the updated userId should be reflected