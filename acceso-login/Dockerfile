FROM openjdk:17-jdk-slim
ADD target/acceso-login-0.0.1-SNAPSHOT.jar acceso-login.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/acceso-login.jar"]
EXPOSE 8193