# Pantry.io
Pantry.io is a web application that allows users to search for recipes based on ingredients in their pantry. Our application gives users the option to favorite recipes, keep track of the number of times they’ve prepared them, and schedule meal plans from their favorited recipes.  

Our goal is to create an application that inspires users to discover new recipes and organize them in a way that best suits their needs.

# Technologies Used
Java - version 1.8  
PostgreSQL - version 42.2.12  
React - version 17.0.2  
Bootstrap - version 4.6.0  
Spring Boot - version 2.5.1  
JSON Web Tokens - version 0.9.1  
Maven - version 3.8.1  
Git - version 2.31.1  
JUnit - version 4.13.2  
Mockito - version 3.9.0  
Swagger - version 3.0.0  
Postman - version 8.3.0  
AWS EC2
AWS S3
AWS CodeBuild
AWS CodePipeline
AWS RDS
AWS Elastic Beanstalk


# Features
- Login and Registration
- Logout and delete user
- View most components in a user Dashboard
- Takes in User ingredient input for searching for recipes
- Query External API for Recipes
- Favorite queried recipes and saves them to the user's acconut
- View recipes favorited by the user
- Keep count of number of times a favorited recipe has been prepared
- Create and schedule meal plans to hold recipes to be prepared for Breakfast, Lunch, Dinner, etc.

To-do list:
- Use Axios to send fetch requests 
- Use React Redux to simplify the state of the application
- Use the Router to keep track of screen/component routing
- Debug the Meal Plan component from front to backend to allow meal plan persistence

# Getting Started
Clone remote repository of the API onto your local repository using the following command:
```
git clone https://github.com/210426-java-react-enterprise/team-charlie-p2-api.git
```
If you want to have access to the user interface, make sure to clone:
```
https://github.com/210426-java-react-enterprise/team-charlie-p2-ui.git
```
It's important to note that our UI sends fetch requests to an API deployed to AWS Elastic Beanstalk, however it may no longer be active.
![image](https://user-images.githubusercontent.com/83236234/122941553-7c808880-d343-11eb-8c74-86331a9a5bac.png)

Setting up the application.yml file:
```
api:
  app_id: [app-id-for-recipe-queries]
  app_key: [external-api-key-for-recipe-queries]
​
server:
  port: 5000
​
spring:
  application:
    name: spring-boot-poc
  h2:
    console:
      enabled: true
      path: /h2
    url: jdbc:h2:mem:test
    driver-class-name: org.h2.Driver
    username: admin
    password: revature
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    show-sql: true
    hibernate:
      ddl-auto: none
​
management:
  endpoints:
    web:
      exposure:
        include: "*"
​
jwt:
  header: Authorization
  prefix: Bearer
  secret: [secret-key]
```

Make sure to generate and use any secret-key of your choice! Furthermore, we used Edamam API 1.0 specifically in order to query for our recipes. Make sure to generate proper app_id and app_key values to put into the YAML file.

# Usage
Once you've cloned the API feel free to run the application locally. Our application has an internal Tomcat server that can be executed.

Next, if the Elastic Beanstalk server has been stopped, you may need to adjust the UI fetch requests to the specific link and port number of your local server.

From there you should be able to start the UI locally and begin pinging the API:
```
npm start
```


# Contributors
Team Lead: Kevin Chang  
Git-flow Manager: Richard Taylor  
DevOps Manager: Uros Vorkapic   
UI Lead: Austin Knauer  
Database Manager: Ozzy Castillo   

