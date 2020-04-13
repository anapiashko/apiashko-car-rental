### Update packages

    ~$ sudo apt update

### Install OpenJDK 8

    ~$ sudo apt install openjdk-8-jdk   
   
### Verify

    ~$ java -version

### Install Maven

    ~$ sudo apt install maven

### Verify maven installation

    ~$ mvn -version

    Maven home: /opt/apache-maven-3.6.0
    Java version: 1.8.0_191, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-8-openjdk-amd64/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux", version: "4.15.0-45-generic", arch: "amd64", family: "unix"

### Environment variable
App needs environment variable CAR_RENTAL_ENV to start using
 web and REST apps together for development and production.
 
 Use ```dev```, when you start app with maven jetty plugin.
 
 Use ```prod```, when you deploy app on Tomcat.
 
 Create CATALINA_BASE/bin/setenv.sh file and put the following line in it, 
 and then start the Tomcat.
 ```
 export CAR_RENTAL_ENV=prod
 ```
 You can set up variable from IntelliJ. Go to run -> edit configurations -> Runner.
 In field Environment variables put  ```CAR_RENTAL_ENV=dev ```.
