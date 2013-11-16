#! /bin/sh

#SHARPINSTALL IS SETTED ON init.sh
. "$SHARPINSTALL"./init.sh

XKTVER=xkt-kb.jar
#XKT=$SHARPINSTALL/platform/bin/agents/$XKTVER
XKT=../agents/$XKTVER
#CMD="java -server -Xms520m -Xmx800m  -jar $XKT"
CMD="java -server -jar $XKT"

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
		#(./init.sh) 2>&1
		echo ""
                $CMD
                ;;
         -stop)
                ./killer $CMD
                ;;

esac


