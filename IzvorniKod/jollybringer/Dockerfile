# Koristi Java 21 baziranu sliku kao osnovu
FROM amazoncorretto:21 AS build


# Postavi radni direktorij
WORKDIR /app

# Kopiraj POM i instaliraj dependencije (koristi cache za ubrzanje)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Kopiraj cijeli projekt u radni direktorij
COPY . .

# Izgradi aplikaciju
RUN mvn clean package -DskipTests

# Koristi manji *JDK* za pokretanje aplikacije
FROM amazoncorretto:21

# Postavi radni direktorij za runtime
WORKDIR /app

# Kopiraj izgradjeni JAR iz faze "build"
COPY --from=build /app/target/JollyBringer-0.0.1-SNAPSHOT.jar app.jar

# Izloži portove za server i frontend
EXPOSE 8080 5173

# Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "/app/app.jar"]