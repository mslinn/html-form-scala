package views.html.htmlForm

import play.api.data.Field
import play.api.i18n.{Lang, Messages}
import play.twirl.api.Html
import views.html.helper.{FieldConstructor, inputText}

/** See https://github.com/playframework/Play20/blob/master/framework/src/play/src/main/scala/views/helper/inputText.scala.html */
object inputText2 {
  def apply(field: Field, label: String, args: (Symbol, Any)*)
           (implicit handler: FieldConstructor, lang: Lang, messages: Messages): Html =
    inputText(field, args.toList ::: (if (label.endsWith("*")) {
      List('required -> "required")
    } else Nil) ::: List('_label -> (if (label.endsWith("*")) {
      import play.twirl.api.Html
      Html(s"${label.substring(0, label.length - 1)}<sup>*</sup>")
    } else Html(label))) ::: List('_help -> ""): _*)
}
