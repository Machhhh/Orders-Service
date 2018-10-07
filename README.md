
# Spring-Boot-Orders-Service  ![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

**OrdeRs Service** is Spring Boot MVC + JPA/Hibernate Web application. Used to generate and manage client requests stored in the database. You can create new request using Swagger, REST endpoints or upload them from CSV, XML file by appropriate buttons. Possibility to generate, print and save in the file, following reports : All Requests or Requests By Client Id ( total count , total value, average value of requests). 

It contains some useful configuration items:

-   Spring Boot MVC
-   Spring Data Repositories
-   Spring data JPA + Hibernate
-   H2 embedded database
-   Swagger 2
-   Project Lombok
-   Mapstruct
-   Thymleaf, Bootstrap, Jquery
-   OpenCSV, Javax
-   Web Mvc Tests, JUnit
-   Scala / Gatling performance, load tests
- Docker container platform / image
-  Project created with IntelliJ IDEA, 1.8 (java version 1.8.0_181), Maven

##  **Things to be checked before starting the app**
I used embedded H2 Database. Update the application.properties file with your  username and password as you like. ( deafult  login: **admin**, password: **pass**, **jdbc:h2:mem:CS**) Note that embedded DB is dropped when closing app. If you wish to used a different database / schema, you will need to override values in application.properties. Ensure that you have a server running and correctly configured in application.properties (default is localhost, port: **8090**)

##  How to run application

`## From base directory build app `
- mvn clean install
- mvn spring-boot:run
`## In your browser go to URL address `
- http://localhost:8090/
`## Option I / clone image from Docker`
-docker pull machhh/orders-service
`## Option II make fat jar and run it  `
-mvn clean package spring-boot:repackage
`## To play with Gatling performance tests  `
-mvn gatling:execute
 
## Application flow
Once the application is started, a browser at [http://localhost:8090](http://localhost:8090)
Use Swagger to reach REST endpoints [http://localhost:8090/swagger-ui.htm](http://localhost:8090/swagger-ui.htm) , it allows you to make CRUD operations in app. database. Correct way is to create client before request, otherwise you’ll get global exc. To see database use: [http://localhost:8090/h2-console/](http://localhost:8090/h2-console/) 
On the frontend side you can check **requests** in the DB by clicking appropriate  buttons ( all req, req by client id (modal select menu), after that the  report will be generated and printed. (+ total count, total value, avg value of requests) There is also possibility to „edit” selected request. Report can be saved to CSV, XML file (by default files are stored in your user.home directory.
You can upload generated files as well ( make sure that proper file is selected before you click submit). Together with the application you will find few attachments (csv, xml files)

##  Screens
![enter image description here](https://lh3.googleusercontent.com/a4nM9ZVQpBgrHYH8lQ9ASKP1OsZ_QcdGLw8iVK7680oeN6ddEg13uWHmxF_F-YCfV85cVVcnjvg_=s00)
![enter image description here](https://lh3.googleusercontent.com/-1_vN8aOaWdPu_5M0XJWqwb8ty1ql56eA41SMKGGeZlj7rjJ0uBDxrLu2ZoM9X_s49msbGXMrtZL=s0)

