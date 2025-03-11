Feature: Find Car with registration and compare with output file

  Background: Test Car compare feature
    Given User open browser

  Scenario: Car data compare
    When Navigate to url "https://car-checking.com"
    Then Verify page title contains "Vehicle check"
    When Read Car registration from "car_input - V6.txt" file

    Scenario: test is dummy scenario
      When Navigate to url "https://car-checking.com"
