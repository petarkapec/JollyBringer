# Startna faza
FROM openjdk:21-jdk-slim

# Instaliraj Maven
RUN apt-get update && apt-get install -y maven

# Postavi radni direktorijum
WORKDIR /app

# Kopiraj sve projektne fajlove
COPY . .

# Izvrši Maven build
RUN mvn package -DskipTests

# Kopiraj JAR fajl
COPY target/*.jar /app.jar

EXPOSE 8080

# Definiši komandnu liniju za pokretanje aplikacije
ENTRYPOINT ["java", "-jar", "/app.jar"]
