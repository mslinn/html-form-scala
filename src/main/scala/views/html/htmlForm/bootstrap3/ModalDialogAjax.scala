package views.html.htmlForm.bootstrap3

import play.api.mvc.Call
import play.twirl.api.Html
import views.html.htmlForm.CsrfForm

/** @param cssClass will be prefixed with "btn-" if provided */
case class Button(
  label: String,
  id: String,
  cssClass: Option[String] = None
)

case class ModalDialogAjax(
  modalId: String = "msgModal",
  hotKey: String="",
  menuIcon: String="flash",
  menuText: String = "Show Dialog"
) {
  /** @param buttons Last button must be the OK (submit) button */
  def formBody(
    title: String,
    form: CsrfForm,
    buttons: List[Button] = Nil,
    showOnLoad: Boolean = true,
    style: Option[String] = None,
    cssClass: String = ""
  ): String = {
    val buttonsRendered = buttons.map { b =>
      val cssClass = b.cssClass.map { c => s"btn-$c" }.mkString
      s"""<button id="${ b.id }" class="btn $cssClass">${ b.label }</button>"""
    }.mkString("\n")

    val showOnLoadJS = if (!showOnLoad) "" else s"""     $$('#$modalId').modal('show');"""

    val styleStr = style.map(s => s"""style="$s"""").getOrElse("")

    val lastButton: String = buttons.lastOption.map { button =>
      s"""$$("#${ button.id }").click(function(e) { modalOkButtonClickHandler(e); });"""
    }.getOrElse("")

    s"""<div id="$modalId" class="modal hide fade $cssClass" data-keyboard="true" tabindex="-1" $styleStr>
       |  <div class="modal-header">
       |    <h3>$title</h3>
       |  </div>
       |  <div class="modal-body">
       |    ${ form.render.toString() }
       |  </div>
       |  <div class="modal-footer">
       |    $buttonsRendered
       |  </div>
       |</div>
       |
       |<script>
       |  $$(window).load(function() {
       |    $showOnLoadJS
       |
       |    $lastButton
       |
       |    $$('#${ form.id }').submit(function(e) {
       |      e.preventDefault();
       |      modalOkButtonClickHandler(e);
       |    });
       |
       |    function modalOkButtonClickHandler(e) {
       |      e.preventDefault();
       |      var modalForm = $$("#${ form.id }");
       |      $$(e.target.parentNode.parentNode).modal('hide');
       |      $$.post(modalForm.attr("action"), modalForm.serialize());
       |    }
       |
       |    $$('#$modalId').on('show', function (e) {
       |      $$(this).css({
       |        'margin-left': function () {
       |          return -($$(this).width() / 2);
       |        }
       |      });
       | });
       |
       | $$('#$modalId').on('shown', function () {
       |    $$(this).find(':text,textarea,select').filter(':visible:first').focus();
       |  });
       |
       |  });</script>
       |""".stripMargin
  }

  val hotKeyCall: Call = Call("GET", s"#$modalId")
  val hotKeyJS: String = hotKey.toLowerCase
  val menuItem =
    Html( s"""<a href="#$modalId" data-toggle="modal"><i class="glyphicon glyphicon-$menuIcon"></i> $menuText
             |  ${ if (hotKey.nonEmpty) s"<div class='keyHelp'>$hotKey</div>" else "" }</a>
             |""".stripMargin)
}
