Feature: User module

  Scenario: Add a new user
    Given the FakeStore User API is available
    And the user payload is prepared
    When the user sends a POST request to create a new user
    Then the user should be created successfully with status code 201

  Scenario: Get a single user
    Given the FakeStore User API is available
    And the user id is 1
    When the user sends a GET request to fetch the single user
    Then the single user should be fetched successfully with status code 200

  Scenario: Get all users
    Given the FakeStore User API is available
    When the user sends a GET request to fetch all users
    Then all users should be fetched successfully with status code 200