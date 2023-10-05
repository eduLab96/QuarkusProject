Summary:

This is a Quarkus Project made using Maven. It runs on Localhost:8080. It’s a simple web application that manages the databases with Hibernate, the backend Api with Jackson, and the front with Vaadin. CDI annotations are used to manage dependencies automatically. 
To start the program in development mode, use “.\mvnw quarkus:dev” in the terminal when in the root of the project.

Database:

For this project I’ve used H2 because it runs in memory. Since this is a simple CRUD project, it was easier to set up and test this way, as well as being easier to showcase to the user. The database is managed with Hibernate and I’ve written some simple functions to select, update or delete entities. In this case, the entity is Product, which just consists of an auto generated id, name and description.

API: 

The API manages the methods GET, POST, PUT and DELETE. These methods control de http requests sent to the exposed port (in this case 8080) and call the necessary methods to persist the changes to the database. The communication between the client and the server is done through JSON.

Frontend:

The frontend is built using only Java through Vaadin. It consists of just a single view where the user can see the products in the database, as well as add a new product, and even update or delete an existing product. 

Tests

There are 2 sets of tests. The first set makes sure all the api calls and the hibernate repository work properly. These tests are done with Junit and RestAssured. The second set checks the functionality of the frontend through Selenium.

All tests can be run with .\mvnw quarkus:test.
