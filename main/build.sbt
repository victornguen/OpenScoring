ThisBuild / scalaVersion     := "2.13.9"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "org.openscoring"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "main",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.2",
      "dev.zio" %% "zio-test" % "2.0.2" % Test,
      "dev.zio" %% "zio-test-sbt"      % "2.0.2" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.0.2" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
