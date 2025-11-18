#!/usr/bin/env bash
set -e

JAR="target/mi-playlist-0.0.1-SNAPSHOT.jar"
PID_FILE="playlist.pid"

echo "[deploy] Construyendo JAR..."
./mvnw -q package

echo "[deploy] Matando instancia anterior (si existe)..."
if [ -f "$PID_FILE" ]; then
  kill "$(cat "$PID_FILE")" 2>/dev/null || true
  rm "$PID_FILE"
fi

echo "[deploy] Levantando nueva versión..."
nohup java -jar "$JAR" > playlist.log 2>&1 &

echo $! > "$PID_FILE"
echo "[deploy] App desplegada con PID $(cat $PID_FILE)"