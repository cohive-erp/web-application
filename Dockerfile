# Usar uma imagem base do OpenJDK para rodar Java
FROM openjdk:17-jdk-alpine

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado para o container
COPY target/cohive-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080, que é a padrão do Spring Boot
EXPOSE 8080

# Comando para executar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
