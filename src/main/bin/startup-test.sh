#!/bin/bash

java \
-server \
-Xms1024M \
-Xmx2048M \
-Xmn768m \
-XX:PermSize=64m \
-XX:MaxPermSize=64m \
-verbose:gc \
-XX:+PrintGC \
-XX:+PrintGCDetails \
-XX:+PrintGCTimeStamps \
-XX:+PrintGCApplicationStoppedTime \
-DAppServer.home="tianyi-server" \
-Dcom.sun.management.jmxremote.port=5000 \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-DENV=dev \
-Xloggc:gc.log \
-cp "lib/*:conf/" \
com.tianyi.Startup