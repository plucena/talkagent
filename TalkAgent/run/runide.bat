@ ECHO OFF 
REM WIN32

SET TA_IDE=C:\TALKAGENT
SET JAVA_HOME=c:\java\j2sdk1.4.0

SET JAVA_LIB=%JAVA_HOME%\jre\lib\ext
SET TA_LIB=%TA_IDE%\ide\bin\lib

copy %TA_LIB%\*.jar %JAVA_LIB%

SET TA=%TA_IDE%\ide\bin\ide\kbeditor.jar

java -jar %TA%


