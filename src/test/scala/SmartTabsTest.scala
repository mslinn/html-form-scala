import model.persistence.Id
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import play.twirl.api.Html
import views.html.htmlForm.bootstrap3._

@RunWith(classOf[JUnitRunner])
class SmartTabsTest extends WordSpec with MustMatchers with EitherValues with OptionValues {
  def addressTab: SmartTab[Long] = SmartTab[Long](
    href = "#Address",
    label = "Address",
    helpText = "Customer address",
    maybeLazyParams = Some(LazyParams("entity", Id(0L)))
  ) { Html("<p>Blah blah</p>") }

  def companyTab[T]: SmartTab[T] = SmartTab[T](
    href = "#Company",
    label = "Company",
    helpText = "Company details",
    isPrimary = true
  ) { Html("<p>Blah blah</p>") }

  "SmartTabs" should {
    val smartTabs = new SmartTabs[Long](None, List(companyTab, addressTab))
    val tabs: String = smartTabs.renderTabs.toString
    val contents: String = smartTabs.renderContents.toString

    "generate tabs" in {
      tabs shouldBe """<ul class="nav nav-tabs">
                      |  <li class='active' data-toggle="tooltip" title="Company details"><a href='#Company' data-toggle='tab'>Company</a></li>
                      |  <li data-toggle="tooltip" title="Customer address"><a href='#Address' data-toggle='tab' data-entity='entity' data-id='0'>Address</a></li>
                      |</ul><!-- End nav nav-tabs -->
                      |""".stripMargin
    }

    "generate tab contents" in {
      contents shouldBe """<div id="TabContent" class="tab-content">
                          | <div class='tab-pane fade in active' id='Company'>
                          |<p>Blah blah</p>
                          |</div><!-- End content tab #Company -->
                          |
                          |  <div class='tab-pane fade' id='Address' data-fetch='lazy'>
                          |
                          |</div><!-- End content tab #Address -->
                          |
                          |</div><!-- End #TabContent -->
                          |""".stripMargin
    }
  }

  "SmartTabs with fragId" should {
    val request = ReallyFakeRequest("GET", "http://localhost/path#Company")
    val fragId: Option[String] = SmartTabs.fragmentId(request)
    val smartTabs = new SmartTabs[Long](fragId, List(companyTab, addressTab))
    val tabs: String = smartTabs.renderTabs.toString
    val contents: String = smartTabs.renderContents.toString

    "generate the fragmentId" in {
      fragId.value shouldBe "#Company"
    }

    "generate tabs" in {
      tabs shouldBe """<ul class="nav nav-tabs">
                      |  <li class='active' data-toggle="tooltip" title="Company details"><a href='#Company' data-toggle='tab'>Company</a></li>
                      |  <li data-toggle="tooltip" title="Customer address"><a href='#Address' data-toggle='tab' data-entity='entity' data-id='0'>Address</a></li>
                      |</ul><!-- End nav nav-tabs -->
                      |""".stripMargin
    }

    "generate tab contents" in {
      contents shouldBe """<div id="TabContent" class="tab-content">
                          | <div class='tab-pane fade' id='Company'>
                          |<p>Blah blah</p>
                          |</div><!-- End content tab #Company -->
                          |
                          |  <div class='tab-pane fade' id='Address' data-fetch='lazy'>
                          |
                          |</div><!-- End content tab #Address -->
                          |
                          |</div><!-- End #TabContent -->
                          |""".stripMargin
    }
  }
}
