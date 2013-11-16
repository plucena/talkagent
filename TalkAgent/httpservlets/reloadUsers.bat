rem the format is:  java sendCmd [port] [name] [pass] [command]
rem port is the port the server is running on
rem name/pass is the admin's name and pass
rem use this file to reload the server user database if you modified config/users.properties file manuly.

rem you can use either java or jview, what ever you have installed
java sendCmd 80 admin admin reloadSrvUsrDb

rem jview sendCmd 80 admin admin reloadSrvUsrDb