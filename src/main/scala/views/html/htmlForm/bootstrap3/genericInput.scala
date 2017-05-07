package views.html.htmlForm.bootstrap3

import play.twirl.api.Html
import views.html.helper.FieldElements

object genericInput {
  def apply(elements: FieldElements): Html = Html {
    s"""<div class="control-group ${ if (elements.hasErrors) "error" else "" }">
       |  <label for="${ elements.id }" class="control-label">${ elements.label }</label>
       |  <div class="controls">
       |    ${ elements.input }
       |    <span class="help-inline">${ elements.errors.mkString(", ") }</span>
       |  </div>
       |</div>
       |""".stripMargin
  }
}
