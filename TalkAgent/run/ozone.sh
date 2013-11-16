#! /bin/sh


#SHARPINSTALL IS SETTED ON init.sh
#. "$SHARPINSTALL"./init.sh


#OZONE=$SHARPINSTALL/platform/bin/kb/ozone-1.1/bin/ozone
OZONE=../kb/ozone-1.1/bin/ozone

#OZONEKB=$SHARPINSTALL/platform/bin/kb/ozone-1.1/kb
OZONEKB=../kb/ozone-1.1/kb


CMD="$OZONE -c -d$OZONEKB"

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
  		$CMD&
                ;;
        -stop)
		./killer $CMD
                ;;
esac



#end of file
