Feature: Movies can be retrieved
	Scenario: Client makes call to POST /movies
		Given movie
		|title|year|
		|My Test Movie|2017|
		Given directors
		|name|
		|first director|
		|second director|
		Given actors
		|name|
		|male lead|
		|female lead|
		Given genres
		|type|
		|Action|
		|Comedy|
		Given movie info
		|release date|rating|image url|plot|rank|running time|
		|01/01/2017|8.8|some image|some plot|110|5820|
		When the client calls POST /movies
		Then the client receives status code of 200
		And movie should have an id
		And movie should be
		|My Test Movie|2017|
		And movie info should be
		|01/01/2017|8.8|some image|some plot|110|5820|
		And directors should be
		|first director|
		|second director|
		And actors should be
		|male lead|
		|female lead|
		And genres should be
		|Action|
		|Comedy|