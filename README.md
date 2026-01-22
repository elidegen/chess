[![Coverage Status](https://coveralls.io/repos/github/elidegen/chess/badge.svg?branch=main)](https://coveralls.io/github/elidegen/chess?branch=main)

# Chess – Docker GUI (noVNC)

Dieses Projekt wird als **fertiges Docker-Image** ausgeliefert.  
Du brauchst **keinen Docker-Account**, **kein Java**, **kein X11** – nur Docker.

Die grafische Oberfläche läuft **im Container** und wird über den Browser angezeigt (noVNC).

## Voraussetzungen

- Docker installiert  
  - macOS (Intel oder Apple Silicon)
  - Linux (x86_64 oder ARM64)
  - Windows (Docker Desktop)

## Docker Image pullen

```
docker pull ghcr.io/elidegen/chess:novnc
```

## Image ausführen
```
docker run --rm -p 127.0.0.1:6080:6080 ghcr.io/elidegen/chess:novnc
```

## GUI ausführen => link im browser öffnen
```
http://localhost:6080/vnc.html
```
