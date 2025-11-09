# JDK 17 leve
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia arquivos do Gradle e fontes
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# Permissões e build do jar (sem rodar testes)
RUN chmod +x ./gradlew && ./gradlew bootJar -x test

# Renomeia o artefato final para app.jar (independe do nome do projeto/versão)
RUN sh -c 'JAR=$(ls build/libs/*.jar | head -n1) && cp "$JAR" /app/app.jar'

# Porta padrão local (Render injeta PORT em runtime)
EXPOSE 8080

# Usa a PORT do Render se existir; senão 8080
CMD ["sh","-c","java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
