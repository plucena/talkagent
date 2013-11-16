#! /bin/sh


#SHARPINSTALL IS SETTED ON init.sh
. "$SHARPINSTALL"./init.sh

OZONEKB=$SHARPINSTALL/platform/bin/kb/ozone-1.1/kb

#STOP OZONE
echo "Stopping Ozone.."
./ozone.sh -stop

#ERASE DATA
echo "Erasing data" 
rm -rf $OZONEKB

# RECREATE OODB
echo "Recreating OODB"  
ozoneInst -d$OZONEKB

./ozone.sh -start&

sleep 5

#ADD USERs
ozoneAdmin newuser -name=root -id=101
ozoneAdmin newuser -name=Administrator -id=102

