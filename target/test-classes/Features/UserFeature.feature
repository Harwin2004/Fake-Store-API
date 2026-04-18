Feature: User module - Create User

  Scenario: Add a new user
    Given the FakeStore User API is available
    And the user payload is prepared
    When the user sends a POST request to create a new user
    Then the user should be created successfully with status code 201