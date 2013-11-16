#! /bin/sh

#SHARPINSTALL IS SETTED ON init.sh
#. "$SHARPINSTALL"./init.sh

CRMVER=crm.jar
#CRM=$SHARPINSTALL/platform/bin/agents/$CRMVER
CRM=../agents/$CRMVER
#CMD="java -server -Xms520m -Xmx800m  -jar $XKT"
CMD="java -jar $CRM"

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


