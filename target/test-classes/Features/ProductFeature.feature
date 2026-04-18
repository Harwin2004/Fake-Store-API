Feature: Product API

Background:
    Given BaseURL is set for product
    
  Scenario: Create product with valid details
    Given I have a valid product payload with title, price, description and category in product
    When I send a POST request to "/products" in product
    Then the response status code should be 201
    And the response should contain created product details
    And validate the title , price and category in product
    And the response time should be less than 5000 ms in product
    
    
  