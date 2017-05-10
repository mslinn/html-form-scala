package views.html.htmlForm

import play.twirl.api.Html

package object bootstrap3 {
  /** Provides a `toHtml` method to all methods in this package which return `String` */
  implicit class RichString(string: String) {
    def toHtml = Html(string)
  }
}
