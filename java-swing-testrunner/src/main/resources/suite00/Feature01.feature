Feature: Sample feature

  Scenario: Navigation
    Given user opens google.com
    When page is opened
    Then user search for something

  Scenario: Searching
    Given user search for something in google.com
    When search results are displayed
    Then user opens one of the search results