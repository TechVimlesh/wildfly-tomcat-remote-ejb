FROM tomcat:8.5-jdk8-openjdk

COPY ./target/webclient.war /usr/local/tomcat/webapps/

EXPOSE 8880

CMD ["catalina.sh", "run"]