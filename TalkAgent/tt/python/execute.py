#!/usr/local/bin/python

import ttkb

TTROOT='/root/tt'

try:
  ttkb.cmd(['qtype=obj&qword=tennis-ball&-assert=1'])
except:
  print 'An error occurred processing the query:'
  print '<pre>'
  for key in form.keys():
    print key+': &lt;'+form[key].value+'&gt;'
  print '</pre>'

