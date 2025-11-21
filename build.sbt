val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "chess",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    // coverageMinimumStmtTotal := 100,
    coverageExcludedFiles := ".*Main\\.scala"
    // libraryDependencies ++= Seq(
    //   // "org.scalameta" %% "munit" % "1.0.0" % Test,
    //   "org.scalactic" %% "scalactic" % "3.2.14",
    //   "org.scalatest" %% "scalatest" % "3.2.14" % Test
    //   // "org.apache.commons" % "commons-lang3" % "3.20.0",
    //   // "commons-io" % "commons-io" % "2.21.0"
    // ))
  )
