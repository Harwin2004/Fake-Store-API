Feature: FakeStore User API Validation (Strict)

  # ================= CREATE USER =================
  Scenario: Create user with valid details
    And the user payload is:
      | id | username | email            | password |
      | 21 | tester   | tester@gmail.com | 12345    |
    When the user sends a POST request to create a new user
    Then verify status code is 201
    And validate created user response
    And verify response time is under 2000 ms

  # ================= NEGATIVE CREATE USER USING EXCEL =================
  Scenario: Create user with invalid data using excel
    When the user reads data from "src/test/resources/Data/User_Negative_Post_Data.xlsx" sheet "Invalid_Post_Data"
    And the user sends POST request with excel data
    Then validate negative post execution from excel

  # ================= GET ALL USERS =================
  Scenario: Retrieve all users
    When the user sends a GET request to fetch all users
    Then verify status code is 200
    And validate user list details
    And verify response time is under 2000 ms

  # ================= GET SINGLE USER =================
  Scenario Outline: Retrieve user with multiple ids
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
    And the user id is 1
    And the updated user payload is:
      | username     | email               | password |
      | updatedUser  | updated@gmail.com   | 9999     |
    When the user sends a PUT request to update the user
    Then verify status code is 200
    And validate updated user response
    And verify response time is under 2000 ms

  # ================= UPDATE USER (DATA TABLE) =================
  Scenario: Update user using data table
    And the user id is 1
    And the updated user payload is:
      | username     | email              | password |
      | updatedUser  | updated@gmail.com  | 9999     |
    When the user sends a PUT request to update the user
    Then verify status code is 200
    And validate updated user response

  # ================= UPDATE USER (INVALID ID) =================
  Scenario: Update user with invalid id
    And the user id is 9999
    And the updated user payload is:
      | username     | email              | password |
      | invalidUser  | invalid@gmail.com  | 1234     |
    When the user sends a PUT request to update the user
    Then verify status code is 404
    And validate error response

  # ================= DELETE USER =================
  Scenario Outline: Delete user with multiple ids
    And the user id is <id>
    When the user sends a DELETE request for the user
    Then verify status code is <status>

    Examples:
      | id   | status |
      | 1    | 200    |
      | 9999 | 404    |
      | -1   | 400    |

  # ================= DELETE USER (VALID IDS) =================
  Scenario Outline: Delete user with valid ids
    And the user id is <id>
    When the user sends a DELETE request for the user
    Then verify status code is 200

    Examples:
      | id |
      | 1  |
      | 2  |
      | 3  |

  # ================= DELETE USER (INVALID IDS - EXCEL) =================
  Scenario: Delete user with invalid ids from excel
    When the user reads data from "src/test/resources/Data/UserTestData.xlsx" sheet "InvalidUsers"
    And the user sends DELETE request using excel data
    Then validate status code from excel