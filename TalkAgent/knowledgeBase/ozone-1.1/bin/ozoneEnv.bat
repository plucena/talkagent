@echo off
rem $Id: ozoneEnv.bat,v 1.1 2002/11/27 16:30:29 cvs Exp $

if defined OZONE_HOME goto setPaths
set OZONE_HOME=%~dp0%..
echo Warning: OZONE_HOME is not set!
echo guessing it to be %OZONE_HOME%

:setPaths
SET OZONE_CLASSPATH=
SET LOCALCLASSPATH=%OZONE_HOME%;%OZONE_HOME%\classes;%OZONE_HOME%\samples

for %%i in (%OZONE_HOME%\lib\*.jar) do call %OZONE_HOME%\bin\lcp.bat %%i

SET OZONE_CLASSPATH=%LOCALCLASSPATH%
SET LOCALCLASSPATH=
