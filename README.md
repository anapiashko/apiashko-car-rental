![Java CI](https://github.com/Brest-Java-Course-2020/apiashko-car-rental/workflows/Java%20CI/badge.svg)
# apiashko-car-rental
car rental app


## How build
Setup java 8, Maven and Environment variable see [environment_setup.md](environment_setup.md)

## Profiles
### dev
To build the app with h2 database execute
```
mvn clean install -Pdev
```
or just command below, because dev profile is active by default
```
mvn clean install 
```
### prod
To build the app with mysql database execute
```
mvn clean install -Pprod
```
-----------------
To make sure it works correctly, you can go to
>/spring-boot-rest-app/target/classes/application.properties

and see the value of variable `spring.profiles.active`. It should be `dev`
or `prod`, depends on you choice. 

After starting your application, the Spring framework
 activates Spring profile as defined in application.properties
 
_Scripts_ _for_ _creating_ _and_ _initializing_ _db_ _described_ _in_ _car-rental-test-db_ _module_ 