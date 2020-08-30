![Java CI](https://github.com/Brest-Java-Course-2020/apiashko-car-rental/workflows/Java%20CI/badge.svg)
# apiashko-car-rental
car rental app

## Profiles
>  rest
>
>  soap (by default)
>
>  test (h2 database)

## How build
Setup java 8, Maven and Environment variable see [environment_setup.md](environment_setup.md)
  
## Build project 

#### Soap profile: 
 Go to Project folder and execute  
     
     mvn install -DskipTests -pl spring-boot-soap-rest -am 
     cd spring-boot-soap-rest/
     mvn  spring-boot:run -P test,soap
   
 Then open second console
       
     mvn install -DskipTests -pl spring-boot-web-app -am
     cd spring-boot-web-app/ 
     mvn spring-boot:run
 
#### Rest profile in the same way 

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