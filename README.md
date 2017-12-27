# Bootstrap 3 HTML Form Widgets for Play Framework

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.org/mslinn/html-form-scala.svg?branch=master)](https://travis-ci.org/mslinn/html-form-scala)
[![GitHub version](https://badge.fury.io/gh/mslinn%2Fhtml-form-scala.svg)](https://badge.fury.io/gh/mslinn%2Fhtml-form-scala)

This project provides Bootstrap 3 compatible HTML form widgets for Play Framework.
The form widgets provided with Play Framework feature a minimum of parameters and have an arcane syntax as a result.
Those widgets are rendered within a &lt;dl/&gt; tags, which is only useful for a narrow range of scenarios.
Most HTML5 features are not supported "out of the box" so it is common to spend a lot of time crafting wrappers around the standard widgets.

The [Play Bootstrap](https://github.com/adrianhurt/play-bootstrap) widgets require Twitter Bootstrap,
but otherwise its API is similar to the Play Framework form widget API.

[ScalaTags](http://www.lihaoyi.com/scalatags/) is another library for generating HTML using Scala.
It is not Play Framework aware, or Twitter Bootstrap aware &ndash; instead, it is completely generic.

This package produces output that closely resembles Play Bootstrap's output,
but it uses many explicit parameters instead of following the Play Framework API.
Widgets in the `views.html.htmlForm` package are HTML5 compatible,
and include a CSRF form helper.

Widgets related to Twitter Bootstrap 3.x are provided in the `views.html.htmlForm.bootstrap3` package.
Two flavors of modal dialog are provided, smart tabs and a date picker.
The `HtmlForm` object contains `checkbox`es, `select`s, and various flavors of `input` for
email, URLs, currency, passwords, percentages, range-limited numeric values and much more.

## Installation
Add this to your project's `build.sbt`:

    resolvers += "micronautics/play on bintray" at "http://dl.bintray.com/micronautics/play"

    libraryDependencies += "com.micronautics" %% "html-form-scala" % "0.2.1" withSources()

## Scaladoc
[Here](http://blog.mslinn.com/html-form-scala/latest/api/index.html#views.html.htmlForm.package)

## Working with html-form-scala
This project requires an implementation of `model.persistence.Id`, defined in
[has-id](https://github.com/mslinn/has-id) for testing purposes.
Other projects that incorporate this project as a dependency must define their own implementation
of `model.persistence.Id` (for persistence, for example).
