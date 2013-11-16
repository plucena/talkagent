rem  use one of the following to startup the server
rem  this script assumes the jvm (either java or jview) is in path.

rem  -- with jdk version 1.2 +
java -jar jzHttpSrv.jar -noui

rem -- with jdk version 1.2+, no ui
rem java -jar jzHttpSrv.jar -noui

rem -- with jdk version 1.1+
rem java -classpath jzHttpSrv.jar com.BajieSoft.HttpSrv.jzHttpSrv

rem -- with jdk version 1.1+, no ui
rem java -classpath jzHttpSrv.jar com.BajieSoft.HttpSrv.jzHttpSrv -noui

rem -- with MS jview
rem jview -cp jzHttpSrv.jar com.BajieSoft.HttpSrv.jzHttpSrv

rem -- with MS jview, no UI
rem jview -cp jzHttpSrv.jar com.BajieSoft.HttpSrv.jzHttpSrv -noui