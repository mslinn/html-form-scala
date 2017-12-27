import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import play.twirl.api.Html
import views.html.htmlForm.bootstrap3._

@RunWith(classOf[JUnitRunner])
class InputterTest extends WordSpec with MustMatchers with EitherValues with OptionValues {
  "toHtml" should {
    "work" in {
      "string".toHtml shouldBe Html("string")
    }
  }

  "dataAttrs" should {
    "work" in {
      def dataAttrs(data: List[(String, String)]) = data.map { case (n, v) => s"data-$n='$v'" }.mkString(" ", " ", " ")

      dataAttrs(List("x" -> "y")) shouldBe " data-x='y' "
      dataAttrs(List("x" -> "y", "a" -> "b")) shouldBe " data-x='y' data-a='b' "
    }
  }
}
