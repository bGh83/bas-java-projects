Feature: User Login
Description: User should be able to login using portal

@smoke01
Scenario Outline: User performs login
	Given user opens the home page
	And enters username "<user name>"
	And enters password "<password>"
	When login button is clicked
	Then signon page is opened
	
Examples:
|user name|password|
|sample|test|
|simple|toast|
#|sea|coast|
#|simple|toast|
#|sea|coast|
#|sample|test|
#|simple|toast|
#|sea|coast|
#|simple|toast|
#|sea|coast|
#|sample|test|
#|simple|toast|
#|sea|coast|
#|simple|toast|
#|sea|coast|
#|sample|test|
#|simple|toast|
#|sea|coast|
#|simple|toast|
#|sea|coast|

#Scenario: User opens registration page
#	Given user opens the home page
#	When register link is clicked
#	Then registration page is opened


	
	