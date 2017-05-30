# movieworld
Movie World project

# Setup
1. Add your AWS credentials as server environment variables (aws_access_key_id, aws_secret_access_key).
2. Build the project "mvn install". It should create all the required tables.
3. All the movie data is stored in AWS S3 as json file. Use the "/setup - POST" endpoint to create movies data.

# Components
1. Spring RESTful API on Movies.
2. Uses AWS MySQL RDS as backend Database.
3. "/setup" endpoint for creating and clearing off the test movie data.
4. MovieController for CRUD operations on Movies.