package views.html.htmlForm.bootstrap3

import currency.Currency
import play.api.data.{Field, Form}
import play.api.mvc.Call
import scala.language.implicitConversions

protected case class FadeValue(value: Boolean) extends AnyVal

/** @see http://stackoverflow.com/a/3508555/553865 */
protected sealed trait StringOrSeq[-T]

protected object StringOrSeq {
  implicit object SeqWitness extends StringOrSeq[Seq[String]]
  implicit object StringWitness extends StringOrSeq[String]
}

/** These methods generate HTML as a `String`. The following CSS classes are used:
  *
  * `btn` - Used by Twitter bootstrap; see [[http://getbootstrap.com/css/#buttons]]
  *
  * `checked`   - Applied to the enclosing &lt;div&gt; around a checkbox.
  *
  * `form-control` - Used by Twitter bootstrap; see [[http://getbootstrap.com/css/#forms-example]]
  *
  * `input-group` - Used by Twitter bootstrap; see [[http://getbootstrap.com/css/#forms-example]]
  *
  * `input-group-addon` - Used by Twitter bootstrap; see [[http://getbootstrap.com/css/#forms-inline]]
  *
  * `input-group-addon-prefix` - Used by Twitter bootstrap; see [[http://codepen.io/swhdesigns/pen/bNwVgG]]
  *
  * `bigCheckbox` - Applied to a multivalue checkbox (multiCheckbox)
  *
  * `mediumCheckbox` - Applied to a checkbox.
  *
  * `panel` - Used by Twitter bootstrap; see [[http://getbootstrap.com/components/#panels]]
  *
  * `panel-default` - Used by Twitter bootstrap; see [[http://getbootstrap.com/components/#panels]]
  *
  * `col-md-N` - Used by Twitter bootstrap; see [[http://getbootstrap.com/css/#grid]]
  *
  * `preFlight` - Warns that an associated widget is in a non-ready state. */
object HtmlForm {
  /** @return the value of [[Field]] */
  def value(fieldName: String)(implicit form: Form[_]): String = form(fieldName).value.mkString(",")

  /** @return an HTML checkbox with CSS class `mediumCheckbox`,
    *         enclosed within a &lt;div&gt; with id `${ fieldName }_container` and the CSS class `checked`.
    *         If it is more convenient to pass the the [[Field]] instead of the name of the field, consider using the `checked` method.
    *         If the `checked` status needs to be independently set, consider using the `checkedFromValue` method.
    * @param fieldName if the [[Field]] with the name `fieldName` has the value `true`, `on` or `enabled`, then set the checkbox's `checked` attribute.
    * @param classes Add this value of this parameter to the enclosing div's CSS classes.
    * @param label if non-empty, the label follows the checkbox */
  def checkedFromName(fieldName: String, label: String="", classes: String="")
                     (implicit form: Form[_]): String = {
    val ckd = if (List("true", "on", "enabled").contains(value(fieldName))) "checked='checked'" else ""
    val fieldId = fieldName.replace('.', '_').replace('[', '_').replace("]", "")
    s"""<div class="checked $classes" id="${ fieldId }_container">
       |  <input type="checkbox" name="$fieldName" id="$fieldId" value="true" $ckd class="mediumCheckbox">
       |  ${ if (label.trim.isEmpty) "" else s"$label" }
       |</div>
       |""".stripMargin
  }

  /** @return an HTML checkbox with CSS class `mediumCheckbox`,
    *         enclosed within a &lt;div&gt; with id `${ fieldName }_container` and the CSS class `checked`.
    *         If the `checked` status needs to be set from the value of the [[Field]], consider using the `checkedFromName` method.
    * @param fieldName if the [[Field]] with the name `fieldName` has the value `true` then set the checkbox's `checked` attribute.
    * @param classes Add this value of this parameter to the enclosing div's CSS classes.
    * @param label if non-empty, the label follows the checkbox */
  def checkedFromValue(fieldName: String, value: Boolean, label: String="", classes: String=""): String = {
    val ckd = if (value) "checked='checked'" else ""
    val fieldId = fieldName.replace('.', '_').replace('[', '_').replace("]", "")
    s"""<div class="checked $classes" id="${ fieldId }_container">
       |  <input type="checkbox" name="$fieldName" id="$fieldId" value="true" $ckd class="mediumCheckbox">
       |  ${ if (label.trim.isEmpty) "" else s"$label" }
       |</div>
       |""".stripMargin
  }

  /** In the follwing usage example `_form` is a [[Form]] instance.
    * {{{checked(_form("isComplete"), label="Disable")}}}
    * @return an HTML checkbox with CSS class `mediumCheckbox`,
    *         enclosed within a &lt;div&gt; with id `${ fieldName }_container` and the CSS class `checked`.
    *         If it is more convenient to pass the name of the field instead of the [[Field]] itself, consider using the `checkedFromName` method.
    *         If the `checked` status needs to be independently set, consider using the `checkedFromValue` method.
    * @param field if the given [[Field]] has the value `true`, `on` or `enabled`, then set the checkbox's `checked` attribute.
    * @param classes Add this value of this parameter to the enclosing div's CSS classes.
    * @param label if non-empty, the label follows the checkbox */
  def checked(field: Field, label: String="", classes: String="")
             (implicit form: Form[_]): String = {
    val ckd = if (List("true", "on", "enabled").contains(value(field.name))) "checked='checked'" else ""
    s"""<div class="checked $classes" id="${ field.id }_container">
       |  <input type="checkbox" name="${ field.name }" id="${ field.id }" value="true" $ckd class="mediumCheckbox">
       |  ${ if (label.trim.isEmpty) "" else s"$label" }
       |</div>
       |""".stripMargin
  }

