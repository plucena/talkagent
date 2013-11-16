#! /bin/bash

#SHARPINSTALL IS SETTED ON init.sh
#. "$SHARPINSTALL"./init.sh

KMVERSION=ucl-interpreter.jar
#KM=$SHARPINSTALL/platform/bin/agents/$KMVERSION
KM=../agents/$KMVERSION

CMD="java -jar $KM"

usage="$0  -start -stop"
start="-start"
stop="-stop"
                
if [ -z "$1" ]
then
  echo "usage:" $usage
  exit 0
fi

case "${1}" in
        -start)
		#(./init.sh) 2>&1&
                echo ""
                $CMD
                ;;
         -stop)
                ./killer $CMD
                ;;
esac



