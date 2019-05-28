@echo off
IF {%3} == {} ( 
ant run-cmdline -Dmode=%1 -Dtag_id=%2 
) ELSE (
ant run-cmdline -Dmode=%1 -Dtag_id=%2 -Ddata=%3
)

