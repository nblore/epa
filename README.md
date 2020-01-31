# Membership System API

## Pre-requisites:
- Git is installed ? (Only if you do not have the application downloaded to start with)
- Docker is installed
- You have Windows Powershell (Windows) or Terminal (Mac)
- You have Maven installed
- Postman is installed
- MySQLWorkbench is installed

If any of the above are not installed then please download and install them before continuing.


## 1. Cloning the repository
If you already have the repository (project containing the application) cloned (downloaded) on your computer then skip to step 2.
Run the following command to clone the repository to your computer:

    git clone https://github.com/nblore/epa.git

**NOTE** We would usually clone using SSH but this requires the user to have an SSH key set-up on their computer as well as on their GitHub account. Cloning through https means we can avoid this steup.

## 2. Setting up the database:
Open up Terminal, or equivalent, and run the following commands:

     docker pull mysql/mysql-server:5.7
     docker run -p 3306:3306 --name epadb -u root -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql/mysql-server:5.7
     docker exec -it epadb mysql -u root --password=my-secret-pw -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'my-secret-pw' WITH GRANT OPTION; FLUSH PRIVILEGES;"

If you receive "access denied", run the command again and it will allow you through.

_You will now be able to connect to the database!_

**NOTE:** The ROOT password will be generated when starting the container. For demonstration purposes of this project,
we are using the ROOT password. In a live environment it is discouraged to use the root user and a weak password for database
connections. This will compromise security.

## 3. Create the database schema using the below

    docker exec -it epadb mysql -u root --password=my-secret-pw -e "CREATE SCHEMA epa"


## Use this command to monitor the output from the database container:

    docker logs epadb

## 4. Start the springbboot server by running

    mvn spring spring-boot:run

This will create the server and allow you to start making HTTP requests. To test the endpoints and open postman.
To view the database please open MySQLWorkbench


## 5. Endpoints available:

_http://localhost:8000/api/employee/login_ >> **POST REQUEST**

_http://localhost:8000/api/employee/register_ >> **POST REQUEST**

_http://localhost:8000/api/card/balance_ >> **POST REQUEST**

_http://localhost:8000/api/card/topup_ >> **POST REQUEST**

_http://localhost:8000/api/employees_ >> **GET REQUEST**

_http://localhost:8000/api/employee/delete_ >> **DELETE REQUEST**


Please refer to the user guide on how to test these endpoints.