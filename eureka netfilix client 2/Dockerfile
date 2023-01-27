FROM openjdk:11
VOLUME /tmp
EXPOSE 8989
ADD target/docker.jar docker.jar
ENTRYPOINT ["java","-jar","/docker.jar"]