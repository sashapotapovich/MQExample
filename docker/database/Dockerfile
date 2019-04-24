FROM oracle/database:12.2.0.1-se2
MAINTAINER sashapotapovich@gmail.com
ENV ORACLE_ALLOW_REMOTE=true
ENV ORACLE_SID=SID
ENV ORACLE_PWD=Pwd
#ENV ORACLE_ALLOW_REMOTE=true
COPY setup /setup
RUN cp /setup/*.sh /docker-entrypoint-initdb.d/startup
EXPOSE 1521
#CMD cp /setup/tnsnames.ora $ORACLE_HOME/network/admin/tnsnames.ora
