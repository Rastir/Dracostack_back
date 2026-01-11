# Etapa 1: Compilación
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY src ./src

# Compilar el proyecto
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiar el JAR de la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]