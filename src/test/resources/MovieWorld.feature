Feature: Movies can be retrieved
	Scenario: Client makes call to GET /movies
		When the client calls /movies
		Then the client receives status code of 200
		And client receives list of movies