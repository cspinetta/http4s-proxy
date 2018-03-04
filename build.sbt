val artifactId = "http4s-proxy"

organization := "com.cspinetta"
name := artifactId
scalaVersion := "2.12.4"

fork in run := true

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture")

val Http4sVersion   = "0.18.0"
val CirceVersion    = "0.8.0"

libraryDependencies ++= Seq(
  "org.http4s"              %%  "http4s-blaze-server"     % Http4sVersion,
  "org.http4s"              %%  "http4s-circe"            % Http4sVersion,
  "org.http4s"              %%  "http4s-dsl"              % Http4sVersion,
  "org.http4s"              %%  "http4s-blaze-client"     % Http4sVersion,
  "ch.qos.logback"          %   "logback-classic"         % "1.2.1",
  "com.github.pureconfig"   %%  "pureconfig"              % "0.7.1",
  "io.circe"                %%  "circe-generic"           % CirceVersion,
  "io.circe"                %%  "circe-java8"             % CirceVersion,
  "io.circe"                %%  "circe-generic-extras"    % CirceVersion,
  "org.scalatest"           %   "scalatest_2.12"          % "3.0.3"                   % "test",
  "org.mockito"             %   "mockito-core"            % "2.7.22"                  % "test"
)

mainClass := Some("com.cspinetta.Server")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

