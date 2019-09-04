/* Copyright 2012-2016 Micronautics Research Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. */

cancelable := true

crossScalaVersions := Seq("2.11.12", "2.12.9", "2.13.0")

developers := List(
  Developer("mslinn",
            "Mike Slinn",
            "mslinn@micronauticsresearch.com",
            url("https://github.com/mslinn")
  )
)

// define the statements initially evaluated when entering 'console', 'console-quick', but not 'console-project'
initialCommands in console := """
                                |""".stripMargin

javacOptions ++= Seq(
  "-Xlint:deprecation",
  "-Xlint:unchecked",
  "-source", "1.8",
  "-target", "1.8",
  "-g:vars"
)

//val playVer = "2.5.12" // scalatestplus-play "2.0.0" is built with this version
val playVer = "2.7.3"  // scalatestplus-play "3.1.2" is built with this version
val stppVer = if (playVer.startsWith("2.7")) "4.0.3" else "2.0.0"
libraryDependencies ++= Seq(
  "com.micronautics"        %% "currency"             % "1.3.0"    withSources(),
  "com.micronautics"        %% "has-value"            % "1.1.0"    withSources(),
  "com.typesafe.play"       %% "play"                 % playVer    % Provided withSources(),
  "com.typesafe.play"       %% "filters-helpers"      % playVer    % Provided withSources(),
  //
  "com.typesafe.play"       %% "play"                 % playVer    % "docs",
  "com.typesafe.play"       %% "filters-helpers"      % playVer    % "docs",
  //
  "com.micronautics"        %% "has-id"               % "1.3.0"    % Test withSources(),
  "junit"                   %  "junit"                % "4.12"     % Test,
  "org.scalatest"           %% "scalatest"            % "3.0.8"    % Test withSources(),
  "org.scalatestplus.play"  %% "scalatestplus-play"   % stppVer    % Test withSources(),
  "org.webjars"             %  "bootstrap-datepicker" % "1.6.1"    % Test,
  "org.webjars"             %  "bootstrap"            % "3.3.7-1"  % Test
)

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

logLevel := Level.Warn

// Only show warnings and errors on the screen for compilations.
// This applies to both test:compile and compile and is Info by default
logLevel in compile := Level.Warn

// Level.INFO is needed to see detailed output when running tests
logLevel in test := Level.Info

name := "html-form-scala"

organization := "com.micronautics"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-target:jvm-1.8",
  "-unchecked",
  "-Xlint"
)

scalacOptions in (Compile, doc) ++= baseDirectory.map {
  bd: File => Seq[String](
     "-sourcepath", bd.getAbsolutePath,
     "-doc-source-url", "https://github.com/mslinn/html-form-scala/tree/master€{FILE_PATH}.scala"
  )
}.value

scalaVersion := "2.13.0"

scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/mslinn/$name"),
    s"git@github.com:mslinn/$name.git"
  )
)

sublimeTransitive := true

resolvers ++= Seq(
  "micronautics/scala on bintray" at "https://dl.bintray.com/micronautics/scala"
)

ThisBuild / turbo := true

version := "0.3.0"
