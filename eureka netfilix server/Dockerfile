FROM openjdk:11
VOLUME /tmp
EXPOSE 8761
ADD target/eurekaNetFilixServer-0.0.1-SNAPSHOT.jar eurekaNetFilixServer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/eurekaNetFilixServer-0.0.1-SNAPSHOT.jar"]