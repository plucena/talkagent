#! /bin/sh


#SHARPINSTALL IS SETTED ON init.sh
#. "$SHARPINSTALL"./init.sh


#SERVLET_HOME=$SHARPINSTALL/platform/bin/usragt/httpservlets
SERVLET_HOME=../usragt/httpservlets

CMD="start.sh"

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
		echo
                echo Starting TalkAgent
                echo http://localhost/talkagent/agent
                echo   
                cd $SERVLET_HOME
                chmod 777 *
  		$CMD
                echo 
                ;;
        -stop)
		./killer java -jar jzHttpSrv.jar -noui
                ;;
esac



#end of file
