package views.html.htmlForm

import currency.Currency
import play.api.data.{Field, Form}
import play.api.mvc.Call
import scala.language.implicitConversions

case class FadeValue(value: Boolean) extends AnyVal

/** @see http://stackoverflow.com/a/3508555/553865 */
sealed trait StringOrSeq[-T]

object StringOrSeq {
  implicit object SeqWitness extends StringOrSeq[Seq[String]]
  implicit object StringWitness extends StringOrSeq[String]
}

object HtmlForm {
  def value(fieldName: String)(implicit form: Form[_]): String = form(fieldName).value.mkString(",")

  def checkedFromName(fieldName: String, label: String="", classes: String="")
                     (implicit form: Form[_]): String = {
    val ckd = if (value(fieldName)=="true") "checked='checked'" else ""
    val fieldId = fieldName.replace('.', '_').replace('[', '_').replace("]", "")
    s"""<div class="checked $classes" id="${ fieldId }_container">
       |  <input type="checkbox" name="$fieldName" id="$fieldId" value="true" $ckd class="mediumCheckbox">
       |  ${ if (label.trim.isEmpty) "" else s"$label" }
       |</div>
       |""".stripMargin
  }

  def checkedFromValue(fieldName: String, value: Boolean, label: String="", classes: String=""): String = {
    val ckd = if (value) "checked='checked'" else ""
    val fieldId = fieldName.replace('.', '_').replace('[', '_').replace("]", "")
    s"""<div class="checked $classes" id="${ fieldId }_container">
       |  <input type="checkbox" name="$fieldName" id="$fieldId" value="true" $ckd class="mediumCheckbox">
       |  ${ if (label.trim.isEmpty) "" else s"$label" }
       |</div>
       |""".stripMargin
  }

  /** Compares against "true" and "false" values */
  def checked(field: Field, label: String="", classes: String="")
             (implicit form: Form[_]): String = {
      val ckd = if (List("true","on", "enabled").contains(value(field.name))) "checked='checked'" else ""
      s"""<div class="checked $classes" id="${ field.id }_container">
         |  <input type="checkbox" name="${ field.name }" id="${ field.id }" value="true" $ckd class="mediumCheckbox">
         |  ${ if (label.trim.isEmpty) "" else s"$label" }
         |</div>
         |""".stripMargin
    }

