FROM eclipse-temurin:24-jdk-alpine
ENV TZ=America/Sao_Paulo
ENV AURORA_PIX_PRIVATE_KEY_STARK_BANK=
ENV AURORA_PIX_PROJECT_FINCON_ID_STARK_BANK=
ENV AURORA_PIX_PUBLIC_KEY_STARK_BANK=
ENV STARK_BANK_BASE_URL_SANDBOX=
VOLUME /tmp
COPY target/aurora-pix-0.0.1-SNAPSHOT.jar aurora-pix.jar
ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","/aurora-pix.jar"]