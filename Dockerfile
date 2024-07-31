FROM tomcat:9.0
LABEL authors="ceotungbeo"

COPY build/libs/demoJDBC-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]