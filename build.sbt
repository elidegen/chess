// build.sbt

// SBT Docker Plugin aktivieren
enablePlugins(DockerPlugin)

val scalaVersion = "3.4.2"

// Projekt-Konfiguration
lazy val root = project
  .in(file("."))
  .settings(
    name := "chess",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scalaVersion,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test
    ),

    // JavaFX / ScalaFX Konfiguration
    fork := true,
    Compile / run / connectInput := true,
    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",
    libraryDependencies ++= {
      val os = System.getProperty("os.name").toLowerCase
      val arch = System.getProperty("os.arch").toLowerCase

      val platform =
        if (os.contains("mac")) {
          if (arch.contains("aarch64") || arch.contains("arm64")) "mac-aarch64" else "mac"
        } else if (os.contains("win")) "win"
        else if (os.contains("linux")) {
          if (arch.contains("aarch64") || arch.contains("arm64")) "linux-aarch64" else "linux"
        } else {
          throw new Exception(s"Unsupported platform: os=$os arch=$arch")
        }

      Seq("base", "controls", "fxml", "graphics")
        .map(m => "org.openjfx" % s"javafx-$m" % "21.0.2" classifier platform)
    },
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.4.0",
    libraryDependencies += "com.lihaoyi" %% "ujson" % "3.1.3",

    // Docker Plugin Einstellungen
    dockerBaseImage := "openjdk:17-slim",  // Basis-Image für Docker
    dockerExposedPorts := Seq(8080),  // Expose den Port 8080, falls du eine Web-Schnittstelle hast
    dockerUpdateLatest := true,

    // Hauptklasse für das Schachspiel definieren
    Compile / run / mainClass := Some("app.Chess")  // Passe dies an die korrekte Main-Class an
  )
