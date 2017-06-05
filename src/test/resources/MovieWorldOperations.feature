Feature: CRUD operations on Movie
	Scenario: Client POST a new Movie
		Given movie details
		|title|year|
		|My Test Movie|2017|
		And movie info to be
		|release_date|rating|image_url|plot|rank|running_time_secs|
		|01/01/2017|8.8|some image|some plot|110|5820|
		And directors to be
		|name|
		|first director|
		|second director|
		And actors to be
		|name|
		|male lead|
		|female lead|
		And genres to be
		|type|
		|Action|
		|Comedy|
		When the client calls POST /movies
		Then the client receives status code of 200
		And movie should have an id
		And movie should be
		|title|year|
		|My Test Movie|2017|
	Scenario: Client can GET Movie
		When Client requests for a movie by Id that exists "true"
		Then the client receives status code of 200
		And Client should receive a movie in the response
		When Client requests for a movie by Id that exists "false"
#		Then the client receives status code of TODO - TODO
	Scenario: Client can PUT Movie
		When Client requests to update a movie by Id that exists "true"
		Then the client receives status code of 200
#		When Client requests to update a movie by Id that exists "false"
#		Then the client receives status code of TODO	- TODO	
	Scenario: Client can DELETE Movie
		When Client requests to delete a movie by Id that exists "true"
		Then the client receives status code of 200
#		When Client requests to delete a movie by Id that exists "false"
#		Then the client receives status code of TODO	- TODO	