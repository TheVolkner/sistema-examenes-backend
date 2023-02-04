FROM openjdk:19
COPY out/artifacts/sistema-examenes/sistema-examenes.jar sistema-examenes.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/sistema-examenes.jar"]
