package views.html.htmlForm

import play.twirl.api.Html

package object bootstrap3 {
  implicit class RichString(string: String) {
    def toHtml = Html(string)
  }
}
