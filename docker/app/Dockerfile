FROM openjdk:11
COPY server.jar /opt/myapp/
WORKDIR /opt/myapp
EXPOSE 2005
CMD ["java", "-jar", "-Djava.rmi.server.hostname=10.0.75.1", "-Djava.security.debug=acess,failure", "-Djava.security.policy=permission=java.security.AllPermission", "server.jar"]