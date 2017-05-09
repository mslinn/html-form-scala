package views.html.htmlForm.bootstrap3

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTime, Days, Months, Period, ReadablePeriod}
import play.api.data.Field
import play.twirl.api.Html

/** Although the `datePicker` is not dependant on Twitter Bootstrap, this object wraps in Bootstrap-centric HTML and CSS.
  * In the following usage example, `_form` is a [[play.api.data.Form]] instance.
  * {{{datePicker(name="from", field=_form("from"), label="Valid from")}}}
  * @see [[https://github.com/eternicode/bootstrap-datepicker]]
  * and [[http://bootstrap-datepicker.readthedocs.io/en/latest/]]. */
object datePicker {
  val format = "yyyy-MM-dd"
  val dateFormatter: DateTimeFormatter = DateTimeFormat.forPattern(format)
  val formatDisplay: String = format.toLowerCase

  def apply(
     name: String,
     field: Field,
     label: String,
     lookaheadPeriod: ReadablePeriod = Period.ZERO,
     readonly: Boolean = true,
     containerClass: String = "",
     labelClasses: String = ""
   ) = Html {
    val inputName = name
    val inputId = inputName.replace(".", "_") // Twirl does the same thing (field.id is the same as field.name, but with dots replaced by underscores)
    val _id = name.replace(".", "_")
    val outerDivId = _id + "Outer"
    val resetId = _id + "Reset"
    val iconId = _id + "Icon"

    val value: String = {
      val v = field.value.mkString.trim
      if (v.nonEmpty) v else ""
    }

    def lookaheadDate: DateTime = DateTime.now
      .withTimeAtStartOfDay
      .plus(Days.ONE)
      .plus(if (lookaheadPeriod == Period.ZERO) Months.ONE else lookaheadPeriod)

    val defaultViewDate = {
      val x = if (value.nonEmpty) value else dateFormatter.print(lookaheadDate)
      s"""data-date-default-view-date='$x'"""
    }

    val readOnly = if (readonly) "readonly" else ""

    s"""<div id="${ outerDivId }_container" ${ if (containerClass.isEmpty) "" else s"class='$containerClass'" }>
       |   <span class="$labelClasses">$label</span>
       |      <div id="$outerDivId" class="input-append date datepicker" data-date="$value"
       |          data-date-format="$formatDisplay" data-date-autoclose="true" data-date-clear-btn="true">
       |        <input id="$inputId" name="$inputName" value="$value" $readOnly style="cursor: pointer" placeholder="$formatDisplay" $defaultViewDate>
       |        <span class="add-on"><i class="glyphicon glyphicon-th"></i></span>
       |      </div>
       |      <span class="datePickerReset" id="#$resetId"><i class="glyphicon glyphicon-remove-circle" id="$iconId"></i></span>
       |     <script>
       |       $$(function(){
       |         $$('#$outerDivId').datepicker();
       |
       |         $$("#$iconId").click(function(e) {
       |           $$("#$inputId").val("");
       |         });
       |
       |         $$("#$resetId").click(function(e) {
       |           $$("#$inputId").clearDates();
       |         });
       |       });
       |     </script>
       |</div>
       |""".stripMargin
  }
}
