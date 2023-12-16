Feature: Search in Google

  @google
  Scenario: Open and search in google
    Given google is opened
    When google search is done
      | SEARCH |
      | Mango  |
    Then search result is displayed
      | SEARCH                           |
      | A mango is an edible stone fruit |
