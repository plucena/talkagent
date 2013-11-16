#/bin/sh


# Stop all agents
clear
echo "STOPPING TALKAGENT SERVER."
echo ""
./killer ./tt_linux2.4.2 -c server
./ams.sh -stop 
./ucl-converter.sh -stop 
#./dispatcher.sh -stop 
./ucl-interpreter.sh -stop 
./xkt-kb.sh -stop
./ozone.sh -stop
./talkagent.sh -stop
./crm.sh -stop
./killer java_vm
./killer ozone
#./crio.sh -stop

echo ""
echo "TalkAgent Stoped"
echo ""

