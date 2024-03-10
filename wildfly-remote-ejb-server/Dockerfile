# Use the WildFly 9 image from Docker Hub
FROM jboss/wildfly:9.0.2.Final

# Set environment variables
ENV WILDFLY_HOME /opt/jboss/wildfly
ENV WILDFLY_CLI $WILDFLY_HOME/bin/jboss-cli.sh

# Copy your application JAR into the deployments directory
COPY ./target/wildfly-remote-ejb-server-1.0-SNAPSHOT.jar /opt/jboss/wildfly/standalone/deployments/

# Create application user (consider environment variables for passwords)
RUN $WILDFLY_HOME/bin/add-user.sh -a ejb test --silent

# Create management user with strong password (replace 'adminPassword' with a strong password)
RUN $WILDFLY_HOME/bin/add-user.sh admin admin --silent

# Expose the management port
EXPOSE 9990
EXPOSE 8080

# Run the WildFly server
CMD /opt/jboss/wildfly/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 -bmanagement 0.0.0.0
