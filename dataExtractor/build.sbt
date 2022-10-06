ThisBuild / scalaVersion     := "2.13.9"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "org.openscoring"
ThisBuild / organizationName := "Scalalazy"

val zioVersion   = "2.0.2"
val quillVersion = "4.4.1"
val zhttpVersion = "2.0.0-RC11"

lazy val root = (project in file("."))
    .settings(
        name := "dataExtractor",
        libraryDependencies ++= Seq(
            "dev.zio"                       %% "zio"                           % zioVersion,
            "dev.zio"                       %% "zio-json"                      % "0.3.0",
            "io.d11"                        %% "zhttp"                         % zhttpVersion,
            "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.8.2",
            "io.getquill"                   %% "quill-zio"                     % quillVersion,
            "io.getquill"                   %% "quill-jdbc-zio"                % quillVersion,
            "com.h2database"                 % "h2"                            % "2.1.214",
            "com.typesafe"                   % "config"                        % "1.4.2",
            "dev.zio"                       %% "zio-test"                      % zioVersion   % Test,
            "dev.zio"                       %% "zio-test-sbt"                  % zioVersion   % Test,
            "dev.zio"                       %% "zio-test-magnolia"             % zioVersion   % Test
        ),
        testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )
