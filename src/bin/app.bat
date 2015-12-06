#!/bin/sh
echo "Starting DropWizard app"
$JAVA_HOME/bin/java $JAVA_OPTS -Ddw.server.connector.port=$PORT -jar lib/*.jar server lib/*.yml