  /** @param optionValues if nonEmpty generate a <select> with the specified <option> name/value pairs
    * @param selectedValues if `optionValues` is nonEmpty use these values to add select attribute to <option>s
    * @param asCents implies isCurrency
    * @param containerClasses CSS classes applied to containing div
    * @param labelClasses CSS class applied to label preceding the input tag
    * @param style CSS style applied to input tag */
  def inputter(
    field: Field,
    label: String = "",
    asCode: Boolean = false,
    containerClasses: String = "",
    helpText: String = "",
    isCurrency: Boolean = false,
    asButton: Boolean = false,
    asCents: Boolean = false,
    isEmail: Boolean = false,
    isHidden: Boolean = false,
    isNumeric: Boolean = false,
    isPassword: Boolean = false,
    isPercentage: Boolean = false,
    isUrl: Boolean = false,
    labelClasses: String = "",
    maybeMin: Option[Double] = None,
    maybeReady: Option[(String, Boolean)] = None,
    optionValues: Seq[(String, String)] = Nil,
    maybePlaceholder: Option[String] = None,
    selectedValues: Seq[String] = Nil,
    isRequired: Boolean = false,
    style: String = "",
    suffixValue: String = ""
  ): String = {
    val testReady = maybeReady.exists(_._2)

    val asNumber: Boolean = isCurrency || isNumeric || isPercentage || asCents

    val inputClasses: String = if (isHidden) "" else "class='form-control" +
      ( if (asCode || isCurrency || isUrl || isEmail || asCents) " code" else "" ) +
      ( if (isPercentage) " input-group-with-suffix" else "" ) +
      ( if (testReady) " preFlight" else "" ) +
      "'"

    val describedBy: String = if (isHidden) ""
                              else if (isPercentage)              s"aria-describedby='${ field.id }Pct'"
                              else if (isCurrency || asCents)     s"aria-describedby='${ field.id }Cur'"
                              else if (suffixValue.trim.nonEmpty) s"aria-describedby='${ field.id }Val'"
                              else ""

    val help = if (isHidden || helpText.trim.isEmpty) "" else s"title='${ helpText.replace("'", "&#39;") }'"

    val id: String = field.id

    val leftCap: String =
      if (isCurrency || asCents) s"<span class='input-group-addon input-group-addon-prefix' id='${ field.id }Cur'>$$</span>"
      else ""

    val min: String = if (isHidden) "" else maybeMin.map(m => s"min='$m'").mkString

    val name: String = field.name

    val placeholder = maybePlaceholder.map(p => s" placeholder='$p'").mkString

    val requiredStr = if (isHidden) "" else if (isRequired) " required" else ""

    val rightCap: String =
      if (isPercentage) s"<span class='input-group-addon' id='${ field.id }Pct'>%</span>"
      else if (suffixValue.trim.nonEmpty)
       s"<span class='input-group-addon' id='${ field.id }Val'>${ suffixValue.trim }</span>"
      else ""

    val typeStr: String = if (asNumber) "type='number' step='any'"
      else if (isEmail)    "type='email'"
      else if (isUrl)      "type='url'"
      else if (isPassword) "type='password'"
      else if (isHidden)   "type='hidden'"
      else                 "type='text'"

    val value: String = field.value.map { value =>
      if (asCents) s"value='${ Currency(value.trim).toCents }'"
      else if (isCurrency) s"value='${ Currency(value.trim) }'"
      else s"value='${ value.trim }'"
    }.getOrElse("")

    val styleTag = if (isHidden || style.trim.isEmpty) "" else s"style='$style'"

    val element: String = if (optionValues.isEmpty) {
      s"<input $typeStr id='$id' name='$name' $value $min $styleTag $inputClasses $describedBy $help$requiredStr$placeholder>"
    } else {
      val options: Seq[String] = optionValues.map { case (n, v) =>
        s"  <option value='$v'${ if (selectedValues.contains(v)) " selected" else "" }>$n</option>"
      }
      s"""<select id='$id' name='$name' $styleTag $inputClasses $help$requiredStr>
         |${ options.mkString("\n") }
         |</select>
         |""".stripMargin
    }

    val labelFor = if (label.trim.isEmpty) ""
      else s"<label for='${ field.id }' id='${ field.id }_label' class='inputLabel $labelClasses'>$label</label>\n"

    val notReadyError = if (testReady) "" else
      maybeReady.map(r => s"\n  <div class='preFlight preFlightBottom' style='clear: both'>${ r._1 }</div>").mkString

    if (isHidden) element else if (asButton) {
      s"""<button class='btn $labelClasses'>$label</button>"""
    } else {
      val divClasses = if (asNumber) containerClasses else s"input-group $containerClasses".trim
      val id = s"${ field.id }_container"
      val (tag2Start, tag2End, divStart, divEnd) =
        if (asNumber) ("", "", s"<div class='$divClasses' id='$id'>\n", "\n</div>")
        else (s"<div class='$divClasses' id='$id'>", "</div>", "", "")
      s"""$divStart$labelFor$tag2Start
         |  $leftCap$element$rightCap
         |$tag2End$notReadyError$divEnd
         |""".stripMargin
    }
  }

  /** Works from a multivalue field (multiple instances of a field) */
  def multiCheckbox(fieldName: String, pmNameValues: List[(String, String)], label: String=""): String = {
    s"""${ if (label.isEmpty) "" else s"<dl>\n  <dt>$label</dt>\n  <dd>" }
       | ${ ( for {
                (name, value) <- pmNameValues
                valueTrim     =  value.trim
                ckd           =  if (valueTrim.nonEmpty) "checked='checked'" else ""
              } yield s"""<input type="checkbox" name="$fieldName[]" id="${ fieldName }_${ name }_checkbox" value="$name" $ckd class="bigCheckbox">
                         |$name
                         |""".stripMargin
            ).mkString("<br>\n")
          }
       |${ if (label.isEmpty) "" else s"</dd>\n  </dl>" }
       |""".stripMargin
  }

  def preFlightWarning(msg: String, ok: Boolean): String = if (ok) msg else s"""<span class="preFlight">$msg</span>"""

