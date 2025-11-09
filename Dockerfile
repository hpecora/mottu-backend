# Imagem base com Java 17
FROM openjdk:17-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Copia tudo do projeto
COPY . .

# Compila o projeto com Gradle
RUN ./gradlew clean build -x test

# Expõe a porta
EXPOSE 8080

# Comando para rodar o app
CMD ["java", "-jar", "build/libs/mottu-0.0.1-SNAPSHOT.jar"]
