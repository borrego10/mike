#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Start Up Batch script
# ----------------------------------------------------------------------------

BASE_DIR=$(dirname "$0")
WRAPPER_JAR="$BASE_DIR/.mvn/wrapper/maven-wrapper.jar"

if [ -z "$JAVA_HOME" ]; then
    JAVA_EXE="java"
else
    JAVA_EXE="$JAVA_HOME/bin/java"
fi

exec "$JAVA_EXE" -Dmaven.multiModuleProjectDirectory="$BASE_DIR" -classpath "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
