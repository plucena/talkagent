#!/bin/sh


#MAC OS X
if [ `uname -s` = "Darwin" ]; then
   JAVALIBEXT="/Library/Java/Extensions"
   INSTALLDIR="/Users/percival/TALKAGENT" 
   export JAVALIBEXT
   SHARPINSTALL="$INSTALLDIR" 
   export JAVALIBEXT
   chmod 777 *
fi


# LINUX 2.4
if [ `uname -s` = "Linux" ]; then
   JAVA=`java -jar ../agents/config.jar -java`
   SHARPINSTALL=`java -jar ../agents/config.jar -installdir`
   JAVALIBEXT="$JAVA/jre/lib/ext"   
   OZONEDIR="$SHARPINSTALL/platform/bin/kb/ozone-1.1/bin/*"
   export JAVALIBEXT SHARPINSTALL
   chmod 777 * $OZONEDIR
   export JAVALIBEXT
   chmod 777 *
fi


# CYGWIN ON WINDOWS XP
if [ `uname -s` = "CYGWIN_NT-5.1" ]; then
   JAVA=`java -jar ../agents/config.jar -java`
   SHARPINSTALL=`java -jar ../agents/config.jar -installdir`
   JAVALIBEXT="$JAVA/jre/lib/ext"   
   OZONEDIR="$SHARPINSTALL/platform/bin/kb/ozone-1.1/bin/*"
   export JAVALIBEXT SHARPINSTALL
   chmod 777 * $OZONEDIR
   export JAVALIBEXT
   chmod 777 *
fi



if [ -z "$1" ]
then
  exit 0
fi

case "${1}" in
        -link)
  		# Create links to libraries
		# TA LIBS
   		cp -f $SHARPINSTALL/platform/bin/lib/ta-lib-common.jar 	$JAVALIBEXT/
   		cp -f $SHARPINSTALL/platform/bin/lib/ucl.jar 		$JAVALIBEXT/
   		cp -f $SHARPINSTALL/platform/bin/lib/xml.jar 		$JAVALIBEXT/
   		cp -f $SHARPINSTALL/platform/bin/lib/xerces.jar 	$JAVALIBEXT/
   		cp -f $SHARPINSTALL/platform/bin/lib/javabeans.jar 	$JAVALIBEXT/
   		cp -f $SHARPINSTALL/platform/bin/lib/ftpserver.jar 	$JAVALIBEXT/
  		cp -f $SHARPINSTALL/platform/bin/lib/gnu-ftp-client.jar	$JAVALIBEXT/
   		cp -f $SHARPINSTALL/platform/bin/lib/fipa.jar 		$JAVALIBEXT/ 
   		cp -f $SHARPINSTALL/platform/bin/agents/xkt-kb.jar 	$JAVALIBEXT/ 
   		cp -f $SHARPINSTALL/platform/bin/agents/crm.jar 	$JAVALIBEXT/ 
		# OZONE LIBS
   		cp -f $SHARPINSTALL/platform/bin/lib/ta-lib-common.jar 			$SHARPINSTALL/platform/bin/kb/ozone-1.1/lib/ 
   		cp -f $SHARPINSTALL/platform/bin/kb/ozone-1.1/lib/ozone-1.1.jar 	$JAVALIBEXT/ 
   		cp -f $SHARPINSTALL/platform/bin/kb/ozone-1.1/lib/jakarta-regexp.jar 	$JAVALIBEXT/
		cp -f $SHARPINSTALL/platform/bin/kb/ozone-1.1/lib/log4j-1.2.jar 	$JAVALIBEXT/ 
		# IDE
  		cp -f $SHARPINSTALL/platform/bin/lib/ta-lib-common.jar $SHARPINSTALL/ide/bin/lib 
                ;;
esac




