package views.html.htmlForm

import play.api.data.Form
import play.api.mvc.{Call, RequestHeader}
import play.twirl.api.Html
import views.html.helper.form

/** Automatically adds a CSRF form field */
case class CsrfForm(id: String, action: Call, args: (Symbol, String)*)
                   (body: Option[Form[_]] => Html)
                   (implicit csrfHelper: CSRFHelper) {
  def render: Html = {
    val moreArgs: Seq[(Symbol, String)] = args :+ Symbol("id") -> id :+ Symbol("name") -> id
    val result: Html = form(action, moreArgs: _*)(Html(csrfHelper.formField.toString + body.toString))
    result
  }
}
