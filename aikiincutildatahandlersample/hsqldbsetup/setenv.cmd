echo off
set JAVA_HOME=E:\jdk1.7.0_01
set WINDOW_PATH=C:\WINDOWS\system32;C:\WINDOWS;.
set CLASSPATH=.;%JAVA_HOME%\jre\lib\rt.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
set CLASSPATH=%CLASSPATH%
set PATH=.;%JAVA_HOME%\bin;%WINDOW_PATH%;

cls
echo CLASSPATH=%CLASSPATH%
echo .
echo PATH=%PATH%
echo .
