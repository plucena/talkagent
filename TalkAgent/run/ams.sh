#! /bin/bash

#SHARPINSTALL IS SETTED ON init.sh
#. "$SHARPINSTALL"./init.sh

AMS=ams.jar
#AMSAGT=$SHARPINSTALL/platform/bin/agents/$AMS
AMSAGT=../agents/$AMS
echo $CMD
CMD="java -jar $AMSAGT"

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
                #(./init.sh) 2>&1 &
  		echo $CMD
		$CMD > .ams.log
                ;;
        -stop)
		./killer $CMD
                ;;
esac



#end of file
