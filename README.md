# movieworld
Movie World project

# Setup
1. Add your AWS credentials as server environment variables (aws_access_key_id, aws_secret_access_key).
2. Build the project "mvn install". It should create all the required tables.
3. All the movie data is stored in AWS S3 as json file. Use the "/setup - POST" endpoint to create movies data.
4. For API documentation use swagger UI -- "http://<host>:<port>/movieworld/swagger-ui.html"

# Components
1. Spring RESTful API on Movies.
2. Uses AWS MySQL RDS as backend Database.
3. Spring Boot.
4. Spring Data Jpa and Hibernate.
5. AWS SDK to connect to AWS S3 to get the movies setup file from S3 bucket.
6. Cucumber with Java step definitions.
7. Swagger for API documentation.