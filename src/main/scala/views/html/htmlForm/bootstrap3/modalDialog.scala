package views.html.htmlForm.bootstrap3

object modalDialog {
  def apply(
     title: String,
     bodyContent: => String = "",
     id: String = "msgModal",
     onclick: String = "",
     showCloseButton: Boolean = true,
     showSaveButton: Boolean = true
   ): String = {
    val onClick = if (onclick.trim.isEmpty) "" else s"onclick='$onclick'"
    s"""<div id="$id" class="modal fade" data-keyboard="true" tabindex="-1" aria-labelledby="${ id }Label" aria-hidden="true">
       |  <div class="modal-dialog" role="document">
       |    <div class="modal-content">
       |      <div class="modal-header">
       |        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
       |        <h3 id="${ id }Label">$title</h3>
       |      </div><!-- End modal-header -->
       |      <div class="modal-body" id="modal-body-$id">
       |        $bodyContent
       |      </div><!-- End modal-body -->
       |      <div class="modal-footer">
       |        ${ if (!showCloseButton) "" else s"<button type='button' id='${ id }Dismiss' class='btn btn-default' data-dismiss='modal'>Dismiss</button>" }
       |        ${ if (!showSaveButton)  "" else s"<button type='button' id='${ id }Save' $onClick class='btn btn-primary'>Save</button>" }
       |      </div><!-- End modal-footer -->
       |    </div><!-- End modal-content -->
       |  </div><!-- End modal-dialog -->
       |</div><!-- End $id -->
       |""".stripMargin
  }
}
