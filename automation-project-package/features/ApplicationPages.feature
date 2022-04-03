Feature: User Login
Description: User should be able to login using portal

@smoke
Scenario: User performs registration
	Given user opens home page
	And opens "REGISTER" page
	And enters following data in "REGISTER" page
		|FIRST_NAME	|LAST_NAME	|
		|TESTING1	|TESTING2	|
	

