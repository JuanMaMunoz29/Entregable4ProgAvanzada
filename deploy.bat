@echo off
set JAR=target\mi-playlist-0.0.1-SNAPSHOT.jar

echo [deploy] Construyendo JAR...
call mvnw.cmd -q package

echo [deploy] Levantando nueva versión...
start "" java -jar "%JAR%"
echo [deploy] App desplegada.

