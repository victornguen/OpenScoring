ThisBuild / scalaVersion     := "2.13.9"
ThisBuild / version          := "0.1.1"
ThisBuild / organization     := "com.scalalazy"
ThisBuild / organizationName := "example"

val Version = new {
    val zio   = "2.0.2"
    val quill = "4.4.1"
    val zhttp = "2.0.0-RC11"
    val jwt   = "9.1.1"
}

lazy val root = (project in file("."))
    .settings(
        name := "bankApiService",
        libraryDependencies ++= Seq(
            "dev.zio"                       %% "zio"                           % Version.zio,
            "dev.zio"                       %% "zio-json"                      % "0.3.0",
            "io.d11"                        %% "zhttp"                         % Version.zhttp,
            "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.8.2",
            "io.getquill"                   %% "quill-zio"                     % Version.quill,
            "io.getquill"                   %% "quill-jdbc-zio"                % Version.quill,
            "com.h2database"                 % "h2"                            % "2.1.214",
            "com.typesafe"                   % "config"                        % "1.4.2",
            "com.github.jwt-scala"          %% "jwt-zio-json"                  % Version.jwt,
            "io.d11"                        %% "zhttp"                         % Version.zhttp % Test,
            "dev.zio"                       %% "zio-test"                      % Version.zio   % Test,
            "dev.zio"                       %% "zio-test-sbt"                  % Version.zio   % Test,
            "dev.zio"                       %% "zio-test-magnolia"             % Version.zio   % Test
        ),
        testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

Docker / packageName        := "openbanking-bankapi-service"
Docker / dockerExposedPorts := Seq(8111)