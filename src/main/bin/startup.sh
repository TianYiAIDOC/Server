cd #!/bin/bash

java \
-server \
-Xms1024M \
-Xmx2048M \
-Xmn768m \
-XX:PermSize=128m \
-XX:MaxPermSize=128m \
-verbose:gc \
-XX:+PrintGC \
-XX:+PrintGCDetails \
-XX:+PrintGCTimeStamps \
-XX:+PrintGCApplicationStoppedTime \
-DAppServer.home="pmchat-service" \
-Dcom.sun.management.jmxremote.port=3334 \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-DENV=release \
-Xloggc:gc.log \
-cp "lib/*:conf/" \
com.tianyi.Startup