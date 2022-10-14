scalaVersion             := "2.13.9"
name                     := "mainservice"
organization             := "com.organization"
scalafmtOnCompile        := true
Test / fork              := true
Test / parallelExecution := true
ThisBuild / version      := "0.1.1"

lazy val Versions = new {
    val kindProjector = "0.13.2"
    val scalamacros = "2.1.1"
    val http4s = "0.23.12"
    val zio = "2.0.2"
    val zhttp = "2.0.0-RC10"
    val zioJson = "0.3.0-RC10"
    val zioInteropCats = "3.3.0"
    val circe = "0.14.3"
    val scalaTest = "3.0.8"
    val randomDataGenerator = "2.8"
    val ciris = "3.0.0-RC1"
    val logback = "1.2.3"
    val h2database = "1.4.200"
    val quill = "3.4.10"
    val tapir = "1.1.2"
    val tapirZioHttpServer = "1.1.2"
    val openapiCirceYaml = "0.2.1"
    val scalaMigrations = "1.1.1"
}
addCompilerPlugin("org.typelevel" %% "kind-projector" % Versions.kindProjector cross CrossVersion.full)

// Scala libraries
libraryDependencies ++= Seq(
  "dev.zio" %% "zio"                                                 % Versions.zio,
  "dev.zio" %% "zio-interop-cats"                                    % Versions.zioInteropCats,
  "org.http4s" %% "http4s-core"                                      % Versions.http4s,
  "org.http4s" %% "http4s-dsl"                                       % Versions.http4s,
  "org.http4s" %% "blaze-core"                                       % Versions.http4s,
  "org.http4s" %% "blaze-http"                                       % Versions.http4s,
  "org.http4s" %% "http4s-circe"                                     % Versions.http4s,
  "io.circe" %% "circe-generic"                                      % Versions.circe,
  "io.getquill" %% "quill-jdbc"                                      % Versions.quill,
  "is.cir" %% "ciris"                                                % Versions.ciris,
  "is.cir" %% "ciris-circe"                                          % Versions.ciris,
  "is.cir" %% "ciris-circe-yaml"                                     % Versions.ciris,
  "is.cir" %% "ciris-enumeratum"                                     % Versions.ciris,
  "is.cir" %% "ciris-http4s"                                         % Versions.ciris,
  "is.cir" %% "ciris-refined"                                        % Versions.ciris,
  "com.softwaremill.sttp.tapir" %% "tapir-core"                      % Versions.tapir,
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"             % Versions.tapir,
  "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server"           % Versions.tapirZioHttpServer,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle"         % Versions.tapir,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"              % Versions.tapir,
  "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml"            % Versions.openapiCirceYaml,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"                % Versions.tapir,
  "org.scalatest" %% "scalatest"                                     % Versions.scalaTest           % "test",
  "com.danielasfregola" %% "random-data-generator"                   % Versions.randomDataGenerator % "test",
  "dev.zio" %% "zio-json"                                            % Versions.zioJson,
  "io.d11" %% "zhttp"                                                % Versions.zhttp,
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.8.2"
)

// Java libraries
libraryDependencies ++= Seq(
  "com.h2database" % "h2"              % Versions.h2database,
  "ch.qos.logback" % "logback-classic" % Versions.logback
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

Docker / packageName        := "openscoring-api-gateway"
Docker / dockerExposedPorts := Seq(8090)
