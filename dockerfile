FROM eclipse-temurin:24-jdk-alpine
ENV TZ=America/Sao_Paulo
ENV AURORA_PIX_DATABASE_URL=jdbc:postgresql://localhost:5432/your_database
ENV AURORA_PIX_DATABASE_USERNAME=your_username
ENV AURORA_PIX_DATABASE_PASSWORD=your_password
ENV ASSAS_BASE_URL_API=
ENV ASSAS_ACCESS_TOKEN=
VOLUME /tmp
COPY target/aurora-pix-0.0.1-SNAPSHOT.jar aurora-pix.jar
ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","/aurora-pix.jar"]