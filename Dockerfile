FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1

# Hinzufügen der GPG-Schlüssel für Debian Bullseye
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 0E98404D386FA1D9 6ED0E7B82643E131 605C66F00D6C9793

# Aktualisieren der Paketquellen auf Bullseye (neueste Debian-Version)
RUN sed -i 's/stretch/bullseye/g' /etc/apt/sources.list && \
    sed -i 's/buster/bullseye/g' /etc/apt/sources.list

# Installiere notwendige X11-Bibliotheken und JavaFX-Abhängigkeiten
RUN apt-get update && \
    apt-get install -y libx11-dev libxext-dev libxrender-dev libxrandr-dev \
    libxi6 libgdk-pixbuf2.0-0 libxxf86vm1 libglu1-mesa libxtst6 \
    libfreetype6 libfontconfig1

WORKDIR /chess
ADD . /chess

RUN sbt assembly

CMD ["java", "-jar", "/chess/target/scala-3.4.2/chess-assembly-0.1.0-SNAPSHOT.jar"]
