val scala3Version = "3.7.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "fiRlefanz",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

      libraryDependencies ++= Seq(
        "org.scalameta" %% "munit" % "1.0.0" % Test,
        "org.scalactic" %% "scalactic" % "3.2.14",
        "org.scalatest" %% "scalatest" % "3.2.14" % Test
      )
  )
