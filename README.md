# Contactless Top Up API

## Pre-requisites:
- Docker is installed

## Setting up the database:
Run the following commands:

     docker pull mysql/mysql-server:5.7
     docker run -p 3306:3306 --name epadb -u root -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql/mysql-server:5.7
     docker exec -it epadb mysql -u root --password=my-secret-pw -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'my-secret-pw' WITH GRANT OPTION; FLUSH PRIVILEGES;"

When prompted for a password enter the MYSQL_ROOT_PASSWORD. This was declared above as "**my-secret-pw**"

_You will now be able to connect to the database!_

**NOTE:** The ROOT password will be generated when starting the container. For demonstration purposes of this project,
we are using the ROOT password. In a live environment it is discouraged to use the root user and a weak password for database
connections. This will compromise security.

## Create the database schema using the below

    docker exec -it epadb mysql -u root --password=my-secret-pw -e "CREATE SCHEMA epa"


## Use this command to monitor the output from the database container:

    docker logs epadb