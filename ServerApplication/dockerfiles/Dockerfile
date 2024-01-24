FROM gradle:7.5.1-jdk17
WORKDIR /ServerApplication
COPY build/libs/server-0.1.0.jar /ServerApplication/
COPY dockerfiles/config/ /ServerApplication/
COPY dockerfiles/keystore/ /ServerApplication/
COPY Report/ /ServerApplication/
CMD ["java","-jar", "server-0.1.0.jar"]
EXPOSE  81
