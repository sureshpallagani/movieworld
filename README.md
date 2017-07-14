# movieworld
Movie World project

# Setup
- Add your AWS credentials as server environment variables (aws_access_key_id, aws_secret_access_key).
- Build the project "mvn install". It should create all the required tables.
- All the movie data is stored in AWS S3 as json file. Use the "/setup - POST" endpoint to create movies data. Once you hit the POST endpoint, you will be given a status of your data setup request. The user should use GET on '/setup/{id}' to check the status. Once the status changes to COMPLETE, the data is ready.
- For API documentation use swagger UI -- "http://host:port/movieworld/swagger-ui.html".
- For setting up MongoDB, create your own database in MongoDB Cloud (https://cloud.mongodb.com). Add environment variable mongodb.cloud.uri with your MongoDB URI.
- For Mongo use /mongo/setup endpoint for data setup.

# Software
- Java 8.
- Spring RESTful API on Movies.
- AWS MySQL RDS and MongoDB as backend Database.
- Spring Boot 1.5.3.
- Spring Boot Actuator for metrics and monitoring.
- Spring Data Jpa, Paging and Sorting.
- AWS SDK to connect to AWS S3 to get the movies setup file from S3 bucket.
- Cucumber with Java step definitions and Gherkin features.
- Swagger 2 for API documentation.
- Spring HATEOAS for API Discoverability.
- /setup and /mongo/setup endpoints for POST and DELETE are Async operations.
- QueryDsl for Search functionalities on movies in MongoDB.
- ModelMapper is used in converting DAO <==> DTO for MySql solution.