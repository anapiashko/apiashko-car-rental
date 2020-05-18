![Java CI](https://github.com/Brest-Java-Course-2020/apiashko-car-rental/workflows/Java%20CI/badge.svg)
# apiashko-car-rental
car rental app


## How build
Setup java 8, Maven and Environment variable see [environment_setup.md](environment_setup.md)
   
## Profiles
### jdbc
```
mvn clean install 
```
### jpa
```
mvn clean install -Pjpa
```