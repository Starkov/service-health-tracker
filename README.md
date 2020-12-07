# About
This is a demo project with such technologies as:
* Spring Boot
* Spring Batch
* Spring Data JPA
* MySQL
* ReactJS

This project contains "backend" and "frontend" modules. I suppose to deploy back-end and front-end parts separately. 

## Hot to run

### Pre requirements
* Maven 
* Yarn  
* MySql  

### Mysql Configuration 
Here is you can specify credentials and url for your MySql instance 
"service-health-tracker/backend/src/main/resources/application.properties"

Assume you are inside the "service-health-tracker" folder.
* go to the "service-health-tracker/backend"

```
cd backend
mvn install
```


* Than got to the  "service-health-tracker/frontend"

```
cd ../frontend
yarn start
```
* Then you should be able to open http://localhost:3000/


### REST API (Swagger)

http://localhost:8080/swagger-ui/index.html#/  