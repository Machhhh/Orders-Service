
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

-   ### From base directory build app
			mvn clean install
			mvn spring-boot:run
-   ### In your browser go to URL address
			http://localhost:8090/
-   ### Option I / clone image from Docker
			docker pull machhh/orders-service
-   ### Option II make fat jar and run it
			mvn clean package spring-boot:repackage
-  ### Option III clone fat jar from repository and run it
			java -jar orders-service-fat.jar
-   ### To play with Gatling performance tests
			mvn gatling:execute
 
## Application flow
Once the application is started, a browser at [http://localhost:8090](http://localhost:8090)
Use Swagger to reach REST endpoints [http://localhost:8090/swagger-ui.htm](http://localhost:8090/swagger-ui.htm) , it allows you to make CRUD operations in app. database. Correct way is to create client before request, otherwise you’ll get global exc. To see database use: [http://localhost:8090/h2-console/](http://localhost:8090/h2-console/) 
On the frontend side you can check **requests** in the DB by clicking appropriate  buttons ( all req, req by client id (modal select menu), after that the  report will be generated and printed. (+ total count, total value, avg value of requests) There is also possibility to „edit” selected request. Report can be saved to CSV, XML file (by default files are stored in your user.home directory.
You can upload generated files as well ( make sure that proper file is selected before you click submit). Together with the application you will find few attachments (csv, xml files)

##  Screens
![enter image description here](https://lh3.googleusercontent.com/a4nM9ZVQpBgrHYH8lQ9ASKP1OsZ_QcdGLw8iVK7680oeN6ddEg13uWHmxF_F-YCfV85cVVcnjvg_=s00)
![enter image description here](https://lh3.googleusercontent.com/-1_vN8aOaWdPu_5M0XJWqwb8ty1ql56eA41SMKGGeZlj7rjJ0uBDxrLu2ZoM9X_s49msbGXMrtZL=s0)
![enter image description here](https://lh3.googleusercontent.com/k07Ha6VvGRDarWuaE_Q-nqNZfd0AteH9DpMB25avTecvTsO-RAusy46rTGlXVs_B7UkRKUODRP4t=s0)
![enter image description here](https://lh3.googleusercontent.com/J-D-WSjxWnb5EDnXkp8ri39BJLsPmxaB1MDtXOHwUVT2mvmHngdtEF3cOeUzPM3JHIXIj8CFRjsH=s0)
![enter image description here](https://lh3.googleusercontent.com/Wl3uhTb36YxQGmgEbfdWrXOpBidhmYQRqO2dSjOEHOzD0ban87VXnwxpc5UB2N2JM-taOVWHwNE5=s0)
![enter image description here](https://lh3.googleusercontent.com/yE1JY4hJFv2wOLgQhII3f-7y1YW8ynT6gH8DbWCdbm9qPnz_LZV7LvVyYBvRmYVeePHH_wxLL5aK=s0)
![enter image description here](https://lh3.googleusercontent.com/3VBtckQyS7HGhRpaMr5uOp-lvWPNci8MJrk-MRnEEGBGrxpwRZxO_6dFENR_uC5Pgnkyud7lIVXY=s0)
![enter image description here](https://lh3.googleusercontent.com/JNd4QLYUGgQ98BtTxUhRqyXbbNwBBjvm0GoVefnZTGHTRxAIr-LytPEizOTcBN5tKuW3QV9y8ACH=s0)
![enter image description here](https://lh3.googleusercontent.com/zyIzOiQnBag5K7qjBwmSSh9NEVgXD5RtEammVQqtGccB_7ZxAP7FSX1OkNzEfQVgsMo6hw6TUCuU=s0)
![enter image description here](https://lh3.googleusercontent.com/aQl6Va2fHtPSR7AASZgf-npL260877MyM7u20q6YDyfk45s0dXkf4pJM5gsn7rWKFBfGY347XkTH=s0
![enter image description here](https://lh3.googleusercontent.com/Y5VfF1DV3MAWPo_EEv4dg8cAVlrjab_GqWL5pnfY7Fq0xmqNTCgJQI-tHVrY7tAqGoXE2vQIeBcQ=s0)
![enter image description here](https://lh3.googleusercontent.com/4Bs7ke5_AcpQ0sfDI3HymUiIZD6KUJOrmXaqRcA1h9cwJORoT-jL4304ilQ4nSgPYLJa84FOY0MM=s0)
![enter image description here](https://lh3.googleusercontent.com/hm7oQbCVrikLqOLBWiorPSVPTIZFU4HMnHO3N0J1du4oPvX07uHQ2Z2Q-NE8ijWw8SOLr7NvpqL1=s0)
![enter image description here](https://lh3.googleusercontent.com/3DNyzEYpttkLelDyWA0dtHaEmJdpc-1cD-GFMt9Ubr0SghjPIJwkQc4hvaYHYc6WtHwam-Ol-31a=s0)
![enter image description here](https://lh3.googleusercontent.com/jIO4rHPskBeKOrYkAIDX3aNa1bDs89CrCTEBltwOkRBeDSxUvVzMskcAWP4u4gifIPSdJ3BGpR47=s0)
![enter image description here](https://lh3.googleusercontent.com/hJj_iYuwLauSwbvSrzjfOgka39xgGIU4_X_CfagagQKP1oYcpE8uhE01atcQRX7eJz6XeRXdMW8v=s0)
![enter image description here](https://lh3.googleusercontent.com/a-xBV-svkbOwcPokP8KGvOLl-zhYEnAGj4WQnbC7943WqSzujQUKD5lLZWf-KlmhazoM6-fATiAI=s0)
