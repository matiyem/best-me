FROM openjdk:11
VOLUME /tmp
EXPOSE 9009
ADD target/docker.jar docker.jar
ENTRYPOINT ["java","-jar","/docker.jar"]