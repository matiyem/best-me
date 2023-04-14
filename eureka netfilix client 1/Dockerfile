FROM openjdk:11
COPY . /app
WORKDIR /app
VOLUME /tmp
EXPOSE 9009
ADD target/docker.jar docker.jar
ENTRYPOINT ["java","-jar","/docker.jar"]