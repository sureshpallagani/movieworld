Feature: CRUD operations on Movie for MongoDB
	Scenario: Client can GET Movie from Mongo
		When Client requests for a movie by Id that exists in Mongo "true"
		Then the client receives status code of 200
		When Client requests for a movie by Id that exists in Mongo "false"
		Then the client receives status code of 404
