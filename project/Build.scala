import sbt._
import Keys._
import PlayProject._

object ApplicationDependencies {
  val Akka = "2.0"
}

object ApplicationBuild extends Build {
  import ApplicationDependencies._

  val appName         = "wekanban"
  val appVersion      = "0.1-SNAPSHOT"

  val appDependencies = Seq(
    "org.scalaz" %% "scalaz-core" % "6.0.4" % "compile",
    "com.typesafe.akka" % "akka-actor" % Akka % "compile",
    "com.typesafe.akka" % "akka-agent" % Akka % "compile"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    // ...
  )
}
