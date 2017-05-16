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

organization := "com.micronautics"
name := "html-form-scala"
version := "0.2.0"
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

scalaVersion := "2.11.11"
//crossScalaVersions := Seq("2.11.11", "2.12.2")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-target:jvm-1.8",
  "-unchecked",
  "-Ywarn-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Xlint"
)

scalacOptions in (Compile, doc) ++= baseDirectory.map {
  (bd: File) => Seq[String](
     "-sourcepath", bd.getAbsolutePath,
     "-doc-source-url", "https://github.com/mslinn/{name.value}/tree/master€{FILE_PATH}.scala"
  )
}.value

javacOptions ++= Seq(
  "-Xlint:deprecation",
  "-Xlint:unchecked",
  "-source", "1.8",
  "-target", "1.8",
  "-g:vars"
)

resolvers ++= Seq(
  "micronautics/scala on bintray" at "http://dl.bintray.com/micronautics/scala"
)

val playVer = "2.5.12" // scalatestplus-play "2.0.0" is built with this version
val stppVer = "2.0.0"
libraryDependencies ++= Seq(
  "com.github.nscala-time"  %% "nscala-time"          % "2.14.0"   withSources(),
  "com.micronautics"        %% "currency"             % "1.2.10"   withSources(),
  "com.micronautics"        %% "has-value"            % "1.0.1"    withSources(),
  "com.typesafe.play"       %% "play"                 % playVer    % Provided withSources(),
  "com.typesafe.play"       %% "filters-helpers"      % playVer    % Provided withSources(),
  //
  "com.typesafe.play"       %% "play"                 % playVer    % Docs,
  "com.typesafe.play"       %% "filters-helpers"      % playVer    % Docs,
  //
  "com.micronautics"        %% "has-id"               % "1.0.1"    % Test withSources(),
  "junit"                   %  "junit"                % "4.12"     % Test,
  "org.scalatest"           %% "scalatest"            % "3.0.1"    % Test withSources(),
  "org.scalatestplus.play"  %% "scalatestplus-play"   % stppVer    % Test withSources(),
  "org.webjars"             %  "bootstrap-datepicker" % "1.6.1"    % Test,
  "org.webjars"             %  "bootstrap"            % "3.3.7-1"  % Test
)

logLevel := Level.Warn

// Only show warnings and errors on the screen for compilations.
// This applies to both test:compile and compile and is Info by default
logLevel in compile := Level.Warn

// Level.INFO is needed to see detailed output when running tests
logLevel in test := Level.Info

// define the statements initially evaluated when entering 'console', 'console-quick', but not 'console-project'
initialCommands in console := """
                                |""".stripMargin

cancelable := true

sublimeTransitive := true