  def notProvided(
   value: Any,
   msg: String = "Not provided",
   asCode: Boolean = false,
   asEmail: Boolean = false,
   asImage: Boolean = false,
   asURL: Boolean = false,
   preFlight: Boolean = false
 )(implicit fade: FadeValue=FadeValue(false)): String = {
    val fadedValueClass = if (!fade.value) "" else " class='fadedValue'"

    def empty(value: Any) = if (preFlight && !fade.value) s"<span class='preFlight'><i>$msg</i></span>" else s"<i$fadedValueClass>$msg</i>"

    def doIt(value: Any) = {
      val v = value.toString.trim
      if (v.isEmpty) empty(v)
      else if (asURL) s"""<a href='$v'><code>$v</code></a>"""
      else if (asCode) s"<code>$v</code>"
      else if (asEmail) s"<code><a href='mailto:$v'>$v</a></code>"
      else if (asImage) s"<img src='$v'>"
      else v
    }

    value match {
      case maybeValue: Option[_] =>
        maybeValue.map(doIt).getOrElse(empty(value))

      case v => doIt(v)
    }
  }

  def expandedContent[T: StringOrSeq](content: T): String = content match {
    case string: String   => string
    case seq: Seq[_] => seq.mkString("\n")
  }

  def panelTable[T: StringOrSeq](title: String, id: String="", columns: Int=6)
                (content: => T): String =
    s"""<div class="panel panel-default col-md-$columns"${ if (id.trim.nonEmpty) s" id='${ id.trim }'" else "" }>
       |  <div class="panel-heading">$title</div>
       |  <table class="table">
       |${ expandedContent[T](content) }
       |  </table>
       |</div>
       |""".stripMargin

  def panel[T: StringOrSeq](title: String="", id: String="", enabled: Boolean=true, showHeading: Boolean=true)
           (content: FadeValue => T): String = {
    val heading = if (showHeading) s"""<div class="panel-heading">[${ trueFalseGlyph(enabled) }] $title</div>""" else ""
    s"""<div class="panel panel-default col-md-6"${ if (id.trim.nonEmpty) s" id='${ id.trim }'" else "" }>
       |  $heading
       |  ${ expandedContent[T](content(FadeValue(!enabled))) }
       |</div>
       |""".stripMargin
  }

  def panelRow(heading: String, value: String)
              (implicit fadeValue: FadeValue=FadeValue(false)): String =
    s"""<div${ if (fadeValue.value) " class='fadedValue'" else "" }>$heading</div>
       |<div${ if (fadeValue.value) " class='fadedValue'" else "" }>$value</div>
       |""".stripMargin

  def tableRow(heading: String, value: String, swap: Boolean=false, noWrap: Boolean=false): String = {
    val wrap = if (noWrap) " nowrap='nowrap'" else ""
    if (swap)
      s"""<tr>
         |  <td$wrap>$value</td>
         |  <th width="99%"$wrap>$heading</th>
         |</tr>
         |""".stripMargin
    else
      s"""<tr>
         |  <th$wrap>$heading</th>
         |  <td width="99%"$wrap>$value</td>
         |</tr>
         |""".stripMargin
  }

  def selector(field: Field, options: Seq[(String, String)], label: String=""): String = {
    val maybeValue: Option[String] = field.value
    s"""<div>${ if (label.isEmpty) "" else s"<span class='selectorLabel'>$label</span>" }
       |  <select id="${ field.id }" name="${ field.name }" class="selector">
       |    ${ options.map { case (name, value) =>
                 s"""<option value='$value' ${
                     if (maybeValue.contains(value)) "selected='selected'" else ""
                   } class='selectorOption'>$name</option>"""
               }.mkString("\n")
            }
       |  </select>
       |</div>
       | """.stripMargin
  }

  def strikeThrough(str: String): String = s"<span style='text-decoration: line-through'>$str</span>"

  def strikeThroughIf(predicate: Boolean, str: String): String = if (predicate) strikeThrough(str) else str

  def textRow(label: String, value: String) =
    s"""<div class='textRow'><span class='textLabel'>$label</span> $value</div>"""

  def topRightButton(call: Call, label: String): String =
    s"""<div id="topRightButton"><a href="$call" class="btn btn-primary btn-small">$label</a></div>
       |""".stripMargin

  def trueFalseGlyph(value: Boolean, preFlight: Boolean=false): String =
    (if (!preFlight) "" else "<div class='trueFalseGlyphContainer preFlight'>") +
    s"<span class='giLower glyphicon glyphicon-${ if (value) "ok" else "remove" }'></span>" +
    (if (!preFlight) "" else "</div>")
}
