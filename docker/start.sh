#!/usr/bin/env bash
set -euo pipefail

# Auflösung/Display einstellen
WIDTH="${WIDTH:-1280}"
HEIGHT="${HEIGHT:-720}"
DEPTH="${DEPTH:-24}"

VNC_AUTH="-nopw"

echo "Starting Xvfb on ${DISPLAY} (${WIDTH}x${HEIGHT}x${DEPTH})..."
Xvfb ${DISPLAY} -screen 0 "${WIDTH}x${HEIGHT}x${DEPTH}" -ac +extension GLX +render -noreset &
sleep 1

echo "Starting window manager (fluxbox)..."
fluxbox >/tmp/fluxbox.log 2>&1 &
sleep 1

echo "Starting x11vnc on port 5900..."
x11vnc -display ${DISPLAY} -forever -shared -rfbport 5900 ${VNC_AUTH} -bg

echo "Starting noVNC on port 6080..."
# novnc liefert üblicherweise /usr/share/novnc/ und nutzt websockify
websockify --web=/usr/share/novnc/ 6080 localhost:5900 >/tmp/novnc.log 2>&1 &
sleep 1

echo "Starting Java app..."
exec java -jar /app/app.jar