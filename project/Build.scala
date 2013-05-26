import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "scalextexample"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "commons-io" % "commons-io" % "2.1",
    "org.apache.commons" % "commons-lang3" % "3.1",
    "com.google.code.gson" % "gson" % "2.2.3"
  )

  val scalext = play.Project("scalext", appVersion, appDependencies, path = file("modules/scalext"))

  val scalextExample = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  ).dependsOn(scalext)
}
