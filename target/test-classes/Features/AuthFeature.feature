Feature: Login API

  Scenario: Login with valid credentials
  Given BaseURL is set for login
  And I have valid login credentials
  When I send a POST request to "/auth/login" for login
  Then the response status code should be 201
  And validate token is generated
  And the response time should be less than 4000 ms in login
    
  Scenario: Attempt login with missing username
  Given BaseURL is set for login
  And I have login credentials with missing username
  When I send a POST request to "/auth/login" for login
  Then the response status code should be 400
  And the response time should be less than 4000 ms in login
    
  Scenario: Attempt login with missing password
  Given BaseURL is set for login
  And I have login credentials with missing password
  When I send a POST request to "/auth/login" for login
  Then the response status code should be 400
  And the response time should be less than 4000 ms in login
  
  Scenario: Attempt login with empty body
  Given BaseURL is set for login
  And I have empty login request body
  When I send a POST request to "/auth/login" for login with empty body
  Then the response status code should be 400
  And the response time should be less than 4000 ms in login
  