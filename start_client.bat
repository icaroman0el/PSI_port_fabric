@echo off
setlocal

cd /d "%~dp0"

if exist ".gradle\jdks\jdk21\jdk-21.0.11+10\bin\java.exe" (
    set "JAVA_HOME=%CD%\.gradle\jdks\jdk21\jdk-21.0.11+10"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
    echo Using bundled JDK 21: %JAVA_HOME%
) else if defined JAVA_HOME (
    echo Using JAVA_HOME: %JAVA_HOME%
) else (
    echo JAVA_HOME is not set; using java from PATH.
)

call gradlew.bat runClient --console=plain

pause
endlocal
