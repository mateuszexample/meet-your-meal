#FROM eclipse-temurin:17
#COPY build/libs/*.jar asdf.jar
#ENTRYPOINT ["java", "-jar", "/asdf.jar"]

# Zainstaluj Javę na obrazie bazowym alpine
# Możesz wykorzystać inny obraz
FROM eclipse-temurin:17-jdk-alpine
# Utwórz katalog roboczy
WORKDIR /app
# Skopiuj plik JAR z aplikacją do katalogu roboczego
COPY build/libs/myapp.jar .
# Uruchom aplikację
CMD ["java", "-jar", "myapp.jar"]
