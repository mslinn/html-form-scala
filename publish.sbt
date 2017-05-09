// bintray settings
bintrayOrganization := Some("micronautics")
bintrayRepository := "play"
bintrayVcsUrl := Some(s"git@github.com:mslinn/${ name.value }.git")

// sbt-site settings
enablePlugins(SiteScaladocPlugin)
siteSourceDirectory := target.value / "api"
publishSite

// sbt-ghpages settings
enablePlugins(GhpagesPlugin)
git.remoteRepo := s"git@github.com:mslinn/${ name.value }.git"

/* import java.nio.file.Path

doc in Compile ~= { (value: java.io.File) => // enhance doc command to also replace the CSS
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
