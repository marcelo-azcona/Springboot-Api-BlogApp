FROM eclipse-temurin:17

LABEL mentainer="azcona_86@hotmail.com"

WORKDIR /app

COPY target/springboot-blog-api-1.0.jar /app/springboot-blog-api-docker.jar

ENTRYPOINT["java", "-jar", "springboot-blog-api-docker.jar"]