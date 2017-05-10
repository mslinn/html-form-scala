package views.html.htmlForm.bootstrap3

import com.micronautics.HasValue
import play.api.mvc.RequestHeader
import play.twirl.api.{Html, HtmlFormat}

object SmartTabs {
  /** Factory for a set of tabs.
    * @param maybeFragmentId is used to identify the active tab, based on the URL. */
  def apply[U](maybeFragmentId: Option[String], tabs: SmartTab[U]*): SmartTabs[U] =
    new SmartTabs[U](maybeFragmentId, tabs.toList)

  /** @return Some(fragment-id) extracted from path (text following #, including the #), or None if not present */
  @inline def fragmentId(implicit request: RequestHeader): Option[String] = {
    val path = request.path
    val i = path.lastIndexOf("#")
    if (i >= 0) Some(path.substring(i)) else None
  }
}

/** See the unit tests for usage examples */
case class SmartTabs[U](maybeFragmentId: Option[String], tabs: List[SmartTab[U]], tabContentId: String="TabContent") {
  def renderTabs: Html =
    Html(s"""<ul class="nav nav-tabs">
            |  ${ tabs.map { _.renderTab(maybeFragmentId) }.mkString("", "\n  ", "") }
            |</ul><!-- End nav nav-tabs -->
            |""".stripMargin)

  def renderContents: Html =
    Html(s"""<div id="$tabContentId" class="tab-content">
            | ${ tabs.map { _.renderContent(maybeFragmentId) }.mkString("", "\n  ", "") }
            |</div><!-- End #$tabContentId -->
            |""".stripMargin)
}

/** When present, signifies that a tab should be lazily loaded.
  * JavaScript is responsible for figuring out how to load the required HTML, given `entity` and `id`. */
case class LazyParams[U](entity: String, id: HasValue[U])

/** Once a `SmartTab` instance has been created, the tab and its contents are separately rendered by invoking
  * `renderTab` and `renderContents`, respectively.
  * @param href link to content with leading #.
  * @param label used as the value of a `title` attribute, which appears when the user hovers over the tab.
  * @param helpText must not contain a single quote character
  * @param isVisible The tab will be visible if this parameter is `true`.
  * @param isPrimary The tab contents will initially be displayed if this parameter is `true`.
  * @param maybeLazyParams if `nonEmpty`, creates tabs with an `href` that has `data-entity` and `data-id` attributes, like the following.
  * { <ul class="nav nav-tabs">
  *     <li data-toggle="tooltip" title="blah blah"><a href='#LectureAbout' data-toggle='tab' data-entity='lecture' data-id='62'>blah blah</a></li>
  *   </ul> }
  *   See `resoures/lazyload.js` for an example of how to lazily load the contents of a tab.
  * @param notReady If true, the `glyphicon-ban-circle` icon will be displayed next to the tab text, and the `preFlight` class will be applied to the tab itself.
  * @param noPadding If true, the tab will be displayed as small as possible */
case class SmartTab[U](
  href: String,
  label: String,
  helpText: String = "",
  isVisible: Boolean = true,
  isPrimary: Boolean = false,
  maybeLazyParams: Option[LazyParams[U]] = None,
  notReady: Boolean = false,
  noPadding: Boolean = false
)(content: => Html) {
  lazy val isShown: Boolean = isVisible && (maybeLazyParams.isDefined || content.toString().trim.nonEmpty)

  def renderTab(maybeFragmentId: Option[String] = None): Html = if (isShown) {
    val activeClass: String = if (maybeFragmentId.map(_ == href).getOrElse(isPrimary)) "active" else ""
    val readyClass: String  = if (notReady) " preFlight" else ""

    val classes: String     = s"$activeClass$readyClass".trim
    val classesAttr: String = if (classes.nonEmpty) s"class='$classes' " else ""
    val dataTab: String     = if (href.startsWith("#")) " data-toggle='tab'" else ""
    val lazyEntity: String  = if (maybeLazyParams.isDefined) s" data-entity='${ maybeLazyParams.map(_.entity).mkString }'" else ""
    val lazyId: String      = if (maybeLazyParams.isDefined) s" data-id='${ maybeLazyParams.map(_.id.value).mkString }'" else ""
    val ready: String       = if (notReady) "<i class='glyphicon glyphicon-ban-circle'></i>"  else ""

    val href2: String       = s"<a href='$href'$dataTab$lazyEntity$lazyId>$ready$label</a>"

    Html(s"""<li ${ classesAttr }data-toggle="tooltip" title="$helpText">$href2</li>""")
  } else HtmlFormat.empty

  def renderContent(maybeFragmentId: Option[String] = None): Html = if (isShown && href.startsWith("#")) {
    val linkId: String        = href.substring(1) // strip off leading #
    val active: String        = if (maybeFragmentId.map(_==linkId).getOrElse(isPrimary)) " in active" else ""
    val lazyAttribute: String = if (maybeLazyParams.isDefined) " data-fetch='lazy'" else ""
    val lazyContent: String   = if (maybeLazyParams.isDefined) "" else content.toString().trim
    val style: String         = if (noPadding) " style='padding: 0'" else ""
    Html(s"""<div class='tab-pane fade$active'$style id='$linkId'$lazyAttribute>
            |$lazyContent
            |</div><!-- End content tab #$linkId -->
            |""".stripMargin)
  } else HtmlFormat.empty
}

object SmartTab {
  /** Factory for an invisible tab with no label and no contents */
  def empty[T]: SmartTab[T] = SmartTab[T](href="", label="", isVisible=false) { HtmlFormat.empty }
}
