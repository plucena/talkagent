#/bin/sh

# Start TALK AGENT SERVER

clear
echo "STARTING TALK AGENT SERVER..."
echo ""
./init.sh -link &

./ams.sh -start > .ams.log &
sleep 4

echo ""
echo ""
echo "* Starting Ozone OODBMS: 3333"
./ozone.sh -start > .ozone.log &

echo ""
echo "* Starting Thought Treasure Server"
./tt.sh > .tt.log &
sleep 10

echo ""
echo ""
./talkagent.sh -start&
./ucl-interpreter.sh -start&
./ucl-converter.sh -start&
echo ""

sleep 5

echo ""
echo ""
./xkt-kb.sh -start&
echo ""
echo ""

./crm.sh -start&


