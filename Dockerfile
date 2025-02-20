# Imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Configurar directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado al contenedor
COPY target/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]