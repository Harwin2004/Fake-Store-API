Feature: Login API

Scenario Outline: Login using Excel data

    Given I read login data from excel row "<rowNumber>"
    When I send a POST request for login
    Then the response status code should be 201
    And validate token is generated
    And the response time should be less than 4000 ms in login

    Examples:
        | rowNumber |
        | 1         |

Scenario: Attempt login with missing username using DataTable

    Given I have login credentials
        | username | password     |
        |          | 83r5^_ |
    When I send a POST request for login
    Then the response status code should be 400

Scenario: Attempt login with missing password

    Given I have username but no password
    When I send a POST request for login
    Then the response status code should be 400


Scenario Outline: Attempt login with empty or invalid body

    Given I have username "<username>" and password "<password>"
    When I send a POST request for login
    Then the response status code should be 400

    Examples:
        | username | password |
        |          |          |