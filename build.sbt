ThisBuild / scalaVersion := "3.4.2"

lazy val root = (project in file("."))
  .settings(
    name := "chess",
    version := "0.1.0-SNAPSHOT",

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      ("org.scalafx" %% "scalafx" % "21.0.0-R32").excludeAll(ExclusionRule("org.openjfx")),
      "org.scala-lang.modules" %% "scala-xml" % "2.4.0",
      "com.lihaoyi" %% "ujson" % "3.1.3"
    ),

    libraryDependencies ++= {
      val os   = System.getProperty("os.name").toLowerCase
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

      val jfxVersion = "22.0.1"
      val jfxModules = Seq("base", "graphics", "controls", "fxml", "media")

      jfxModules.map(m => "org.openjfx" % s"javafx-$m" % jfxVersion classifier platform)
    },

    fork := true,
    Compile / run / mainClass := Some("app.Chess"),

    assembly / mainClass := Some("app.Chess"),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case x                        => MergeStrategy.first
    },
  )
