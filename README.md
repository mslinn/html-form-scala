# Yet More HTML Form Widgets for Play Framework

[![Build Status](https://travis-ci.org/mslinn/html-form-scala.svg?branch=master)](https://travis-ci.org/mslinn/html-form-scala)
[![GitHub version](https://badge.fury.io/gh/mslinn%2Fhtml-form-scala.svg)](https://badge.fury.io/gh/mslinn%2Fhtml-form-scala)

This is yet another approach to providing HTML form widget for Play Framework.
The form widgets provided with Play Framework feature a minimum of parameters and have an arcane syntax as a result.
Those widgets are rendered within a &lt;dl/&gt; tags, which is only useful for a narrow range of scenarios.

The [Play Bootstrap](https://github.com/adrianhurt/play-bootstrap) widgets require Twitter Bootstrap,
but otherwise its API is similar to the Play Framework form widget API.

This package produces output that more closely resembles Play Bootstrap's output, 
but it uses many explicit parameters instead of following the Play Framework API.
Widgets in the `views.html.htmlForm` package are HTML5 compatible.
Widgets related to Twitter Bootstrap 3.x are provided in the `views.html.htmlForm.bootstrap3` package.

## Installation
Add this to your project's `build.sbt`:

    resolvers += "micronautics/play on bintray" at "http://dl.bintray.com/micronautics/play"

    libraryDependencies += "com.micronautics" %% "html-form-scala" % "0.1.3" withSources()
