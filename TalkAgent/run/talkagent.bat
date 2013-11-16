@echo off
cd ..\usragt\httpservlets
SET CMD="startup.bat"
echo
echo STARTING TALKAGENT....
echo http://localhost/talkagent
echo
%CMD% > .talkagent.log



