package views.html.htmlForm

import play.api.data.Field
import play.api.i18n.Lang
import play.twirl.api.Html
import views.html.helper.FieldConstructor

/**
  * @author mslinn */
object password {
  def apply(field: Field, label: String, args: (Symbol, Any)*)
           (implicit handler: FieldConstructor, lang: Lang): Html =
    Html {
      val label2 = if (label.endsWith("*")) s"${ label.substring(0, label.length-1) }<sup>*</sup>" else label
      s"""<dl ${ if (field.errors.nonEmpty) "class='error'" else "" } id="${ field.id }_label">
         |  <dt><label for="${ field.id }">$label2</label></dt>
         |    <dd><input type="password" id="${ field.id }" name="${ field.id }" value="${ field.value }">
         |  ${ if (field.errors.nonEmpty)  field.errors else "" }</dd>
         |</dl>
         |""".stripMargin
    }
}
