import java.nio.file.Path

organization := "com.micronautics"
name := "html-form-scala"
version := "0.1.6"
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

scalaVersion := "2.11.8"
//crossScalaVersions := Seq("2.11.8", "2.12.2")

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
     "-doc-source-url", "https://github.com/mslinn/html-form-scala/tree/master€{FILE_PATH}.scala"
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

val playVer = "2.5.6"

libraryDependencies ++= Seq(
  "com.github.nscala-time"  %% "nscala-time"          % "2.14.0"  withSources(),
  "com.micronautics"        %% "currency"             % "1.2.10"  withSources(),
  "com.typesafe.play"       %% "play"                 % playVer   % Provided withSources(),
  "com.typesafe.play"       %% "filters-helpers"      % playVer   % Provided withSources(),
  //
  "com.typesafe.play"       %% "play"                 % playVer   % Docs,
  "com.typesafe.play"       %% "filters-helpers"      % playVer   % Docs,
  //
  "org.scalatest"           %% "scalatest"            % "3.0.1"   % Test withSources(),
  "junit"                   %  "junit"                % "4.12"    % Test,
  "org.webjars"             %  "bootstrap-datepicker" % "1.6.1"   % Test,
  "org.webjars"             %  "bootstrap"            % "3.3.7-1" % Test
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

// bintray settings
bintrayOrganization := Some("micronautics")
bintrayRepository := "play"
bintrayVcsUrl := Some("git@github.com:mslinn/html-form-scala.git")

// sbt-site settings
enablePlugins(SiteScaladocPlugin)
siteSourceDirectory := target.value / "api"
publishSite

// sbt-ghpages settings
enablePlugins(GhpagesPlugin)
git.remoteRepo := "git@github.com:mslinn/html-form-scala.git"

/*doc in Compile ~= { (value: java.io.File) => // enhance doc command to also replace the CSS
  import java.nio.file.{Files, Paths, StandardCopyOption}
  val source: Path = Paths.get("src/site/latest/api/lib/template.css")
  val dest: Path = Paths.get("target/site/latest/api/lib/").resolve(source.getFileName)
  println(s"Copying $source to $dest")
  Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING)
  value
}*/

/*ghpagesPushSite ~= { _: Unit => // enhance doc command to also replace the CSS
  import java.nio.file.{Files, Paths, StandardCopyOption}
  val source: Path = Paths.get("src/site/latest/api/lib/template.css")
  val dest: Path = Paths.get("target/site/latest/api/lib/").resolve(source.getFileName)
  println(s"Copying $source to $dest")
  Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING)
  ()
}*/

/*previewSite ~= { _: Unit => // enhance doc command to also replace the CSS
  import java.nio.file.{Files, Paths, StandardCopyOption}
  val source: Path = Paths.get("src/site/latest/api/lib/template.css")
  val dest: Path = Paths.get("target/site/latest/api/lib/").resolve(source.getFileName)
  println(s"Copying $source to $dest")
  Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING)
  ()
}*/
