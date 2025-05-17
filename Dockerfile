FROM eclipse-temurin:17-jdk
LABEL authors="dorani"
LABEL description="naver news rank crowling"
LABEL version="0.0.1"
COPY build/libs/blog-gen-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]