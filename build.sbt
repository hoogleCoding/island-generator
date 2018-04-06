name := "island_generator"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5-M1"
libraryDependencies += "com.github.ArlindNocaj" % "power-voronoi-diagram" % "-SNAPSHOT"
libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "util" % "2.3.0"
libraryDependencies += "com.softwaremill.macwire" %% "proxy" % "2.3.0"