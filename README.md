![Java CI](https://github.com/Brest-Java-Course-2020/apiashko-car-rental/workflows/Java%20CI/badge.svg)
# apiashko-car-rental
car rental app

## Run app in docker container
Go to Project folder and execute  
      
     docker-compose up

Then you will be able to use web and rest endpoints and mysql-database in container

#### For development, you can run only database in container
To start mysql-database in container

     docker-compose -f docker-compose-db.yml up

Then you have working mysql database on 3307 port (local) and 3306 port (in container; host: mysql-app)

## Stop containers
     docker-compose down -v

## How build
Setup java 8, Maven and Environment variable see [environment_setup.md](environment_setup.md)
  
 ## Build project 
 Go to Project folder and execute  
      
     mvn clean install
   

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