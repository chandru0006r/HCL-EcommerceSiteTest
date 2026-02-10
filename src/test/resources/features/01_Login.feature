Feature: 01 Login Verification

  Scenario: Verify user session logic
    Given the browser is open and session is initialized
    Then the user should be logged in with a valid session cookie
