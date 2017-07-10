# movieworld
Movie World project

# Setup
1. Add your AWS credentials as server environment variables (aws_access_key_id, aws_secret_access_key).
2. Build the project "mvn install". It should create all the required tables.
3. All the movie data is stored in AWS S3 as json file. Use the "/setup - POST" endpoint to create movies data. Once you hit the POST endpoint, you will be given a status of your data setup request. The user should use GET on '/setup/{id}' to check the status. Once the status changes to COMPLETE, the data is ready.
4. For API documentation use swagger UI -- "http://host:port/movieworld/swagger-ui.html".
5. For setting up MongoDB, create your own database in MongoDB Cloud (https://cloud.mongodb.com). Add environment variable mongodb.cloud.uri with your MongoDB URI.
6. For Mongo use /mongo/setup endpoint for data setup.

# Components
1. Spring RESTful API on Movies.
2. Uses AWS MySQL RDS as backend Database.
3. Spring Boot.
4. Spring Data Jpa and Hibernate.
5. AWS SDK to connect to AWS S3 to get the movies setup file from S3 bucket.
6. Cucumber with Java step definitions.
7. Swagger for API documentation.
8. Spring HATEOAS used for API Discoverability.
9. /setup endpoint for POST and DELETE are Async operations.
10. MongoDB solution for setting up the movies data and CRUD operations on Movies.