#! /bin/sh

#SHARPINSTALL IS SETTED ON init.sh
#. "$SHARPINSTALL"./init.sh

UCLVER=ucl-converter.jar
#UCLAGT=$SHARPINSTALL/platform/bin/agents/$UCLVER
UCLAGT=../agents/$UCLVER
CMD="java -jar $UCLAGT"

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
		(./init.sh) 2>&1 &
		echo ""
                $CMD
                ;;
         -stop)
                ./killer $CMD
                ;;
esac



