FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# Copiar arquivos do Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permissão de execução ao mvnw
RUN chmod +x mvnw

# Baixar dependências
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Compilar aplicação
RUN ./mvnw clean package -DskipTests

# Expor porta
EXPOSE 8888

# Comando para executar com suporte a JAVA_OPTS
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/target/travessia-0.0.1-SNAPSHOT.jar"]