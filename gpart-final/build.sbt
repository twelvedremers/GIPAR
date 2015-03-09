import sbt._
import sbt.Keys._
import play.Project._

play.Project.playJavaSettings

name := """GIPAR"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.webjars"   %% "webjars-play"  % "2.2.0",
  "org.webjars" % "bootstrap" % "3.0.0" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "1.8.3",
  "com.typesafe.play" %% "play-mailer" % "2.4.0-RC1"
) 

resolvers ++= Seq(
  "webjars" at "http://webjars.github.com/m2"
)
