Feature: Login API

  Scenario: Login with valid credentials
    Given BaseURL is set for login
    And I have valid login credentials
    When I send a POST request to "/auth/login" for login
    Then the response status code should be 201
    And validate token is generated
    And the response time should be less than 4000 ms in login