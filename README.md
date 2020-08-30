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
 
 #### Wsdl will be opened on: http://localhost:8088/ws/car-rental.wsdl  
   
 Then open second console
       
     mvn install -DskipTests -pl spring-boot-web-app -am
     cd spring-boot-web-app/ 
     mvn spring-boot:run
 
