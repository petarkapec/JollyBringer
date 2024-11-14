FROM alpine/java:21-jre

## Ovdje je moguce instalirati alate potrebne za rad aplikacije. Vjerojatno vam nece trebati, no dobro je znati.
## Linux distro koji se koristi je Alpine, stoga se kao package manager koristi apk
# RUN apk install <nesto>

# Kopiranje izvrsnog JAR-a iz build containera u izvrsni container
COPY target/*.jar /app.jar

# Izlaganje porta
EXPOSE 8080

# Naredba kojom se pokrece aplikacija
ENTRYPOINT ["java","-jar","/app.jar"]