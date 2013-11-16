#!/bin/bash
for cfile in *.c;
do
  hfile=`basename $cfile .c`.h;
  echo $cfile $hfile;
  cproto $cfile >$hfile;
done
