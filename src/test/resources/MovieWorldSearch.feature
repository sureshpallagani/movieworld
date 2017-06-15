Feature: Movies Search and Pagination features
	Scenario: Client makes call to GET /movies
		When the client calls /movies
		Then the client receives status code of 200
		And client receives list of movies
	Scenario: Client makes paginated call to GET /movies
		Given page is 3 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
#		And client receives 5 movies -- TODO
#		And is first page is "false" and is last page is "false"
	Scenario: Client makes paginated call to GET /movies for first page
		Given page is 0 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
#		And client receives 5 movies -- TODO
#		And is first page is "true" and is last page is "false"
	Scenario: Client makes paginated call to GET /movies for last page
		Given page is last and size is 5
		When the client calls /movies
		Then the client receives status code of 200
