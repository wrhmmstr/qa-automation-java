FROM eclipse-temurin:11-jre
WORKDIR /usr/share/app
ENTRYPOINT ["java", "-jar", "app-dev.jar", "--spring.profiles.active=prod,api-docs", "--spring.liquibase.contexts=prod,faker"]
COPY app.codegen/build/app-dev.jar ./
