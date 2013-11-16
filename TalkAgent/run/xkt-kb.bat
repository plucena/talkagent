@echo off
SET SHARPINSTALL=D:\ta-bu-cvs\builts\0.32n

SET CLP=%SHARPINSTALL%\platform\bin\lib\ta-lib-common.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\ucl.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\xml.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\xerces.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\javabeans.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\ftpserver.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\gnu-ftp-client.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\fipa.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\xkt-kb.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\lib\crm.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\kb\ozone-1.1\lib\ozone-1.1.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\kb\ozone-1.1\lib\jakarta-regexp.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\kb\ozone-1.1\lib\log4j-1.2.jar
SET CLP=%CLP%;%SHARPINSTALL%\platform\bin\agents\xkt-kb.jar

SET CMD=java -classpath %CLP% br.usp.talkagent.xkt.XTest

%CMD%

