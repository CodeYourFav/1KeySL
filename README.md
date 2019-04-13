# 1KeySL
A little program that allows you to modify read/write permission of specified file with 1 key.
Global Key logger from: https://github.com/kristian/system-hook/releases

# Pre-Req
1. Import JAR
2. Latest JDK or JRE

# Note
Pressing F12 will stop capturing keystroke and exit program immediately.

# Motive
The project starts from a friend who asks me to create a small program that can change file permission with single keytroke. The objective is simple: change the read-only option of the selected document. However, switching between windows and modifing file write permission is annoying. Therefore, I needed to this program to do it without switching windows. JAVA itself doesn't seem to support global key logger but JNI does.
