package views.html.htmlForm

import play.api.data.Field
import play.api.i18n.{Lang, Messages}
import play.api.templates.PlayMagic.toHtmlArgs
import play.twirl.api.Html
import views.html.helper.{FieldConstructor, input}

/** Generate an HTML input checkbox for Twitter Bootstrap.
  * Example:
  * {{{
  * @checkbox(field = myForm("done"))
  * }}}
 *
  * @param field The form field.
  * @param args Set of extra HTML attributes ('''id''' and '''label''' are 2 special arguments).
  * @param handler The field constructor. */
object twitterCheckbox {
  def apply(field: Field, args: (Symbol, Any)*)
           (implicit handler: FieldConstructor, lang: Lang, messages: Messages): Html = {
    val boxValue = args.toMap.getOrElse('value, "true")
    input(field, args:_*) { (id, name, value, htmlArgs) =>
      Html(s"""<label class="checkbox">
              |  <input type="checkbox" name="$name" value="$boxValue"
              |    ${ if (value.contains(boxValue)) "checked" else "" } ${ toHtmlArgs(htmlArgs.filterKeys(_ != 'value)) } />
              |  ${ args.toMap.getOrElse('_text, "") }
              |</label>
              |""".stripMargin)
    }
  }
}