  /** Generates an &gt;input&lt; tag.
    * The following usage examples assume `_form` is a [[Form]] instance:
    * {{{ Seq(
    *   inputter(_form("userId"), label=s"User ID", asCode=true),
    *   inputter(_form("resellerCode"), label="Reseller code"),
    *   inputter(_form("id"), isHidden=true),
    *   inputter(_form("customerAddress.name"), label="Name", maybePlaceholder=Some("Name"))
    * ).mkString("\n") }}}
    * @param field [[play.api.data.Form]] [[Field]] that supplies values for this widget instance, and is updated by submitting the HTML form containing this widget.
    * @param label Rendered within a &lt;label/&gt; tag associated with the generated &gt;input/&lt; tag.
    * @param asCode causes the displayed value to be rendered with a monospaced font.
    * @param containerClasses CSS classes applied to the containing &lt;div/&gt;.
    * @param helpText rendered to a `title` attribute so it appears when hovering the cursor over the widget.
    * @param isCurrency the value is rendered with a monospaced font and with a prefix "cap" which contains a dollar sign.
    * @param asButton renders a &lt;button&gt; tag.
    * @param asCents divides the value by 100 and uses two decimal places; implies `isCurrency`.
    * @param isEmail renders an HTML5 `type='email'` attribute; browsers validate the value before allowing the form to be submitted.
    * @param isHidden renders an `type='hidden'` attribute so the widget does not appear, yet it contains an immutable value.
    * @param isNumeric renders an HTML5 `type='number' step='any'` attribute. Various browsers might render this type of widget differently.
    * @param isPassword renders an `type='password'` attribute so the value typed by the user into the widget is masked.
    * @param isPercentage the value is rendered with a monospaced font and with a suffix "cap" which contains a percent sign.
    * @param isUrl renders an `type='url'` attribute; browsers validate the format but not this value before allowing the form to be submitted.
    * @param labelClasses CSS class applied to label preceding the &lt;input&gt; tag.
    * @param maybeMin If `Some(value)` is provided, the value is rendered as an `min='value'` attribute.
    *                 Should be used with a numeric value.
    * @param maybeReady Displays messages if the tuple sequence does not contain a true value.
    * @param optionValues if nonEmpty generate a &lt;select&gt; with the specified &lt;option&gt; name/value pairs.
    * @param maybePlaceholder If `Some(value)` provided, generate an HTML5 `placeholder='value'` attribute.
    * @param selectedValues if `optionValues` is nonEmpty use these values to add select attribute to &lt;option&gt;s.
    * @param isRequired Generate an `required` attribute if `true`.
    * @param style CSS style applied to input tag.
    * @param suffixValue Generate an end "cap" with the provided value. */
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

  /** @return If `ok`, merely return the given `msg`, else return a &lt;span&gt; tag decorated with `preFlight` class and containing the given `msg`. */
  def preFlightWarning(msg: String, ok: Boolean): String = if (ok) msg else s"""<span class="preFlight">$msg</span>"""

  /** Text message that can be presented in a variety of ways. */
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

  protected def expandedContent[T: StringOrSeq](content: T): String = content match {
    case string: String   => string
    case seq: Seq[_] => seq.mkString("\n")
  }

  /** Displays as a table with a heading; the height of the box depends on its contents.
    * See http://getbootstrap.com/components/#panels
    *
    * In the following usage example, `_form` is a [[Form]] instance.
    * {{{panelTable("Financial Details", id="financialDetails") {
    *     Seq(
    *       tableRow("Currency", inputter(_form("paymentDetail.mcCurrency"), maybePlaceholder=Some("USD"))),
    *       tableRow("Currency", inputter(_form("paymentDetail.mcCurrency"), maybePlaceholder=Some("USD"))),
    *       tableRow("Fee",      inputter(_form("paymentDetail.mcFee"),      maybePlaceholder=Some("(in USD)"), isCurrency=true)),
    *       tableRow("Gross",    inputter(_form("paymentDetail.mcGross"),    maybePlaceholder=Some("(in USD)"), isCurrency=true)),
    *       tableRow("Tax",      inputter(_form("paymentDetail.tax"),        maybePlaceholder=Some("(in USD)"), isCurrency=true))
    *     )}}}}
    * @param title Heading for the panel
    * @param id If non-empty, provides the HTML id for the entire panel
    * @param columns number of Twitter columns the panel uses; sets the width of the panel
    * @param content `tableRow`s concatenated together, for example:
    *                {{{Seq(tableRow("abc"), tableRow("def")).mkString("\n")}}} */
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

  /** In the following usage example, RoleEnum is a Java enum, and `_form` is a [[Form]] instance.
    * {{{selector(_form("roleEnum"),  label="Role type", options=RoleEnum.values.toSeq.map(v => (v.name, v.name)))}}} */
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
