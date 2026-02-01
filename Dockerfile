# build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# caching dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# collect to jar
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime
FROM  eclipse-temurin:17-jre-alpine
WORKDIR /app

# create user for security
RUN addgroup -S spring && adduser -S spring -G spring

# copy compiled JAR.
# As in pom.xml: <artifactId>demo</artifactId> and <version>0.0.1-SNAPSHOT</version>
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

USER spring:spring

# port as from application.yaml
EXPOSE 8081

# launch
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]

