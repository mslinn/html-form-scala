package views.html

package object htmlForm {
  def tag(start: String, end: String, content: String*): String =
    s"""$start
       |${content.mkString("\n")}
       |$end
       |""".stripMargin
}
