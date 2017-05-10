package views.html

/** This is yet another approach to providing HTML form widgets for Play Framework using Scala.
  * The [[https://playframework.com/documentation/latest/api/scala/index.html#play.api.data.package form widgets]]
  * provided with Play Framework feature a minimum of parameters and have an arcane syntax as a result.
  * Those widgets are rendered within a &lt;dl/&gt; tags, which is only useful for a narrow range of scenarios.
  * Most HTML5 features are not supported "out of the box" so it is common to spend a lot of time crafting wrappers around the standard widgets.
  *
  * The [[https://github.com/adrianhurt/play-bootstrap Play Bootstrap]] widgets require Twitter Bootstrap,
  * but otherwise its API is similar to the Play Framework form widget API.
  *
  * [[http://www.lihaoyi.com/scalatags/ ScalaTags]] is another library for generating HTML using Scala.
  *
  * This package produces output that closely resembles Play Bootstrap's output,
  * but it uses many explicit parameters instead of following the Play Framework API approach.
  * Widgets in the `views.html.htmlForm` package are HTML5 compatible,
  * and include a [[htmlForm.CSRFHelper CSRF form helper]].
  *
  * Widgets related to Twitter Bootstrap 3.x are provided in the `views.html.htmlForm.bootstrap3` package.
  * Two flavors of modal dialog are provided, [[htmlForm.bootstrap3.SmartTabs]] and a [[htmlForm.bootstrap3.datePicker date picker]].
  * The [[htmlForm.bootstrap3.HtmlForm]] object contains `checkbox`es, `select`s, and various flavors of `input` for
  * email, URLs, currency, passwords, percentages, range-limited numeric values and much more.
  *
  * Widgets return String, not Html, for efficiency when using Plain Old Scala views.
  * The `bootstrap3` class enriches `String` by adding a `toHtml` method. */
package object htmlForm {
  /** Generic low-level method */
  def tag(start: String, end: String, content: String*): String =
    s"""$start
       |${ content.mkString("\n") }
       |$end
       |""".stripMargin
}
