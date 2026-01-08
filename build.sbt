val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "chess",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test))

// JavaFX / ScalaFX
val javafxVersion = "21.0.2"
val scalafxVersion = "21.0.0-R32"

// Running JavaFX from sbt is more reliable when forking.
fork := true

libraryDependencies += "org.scalafx" %% "scalafx" % scalafxVersion
libraryDependencies ++= {
  val os = System.getProperty("os.name").toLowerCase
  val arch = System.getProperty("os.arch").toLowerCase

  val platform =
    if (os.contains("mac")) {
      // Apple Silicon needs mac-aarch64, Intel mac uses mac
      if (arch.contains("aarch64") || arch.contains("arm64")) "mac-aarch64" else "mac"
    } else if (os.contains("win")) "win"
    else if (os.contains("linux")) {
      if (arch.contains("aarch64") || arch.contains("arm64")) "linux-aarch64" else "linux"
    } else {
      throw new Exception(s"Unsupported platform: os=$os arch=$arch")
    }

  Seq("base", "controls", "fxml", "graphics")
    .map(m => "org.openjfx" % s"javafx-$m" % javafxVersion classifier platform)
}

Compile / run / mainClass := Some("Chess")
