package views.html.htmlForm

import javax.inject.Inject
import play.api.Configuration
import play.api.mvc.RequestHeader
import play.twirl.api.{Html, HtmlFormat}
import views.html.helper.CSRF

class CSRFHelper @Inject()(configuration: Configuration) {
  lazy val enableCSRF: Boolean = Option(configuration.get[Boolean]("play.http.filterEnableCSRF")).contains(true)

  def formField(implicit request: RequestHeader): Html = if (enableCSRF) CSRF.formField else HtmlFormat.empty
}
