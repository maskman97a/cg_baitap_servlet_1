FROM gradle:7.5.1-jdk8 AS builder

WORKDIR /

COPY . .

RUN gradle build

FROM tomcat:9.0-jdk8-openjdk
LABEL authors="ceotungbeo"

COPY --from=builder /build/libs/demoJDBC-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]