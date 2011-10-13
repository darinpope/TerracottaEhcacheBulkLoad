#!/bin/bash
export TC_HOME=$HOME/Tools/Terracotta/terracotta-ee-3.6.0-SNAPSHOT

java -showversion -server \
-Xms512m -Xmx512m \
-Xloggc:gc.log \
-verbose:gc \
-XX:+PrintGCDateStamps \
-XX:+PrintGCTimeStamps \
-XX:+PrintGCDetails \
-XX:+UseCompressedOops \
-XX:PermSize=128m \
-XX:MaxPermSize=128m \
-Dorg.terracotta.license.path=$TC_HOME/terracotta-license.key \
-DterracottaConfigUrl="localhost:9510" \
-DbatchLoad=false \
-DloaderFile=0-testdata.txt \
-classpath build/classes/main:$TC_HOME/common/*:$TC_HOME/ehcache/lib/*:lib/*:$HOME/.gradle/cache/net.sf.opencsv/opencsv/jars/*:$HOME/.gradle/cache/commons-lang/commons-lang/jars/* \
com.darinpope.BulkLoadMain