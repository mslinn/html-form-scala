/* The person who associated a work with this deed has dedicated the work to the public domain by waiving all of his or
 * her rights to the work worldwide under copyright law, including all related and neighboring rights, to the extent allowed by law.
 *
 * You can copy, modify, distribute and perform the work, even for commercial purposes, all without asking permission.
 *
 * In no way are the patent or trademark rights of any person affected by CC0, nor are the rights that other persons may
 * have in the work or in how the work is used, such as publicity or privacy rights.
 * Unless expressly stated otherwise, the person who associated a work with this deed makes no warranties about the work,
 * and disclaims liability for all uses of the work, to the fullest extent permitted by applicable law.
 * When using or citing the work, you should not imply endorsement by the author or the affirmer.
 *
 * The full legal text is here: https://creativecommons.org/publicdomain/zero/1.0/legalcode */

//resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")

// See https://github.com/orrsella/sbt-sublime
addSbtPlugin("com.orrsella" % "sbt-sublime" % "1.1.1")

// See https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.0")

// See https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.0")
