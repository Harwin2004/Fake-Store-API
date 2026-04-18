Feature: Cart API Functional Testing

  Scenario: Create cart with valid details
    Given user sets the base URI
    And user prepares valid cart payload
    When user sends POST request to create cart
    Then user should receive status code 201
    And response time should be less than 2000 ms
    And response should contain created cart details