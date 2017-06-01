Feature: Movies can be retrieved
	Scenario: Client makes call to GET /movies
		When the client calls /movies
		Then the client receives status code of 200
		And client receives list of movies
	Scenario: Client makes paginated call to GET /movies
		Given page is 3 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
		And client receives 5 movies
		And is first page is "false" and is last page is "false"
		And total pages should be 5
	Scenario: Client makes paginated call to GET /movies for first page
		Given page is 0 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
		And client receives 5 movies
		And is first page is "true" and is last page is "false"
		And total pages should be 5		
	Scenario: Client makes paginated call to GET /movies for last page
		Given page is 4 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
		And client receives 2 movies
		And is first page is "false" and is last page is "true"
		And total pages should be 5				