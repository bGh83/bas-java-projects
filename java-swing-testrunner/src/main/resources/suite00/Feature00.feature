Feature: Sample feature

  Scenario: Navigation
    Given user opens web page
      | WEB_PAGE   |
      | google.com |
    When page is opened
    Then user search for something
      | SEARCH |
      | MANGO  |

  Scenario: Searching
    Given user search for something in web page
      | WEB_PAGE   | SEARCH |
      | google.com | mango  |
    When search results are displayed
    Then user opens one of the search results