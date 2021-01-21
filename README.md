![Java CI](https://github.com/Brest-Java-Course-2020/apiashko-car-rental/workflows/Java%20CI/badge.svg)
# apiashko-car-rental
car rental app

#Spring-boot-xml
- The method Upload archive from xml will delete the whole database and will fill it with new data.

- Make sure before uploading you do not have important information in database and don't have orders with these cars.

## How build
Setup java 8, Maven and Environment variable see [environment_setup.md](environment_setup.md)
  
 ## Build project 
 Go to Project folder and execute  
      
     mvn clean install
   
## Make a report 
Go to Project folder and execute  
```
mvn site
```
Open to see the result:
> /target/site/index.html

Go to see javadocs for the project
> /target/site/apidocs/index.html

### Start Web using Maven Jetty plugin 

Set up variable CAR_RENTAL_ENV=dev using IntelliJ (see [environment_setup.md](environment_setup.md))

To start Web using Maven Jetty plugin use:
```
cd car-rental-web-app
CAR_RENTAL_ENV=dev mvn jetty:run
```
After starting, the app will be available at
```
http://localhost:8080
```

## Rest server

### Start Rest using Maven Jetty plugin 
    
To start Rest using Maven Jetty plugin use:

```
cd car-rental-rest-app
mvn jetty:run
```

## Available REST endpoints    

### car_dtos
find all cars in time period with summary number of orders
```
curl --request GET 'http://localhost:8088/car_dtos?dateFrom=2020-01-01&dateTo=2020-03-04'
```

Pretty print json:

```
curl --request GET 'http://localhost:8088/car_dtos?dateFrom=2020-01-01&dateTo=2020-03-04' | json_pp
```

### cars

#### findAllByDate
find all free cars on date
```
curl --request GET 'http://localhost:8088/cars/filter/2020-01-01' | json_pp
```

#### findById

```
curl --request GET 'http://localhost:8088/cars/1' | json_pp
```

#### create

```
curl --request POST 'http://localhost:8088/cars' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
	"brand": "Honda",
        "registerNumber": "4444 AB-1",
        "price": "135"
}'
```

#### update

```
curl --request PUT 'http://localhost:8088/cars/4' \
--header 'Content-Type: application/json' \
--data-raw '{
         "id": "4",
 	 "brand": "Honda",
         "registerNumber": "4444 AB-1",
         "price": "140"
}'
```

#### delete

```
curl --request DELETE 'http://localhost:8088/cars/4'
```

### orders

#### create

```
curl --request POST 'http://localhost:8088/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2020-01-02",
    "carId": "3"
}'
```

## Deploying on Tomcat

Set up variable CAR_RENTAL_ENV=prod using tomcat (see [environment_setup.md](environment_setup.md))

Briefly:

Create CATALINA_BASE/bin/setenv.sh file and put the following line in it, 
and then start the Tomcat.
```
export CAR_RENTAL_ENV=prod
```

Go to project

First, you should build the project. Go to project folder and execute  
```
mvn clean install
```
Then, you can find  war-files in: 
>  /car-rental-web-app/target/car-rental-web.war
>
>  /car-rental-rest-app/target/car-rental-rest.war

Copy them to your tomcat server in webapps folder.
It's recommended to restart tomcat for normal work.
If everything is correct you can see the result at:
```
http://localhost:8080/car-rental-web/
```
For REST service for example:
```
http://localhost:8080/car-rental-rest/cars/1
```