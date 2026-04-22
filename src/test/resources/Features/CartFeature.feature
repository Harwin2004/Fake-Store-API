Feature: Cart API Functional Testing

  Background:
    Given user sets the base URI


  # =========================
  # 1. CREATE CART (DataTable)
  # =========================
  Scenario: Create cart with multiple products using DataTable
    And user prepares cart payload with following products
      | productId | quantity |
      | 1         | 2        |
      | 2         | 3        |
      | 3         | 1        |
    When user sends POST request to create cart
    Then user should receive status code 201
    And response time should be less than 2000 ms
    And response should contain created cart details


  # =========================
  # 2. GET ALL CARTS (Normal)
  # =========================
  Scenario: Retrieve all carts and validate response
    When user sends GET request to fetch all carts
    Then user should receive status code 200
    And response time should be less than 2000 ms
    And response should contain cart list


  # =========================
  # 3. GET CART BY ID (Scenario Outline)
  # =========================
  Scenario Outline: Retrieve cart with different cart IDs
    When user sends GET request for cart with id <cartId>
    Then user should receive status code <statusCode>
    And response time should be less than <time> ms

  Examples:
    | cartId | statusCode | time |
    | 1      | 200        | 2000 |
    | 9999   | 404        | 2000 |
    | -1     | 400        | 2000 |


  # =========================
  # 4. UPDATE CART (Excel Data)
  # =========================
  Scenario: Update cart using Excel data
    And user reads updated cart data from Excel file "TestCases.xlsx"
    When user sends PUT request to update cart with id 1
    Then user should receive status code 200
    And response time should be less than 2000 ms
    And response should contain updated cart details


  # =========================
  # 5. DELETE CART (Scenario Outline)
  # =========================
  Scenario Outline: Delete cart with different cart IDs
    When user deletes cart with id <cartId>
    Then user should receive status code <statusCode>
    And response time should be less than <time> ms

  Examples:
    | cartId | statusCode | time |
    | 1      | 200        | 2000 |
    | 9999   | 404        | 2000 |
    | -1     | 400        | 2000 |