
# Spring-Boot-Orders-Service  ![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

**OrdeRs Service** is Spring Boot + JPA / Hibernate web application. Used to generate and manage client requests stored in the database. 
It contains some useful configuration items:

-   Spring Boot 
-   Spring Data JPA + Hibernate
-   Spring Security 
-   H2 embedded database
-   Swagger 
-   Project Lombok
-   Mapstruct
- Devtools
-   Thymleaf, Bootstrap, Jquery
-   Web Mvc Tests, JUnit
-   Scala / Gatling performance, load tests
- Docker container platform / image
-  Project created with Maven, IntelliJ IDEA (java version 1.8.0_181)

##  **Things to be checked before starting the app**
Spring Security Admin role : Deafult  login: **admin**, password: **pass**
Embedded H2 Database. Deafult  login: **admin**, password: **pass**, **jdbc:h2:mem:CS**) Note that embedded DB is dropped when closing app. If you wish to use a different database / schema, you will need to override values in application.properties. Ensure that you have a server running and correctly configured in application.properties (default is localhost, port: **8090**)

##  How to run application

-   ### From base directory build app
			mvn clean package
			mvn spring-boot:run
-   ### In your browser go to URL address
			http://localhost:8090/
-   ### Option I / clone image from Docker
			docker pull machhh/orders-service
-   ### Option II / make fat jar and run it
			mvn clean package spring-boot:repackage
-  ### Option III / save fat jar from repository and run it
			java -jar orders-service-fat.jar
-   ### To play with Gatling performance tests
			mvn gatling:execute
 
## Application flow
Once the application is started, a browser at [http://localhost:8090](http://localhost:8090)
After login as ADmin, use Swagger to reach REST endpoints [http://localhost:8090/swagger-ui.htm](http://localhost:8090/swagger-ui.htm) , it allows you to make CRUD operations in app. database. To see database use: [http://localhost:8090/h2-console/](http://localhost:8090/h2-console/) 
On the frontend side you can check **requests** in the DB by clicking appropriate  buttons ( all req, req by client id (modal select menu), after that the  report will be generated and printed. (+ total count, total value, avg value of requests) There is also possibility to edit, delete selected request or create new from User role. Report can be saved, uploaded from/ to CSV, XML file (by default files are stored in your user.home directory.

##  Screens
![enter image description here](https://lh3.googleusercontent.com/v8idSv0MBjQSOZfkjXm4nSpjs24MLMp8i9voEyyLLpoLt_C5uQru-rNreQXD9qxi8aEJ0R0PZMFo=s0)
![enter image description here](https://lh3.googleusercontent.com/gDy6F-6ysnr0UEvX0G5FaK_I4MnHrO2zTLyTa9h1YeJbh3_iWaJ9pI9gc8X3pDhk4W8M2fUwUfmS=s0)
![enter image description here](https://lh3.googleusercontent.com/zoeQ4lWXBr1KyBol3KNN5f1MDhKYk8wtcc2W9EJH3tSuNs2Sqy4gRok6bZHxLQq7iKF4cpcRkKez=s0)
![enter image description here](https://lh3.googleusercontent.com/fygaQfRFegKWnsBjLXcro6uUzgLQIXdU3-CK8YzKZxQGqe6QsoEV1scbX1rycb16YzeDU3BEDufA=s0)
![enter image description here](https://lh3.googleusercontent.com/RqkuP-AjFZhSqeFP7e7IttD4u_VMF336N3mkghV6wDQJ75WdAMC_SVIj8Fo1T140LoQc-D4NEwPW=s0)
![enter image description here](https://lh3.googleusercontent.com/Quz8dI4-dMMi8T4ddqENWBtKZMDkr53YjyJ6j_M3tbajTu5pH-549IJTNHi5Tt992_OE28W9KoMX=s0)
![enter image description here](https://lh3.googleusercontent.com/G7kiGUL8JebgWjGlFzHnU_MuMyw2sDgokLVuh41fRpSsEHIvr5wAvJz-x9ZooDXKqYf7xMhvIX-v=s0)
![enter image description here](https://lh3.googleusercontent.com/nU4fcP_n1wMce45cIlF-F9yqQ7lS9Gf9n5ashC8GtJoWQS8TufmoTmtOY8y5nPAGLo_B6QGroybB=s0)
![enter image description here](https://lh3.googleusercontent.com/afyz_zwPzS9viRjsJ7NxlKU5m-SM_ZBaLV8kzEs0RRjX0bgOcd-lpcXz8ZGbvRX1CeZa1EzCCuiJ=s0)
![enter image description here](https://lh3.googleusercontent.com/hm7oQbCVrikLqOLBWiorPSVPTIZFU4HMnHO3N0J1du4oPvX07uHQ2Z2Q-NE8ijWw8SOLr7NvpqL1=s0)
![enter image description here](https://lh3.googleusercontent.com/3DNyzEYpttkLelDyWA0dtHaEmJdpc-1cD-GFMt9Ubr0SghjPIJwkQc4hvaYHYc6WtHwam-Ol-31a=s0)
![enter image description here](https://lh3.googleusercontent.com/jIO4rHPskBeKOrYkAIDX3aNa1bDs89CrCTEBltwOkRBeDSxUvVzMskcAWP4u4gifIPSdJ3BGpR47=s0)
![enter image description here](https://lh3.googleusercontent.com/hJj_iYuwLauSwbvSrzjfOgka39xgGIU4_X_CfagagQKP1oYcpE8uhE01atcQRX7eJz6XeRXdMW8v=s0)
![enter image description here](https://lh3.googleusercontent.com/a-xBV-svkbOwcPokP8KGvOLl-zhYEnAGj4WQnbC7943WqSzujQUKD5lLZWf-KlmhazoM6-fATiAI=s0)
