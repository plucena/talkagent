#!/bin/sh

# TT MAC OS X BUILD
if [ `uname -s` = "Darwin" ]; then
   echo " " 
   echo "* Starting Thought Treasure Server"
   echo " "
   cd ../tt/bin/
   chmod 777 * 
   ./ttmac.sh > ttlog
fi

# TT LINUX 2.4 BUILD
if [ `uname -s` = "Linux" ]; then
   echo " " 
   echo "* Starting Thought Treasure Server"
   cd ../tt/bin/
   chmod 777 *
   ./ttlinux.sh
#   ./ttlinux.sh > log
fi


# TT WIN32 BUILD
if [ `uname -s` = "CYGWIN_NT-5.1" ]; then
   echo " " 
   echo "* Starting Thought Treasure Server"
   chmod 777 *
    cd ../tt/bin
    ttwin.sh > log
fi

