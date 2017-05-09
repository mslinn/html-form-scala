import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest._
import org.scalatest.Matchers._
import play.twirl.api.Html
import views.html.htmlForm.bootstrap3.{LazyParams, SmartTab, SmartTabs}

// Structural types cannot use value classes in method parameter or return types
// See http://docs.scala-lang.org/overviews/core/value-classes.html
case class Id(value: Long) /*extends AnyVal*/

@RunWith(classOf[JUnitRunner])
class SmartTabsTest extends WordSpec with MustMatchers {

  def addressTab: SmartTab[Long] = SmartTab[Long](
    href = "#Address",
    label = "Address",
    helpText = s"Customer address",
    maybeLazyParams = Some(LazyParams("entity", Id(0L)))
  ) {Html("<p>Blah blah</p>")}

  def companyTab: SmartTab[Long] = SmartTab[Long](
    href = "#Company",
    label = "Company",
    helpText = s"Company details"
  ) {Html("<p>Blah blah</p>")}

  val smartTabs = new SmartTabs[Long](None, List(companyTab, addressTab))

  val tabs: String = smartTabs.renderTabs.toString
  val contents: String = smartTabs.renderContents.toString

  "SmartTabs" should {
    "generate tabs" in {
      tabs === """<ul class="nav nav-tabs">
                 |  <li data-toggle="tooltip" title="Company details"><a href='#Company' data-toggle='tab'>Company</a></li>
                 |  <li data-toggle="tooltip" title="Customer address"><a href='#Address' data-toggle='tab'>Address</a></li>
                 |</ul><!-- End nav nav-tabs -->
                 |""".stripMargin
    }

    "generate tab contents" in {
      contents === """<div id="TabContent" class="tab-content">
                     | <div class='tab-pane fade' id='Company'>
                     |  <p>Blah blah</p>
                     |</div><!-- End content tab #Company -->
                     |
                     |  <div class='tab-pane fade' id='Address'>
                     |  <p>Blah blah</p>
                     |</div><!-- End content tab #Address -->
                     |
                     |</div><!-- End #TabContent -->
                     |""".stripMargin
    }
  }
}
