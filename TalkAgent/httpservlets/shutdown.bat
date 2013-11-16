rem the format is:  java sendCmd [port] [name] [pass] [command]
rem port is the port the server is running on
rem name/pass is the admin's name and pass

rem command usage:
rem         'shutdown' for shutdown
rem         'actreloadbeans'  to reload classpath (if you put .jar files in)
rem         'actreloadWars'   to reload wars (if you put new .war file in wars/ directory)
rem         'reloadSrvUsrDb'  to reload the server user database if you modified config/users.properties file manuly.

rem you can use either java or jview, what ever you have installed
java sendCmd 80 admin admin shutdown

rem jview sendCmd 80 admin admin shutdown