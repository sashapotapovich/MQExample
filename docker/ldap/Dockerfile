FROM kwart/ldap-server:2017-09-04

COPY setup /setup

RUN cp /setup/custom.ldif /mnt/custom.ldif

EXPOSE 389 636

CMD ["java", "-jar", "/ldap-server.jar", "-p", "389", "/mnt/custom.ldif"]
