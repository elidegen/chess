# Verwende das Scala SBT-Image mit GraalVM (Java 17) als Basis
FROM sbtscala/scala-sbt:graalvm-ce-22.3.3-b1-java17_1.12.0_3.7.4

# Setze das Arbeitsverzeichnis im Container
WORKDIR /chess

# Kopiere dein gesamtes Projekt in das Arbeitsverzeichnis
COPY . /chess

# Installiere notwendige X11- und JavaFX-Abhängigkeiten (GTK/X11)
RUN microdnf install -y \
    gtk3 \
    libXrender \
    libXtst \
    libXi \
    libXrandr \
    alsa-lib \
    mesa-libGL \
    mesa-dri-drivers \
 && microdnf clean all

# Führe SBT aus, um alle Abhängigkeiten herunterzuladen und das Projekt zu bauen
RUN sbt update

# Starte das Projekt
CMD ["sbt", "run"]
