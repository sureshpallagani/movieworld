Feature: Movies Search and Pagination features
	Scenario: Client makes call to GET /movies
		When the client calls /movies
		Then the client receives status code of 200
		And client receives 20 movies
	Scenario: Client makes paginated call to GET /movies
		Given page is 3 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
		And client receives 5 movies
		And response should have "firstPage" link
		And response should have "lastPage" link
		And response should have "self" link
		And response should have "previousPage" link
		And response should have "nextPage" link
	Scenario: Client makes paginated call to GET /movies for first page
		Given page is 0 and size is 5
		When the client calls /movies
		Then the client receives status code of 200
		And client receives 5 movies