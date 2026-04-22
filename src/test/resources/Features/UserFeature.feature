Feature: FakeStore User API Validation (Strict)

  # ================= CREATE USER =================
  Scenario: Create user with valid details
    Given the FakeStore User API is available
    And the user payload is:
      | id | username | email            | password |
      | 21 | tester   | tester@gmail.com | 12345    |
    When the user sends a POST request to create a new user
    Then verify status code is 201
    And validate created user response
    And verify response time is under 2000 ms
    
    # ================= NEGATIVE CREATE USER USING EXCEL =================
Scenario: Create user with invalid data using excel
  Given the FakeStore User API is available
  When the user reads invalid post data from "src/test/resources/Data/User_Negative_Post_Data.xlsx" sheet "Invalid_Post_Data"
  And the user sends POST request with excel data
  Then validate negative post execution from excel


  # ================= GET ALL USERS =================
  Scenario: Retrieve all users
    Given the FakeStore User API is available
    When the user sends a GET request to fetch all users
    Then verify status code is 200
    And validate user list details
    And verify response time is under 2000 ms


  # ================= GET SINGLE USER =================
  Scenario Outline: Retrieve user with multiple ids
    Given the FakeStore User API is available
    And the user id is <id>
    When the user sends a GET request to fetch the single user
    Then verify status code is <status>
    And verify response time is under 2000 ms

  Examples:
    | id   | status |
    | 1    | 200    |
    | 9999 | 404    |
    | -1   | 400    |


  # ================= UPDATE USER =================
  Scenario: Update user with valid id
    Given the FakeStore User API is available
    And the user id is 1
    And the updated user payload is:
      | username     | email               | password |
      | updatedUser  | updated@gmail.com   | 9999     |
    When the user sends a PUT request to update the user
    Then verify status code is 200
    And validate updated user response
    And verify response time is under 2000 ms


  # ================= DELETE USER =================
  Scenario Outline: Delete user with multiple ids
    Given the FakeStore User API is available
    And the user id is <id>
    When the user sends a DELETE request for the user
    Then verify status code is <status>

  Examples:
    | id   | status |
    | 1    | 200    |
    | 9999 | 404    |
    | -1   | 400    |