FROM gradle:7.5.1-jdk17
WORKDIR /ServerAPI
COPY dockerfiles/keystore/bdpost.p12 /ServerAPI/keystore/
#COPY dockerfiles/config/application.properties /ServerAPI/config/
COPY build/libs/ServerAPI-0.1.0.jar /ServerAPI/
CMD ["java","-jar", "ServerAPI-0.1.0.jar"]
EXPOSE 82 
