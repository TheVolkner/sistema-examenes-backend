FROM openjdk:19
COPY target/*.jar sistema-examenes.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/sistema-examenes.jar"]