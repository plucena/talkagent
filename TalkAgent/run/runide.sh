#! /bin/sh

# linux

TA_IDE=/root/TALKAGENT
JAVA_HOME=/usr/java/j2sdk1.4.1

JAVA_LIB=$JAVA_HOME/jre/lib/ext
TA_LIB=$TA_IDE/ide/bin/lib

cp -f  $TA_LIB/*.jar $JAVA_LIB

TA=$TA_IDE/ide/bin/ide/kbeditor.jar

java -jar $TA



