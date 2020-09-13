![Java CI](https://github.com/Brest-Java-Course-2020/apiashko-car-rental/workflows/Java%20CI/badge.svg)
# apiashko-car-rental
car rental app

## Visit our car rental website
http://springbootwebapp-env.eba-knmyamem.eu-central-1.elasticbeanstalk.com/

#### Rest service is located at 
http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/

   *consider, that there are no endpoints at root path (' / ')
## Rest server

## Available REST endpoints    

### car_dtos
find all cars in time period with summary number of orders
```
curl --request GET 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/car_dtos?dateFrom=2020-01-01&dateTo=2020-12-12'
```

Pretty print json:

```
curl --request GET 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/car_dtos?dateFrom=2020-01-01&dateTo=2020-12-12' | json_pp
```

### cars

#### findAllByDate
find all free cars on date
```
curl --request GET 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/cars/filter/2020-01-01' | json_pp
```

#### findById

```
curl --request GET 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/cars/1' | json_pp
```

#### create

```
curl --request POST 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/cars' \
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
curl --request PUT 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/cars/4' \
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
curl --request DELETE 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/cars/4'
```

### orders

#### create

```
curl --request POST 'http://springbootrestapp-env.eba-fjyhspi2.eu-central-1.elasticbeanstalk.com/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2020-01-02",
    "carId": "3"
}'
```