FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y libxext6 libxrender1 libxtst6 libxi6 libfreetype6 

ARG  JAR_FILE=target/Lab1PA-1.0-SNAPSHOT-jar-with-dependencies.jar

COPY ${JAR_FILE} app_Culturarte.jar

ENTRYPOINT ["java", "-jar", "app_Culturarte.jar"]
