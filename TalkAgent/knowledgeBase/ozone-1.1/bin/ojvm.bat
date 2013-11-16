@echo off
rem $Id: ojvm.bat,v 1.1 2002/11/27 16:30:28 cvs Exp $

rem ozoneEnv.bat *must* set the OZONE_CLASSPATH
setlocal

call ozoneEnv.bat
if "%OZONE_CLASSPATH%" == "" goto missing_cp

java -classpath "%OZONE_CLASSPATH%;%CLASSPATH%" %1 %2 %3 %4 %5 %6 %7 %8 %9

SET OZONE_CLASSPATH=

goto end_of_script

:missing_cp
echo Warning: OZONE_CLASSPATH is not set!

:end_of_script
endlocal
