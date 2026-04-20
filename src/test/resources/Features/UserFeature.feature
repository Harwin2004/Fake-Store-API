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
   
  Scenario: Get user with invalid id
  	Given the FakeStore User API is available
  	And the user id is 9999
	When the user sends a GET request to fetch the single user
	Then the user should not be found and status code should be 404
    
  Scenario: Update user with valid id
    Given the FakeStore User API is available
    And the user id is 1
    And the updated user payload is prepared
    When the user sends a PUT request to update the user
    Then the user should be updated successfully with status code 200
    And the updated fields should be reflected in the response

  Scenario: Delete user with valid id
    Given the FakeStore User API is available
    And the user id is 1
    When the user sends a DELETE request for the user
    Then the user should be deleted successfully with status code 200

  Scenario: Delete user with invalid id
    Given the FakeStore User API is available
    And the user id is 9999
    When the user sends a DELETE request for the user
    Then the user should not be found and status code should be 404