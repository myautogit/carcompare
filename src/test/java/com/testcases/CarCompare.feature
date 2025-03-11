Feature: Find Car with registration and compare with output file

  Background: Test Car compare feature
    Given User open browser

  Scenario: Car data compare
    When Navigate to url "https://car-checking.com"
    Then Verify page title contains "Vehicle check"
    When Read Car registration from "CarInputFile" file and store
    And User make search for Cars registration and capture details
    Then Verify Car details captured match with "CarOutputFile" file
