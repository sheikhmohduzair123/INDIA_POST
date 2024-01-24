FROM gradle:7.5.1-jdk17
WORKDIR /ClientApplication
COPY build/libs/client-0.1.1.jar /ClientApplication/
COPY dockerfiles/config/ /ClientApplication/config
COPY Report/ /ClientApplication/Report
CMD ["java", "-jar", "client-0.1.1.jar"]
EXPOSE 84
