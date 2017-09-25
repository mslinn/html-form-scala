import java.security.cert.X509Certificate
import play.api.mvc.{Headers, RequestHeader}

/** Does not require an Application to be in scope, or even a routing table */
case class ReallyFakeRequest(method: String, uri: String) extends RequestHeader {
  val url = new java.net.URL(uri)

  val path: String = url.getPath + {
    val ref = url.getRef
    if (ref.nonEmpty) s"#$ref" else ""
  }

  val queryString: Map[String, Seq[String]] = {
    val x = for {
      query  <- Option(url.getQuery).toSeq
      nvPair <- query.split("&").toSeq
    } yield {
      val Array(name, value) = nvPair.split("=")
      (name, Seq(value))
    }
    x.toMap
  }

  val id: Long = 0L

  val tags: Map[String, String] = Map.empty

  val version: String = "1.0"

  val headers: Headers = Headers()

  val remoteAddress: String = "http://localhost"

  val secure: Boolean = false

  val clientCertificateChain: Option[Seq[X509Certificate]] = None
}
