# 1KeySL
A little program that allows you to modify read/write permission of specified file with 1 key.
Global Key logger from: https://github.com/kristian/system-hook/releases

# Pre-Req
1. Import JAR
2. Latest JDK or JRE

# Note
Pressing F12 will stop capturing keystroke and exit program immediately.

# Motive
The idea came to me when I was playing a game that allows save&load technique to avoid death and loss. The operation is simple: change the read-only option of the game document. However, switching between windows and modifing file write permission is annoying during the game. Therefore, I want to write a program that changes the permission by hitting a key. JAVA itself doesn't seem to support global key logger but JNI does.
