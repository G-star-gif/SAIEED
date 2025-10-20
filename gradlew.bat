@echo off
setlocal
set PRG=%~dp0%~nx0
set PRGDIR=%~dp0
"%JAVA_HOME%\bin\java" -cp "%PRGDIR%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
