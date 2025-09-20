# Blog Backend API – Spring Boot + JPA + Docker + AWS (+ AI Summarization) 

## Objective
A backend service using Spring Boot and JPA to let users create, retrieve, update, and manage blogs. Includes a simple AI-powered text summarization feature and is deployable on AWS.

## Features
- Full Blog Management (CRUD)  
- Pagination for blog listing  
- AI Integration – Auto-generated summary for any blog (OpenAI, Gemini, or spaCy via REST)  
- Database: PostgreSQL/MySQL (choose in config)  
- Ready for AWS: Docker deployable, S3 support optional  
- JWT authentication, Redis caching

## API Endpoints

| Feature              | Method & Path                       | Request Body / Query                                                                                     | Description                                |
|----------------------|-------------------------------------|---------------------------------------------------------------------------------------------------------|--------------------------------------------|
| **Add blog**         | `POST /api/blogs`                   | `{ "title": "...", "content": "...", "author": "..." }`                                                 | Create a new blog                          |
| **Get all blogs**    | `GET /api/blogs?page=0&size=10`     | Query params: `page`, `size`                                                                            | List blogs (paginated)                     |
| **Get blog by ID**   | `GET /api/blogs/{id}`               | –                                                                                                       | Retrieve a specific blog by its ID         |
| **Update blog**      | `PUT /api/blogs/{id}`               | `{ "title": "...", "content": "...", "author": "...", "createdAt": "YYYY-MM-DD" }`                      | Update an existing blog                    |
| **Delete blog**      | `DELETE /api/blogs/{id}`            | –                                                                                                       | Delete a blog by its ID                    |
| **AI Summarization** | `POST /api/ai/summarize/{blogId}`   | –<br>**Response:**<br>`{ "summary": "..." }`                                                            | Get AI-generated summary for a blog        |
| **User Signup**      | `POST /api/auth/signup`             | `{ "username": "...", "email": "...", "password": "..." }`                                              | Register a new user                        |
| **User Login**       | `POST /api/auth/login`              | `{ "username": "...", "password": "..." }`                                                              | Authenticate user and get JWT token        |


## Tech Stack
- Java 17+ and Spring Boot 3+  
- Spring Data JPA  
- PostgreSQL/MySQL (configurable)  
- OpenAI API OR Gemini API OR spaCy (microservice) for AI  
- JUnit & MockMvc (testing)  
- Docker (deploy/optional)  
- AWS Ready  

## Quick Start

### Clone Repo
```
git clone https://github.com/manasvi-tech/blog-service.git
cd blog-service
```


### Configure Database
Edit `src/main/resources/application.properties`:

```
Database (PostgreSQL by default, override for MySQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
spring.datasource.username=postgres
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080

Redis Cache Config
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379


Gemini API
gemini.api.key=<your_gemini_api_key>
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent

AWS S3 integration
cloud.aws.credentials.access-key=<your_access_key>
cloud.aws.credentials.secret-key=<your_secret_key>
cloud.aws.region.static=ap-south-1
cloud.aws.s3.bucket=your-bucket-name

```

For MySQL, update the datasource driver and URL accordingly.

### Configure AI Integration
- For **OpenAI/Gemini**: Set API keys in environment variables or `application.properties`.  
- For **spaCy**: Start the Python microservice (edit endpoint in Spring config).  

### Install Dependencies, Build, and Run

```
mvn clean install
mvn spring-boot:run
```

### Try the API!
**Add a blog:**  

```
curl -X POST http://localhost:8080/api/blogs
-H "Content-Type: application/json"
-d '{"title": "Spring Boot Post", "content": "How Spring Boot simplifies REST development...", "author": "Your Name"}'

```

**Get a summary for a blog**
```
curl -X POST http://localhost:8080/api/ai/summarize/1
```

**Register a new user**
```
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username": "testuser", "password": "mypassword"}'
```

**Login and get JWT**

```
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "testuser", "password": "mypassword"}'
```


**Dockerization & AWS Deployment**
```
Build Docker Image
docker build -t blog-backend:latest .
```

**Run Docker Container**
```
docker run -p 8080
```


## Contributing

Contributions are welcome! If you’d like to help improve this project:

1. **Fork** the repository.
2. **Clone** your fork and create a new branch:

```
git clone https://github.com/your-username/blog-service.git
cd blog-service
git checkout -b feature/your-feature-name

```

3. **Make your changes**, commit, and push:

```
git add .
git commit -m "Describe your changes"
git push origin feature/your-feature-name
```

4. **Open a Pull Request** on GitHub. Please include a clear description of what you changed and why.

**Tips:**
- Follow the existing code style.
- Make sure all tests pass before PR.
- For major changes, open an issue first to discuss.

Thank you for your contributions!


