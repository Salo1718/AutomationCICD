@tag
Feature: Error Validation
  I want to use this template for my feature file


  @ErrorValidation
  Scenario Outline: Title of your scenario outline
    Given I landed on Ecommerce Page
    When Logged in with username <name> and password <password>
    When I check for the <value> in step
    Then "Incorrect email or password." message is displayed

     Examples: 
      | name            | password | 
      | srk17@gmail.com | Loni@18  |  
