@echo off
rem $Id: ozone.bat,v 1.1 2002/11/27 16:30:29 cvs Exp $

call ojvm.bat -mx64000000 org.ozoneDB.core.Server %1 %2 %3 %4 %5 %6 %7 %8 %9
