import java.security.cert.X509Certificate
import play.api.mvc.{Headers, RequestHeader}

/** Does not require an Application to be in scope, or even a routing table */
case class ReallyFakeRequest(method: String, uri: String) extends RequestHeader {
  val url = new java.net.URL(uri)

  override def id: Long = 0L

  override def tags: Map[String, String] = Map.empty

  override def path: String = url.getPath + {
    val ref = url.getRef
    if (ref.nonEmpty) s"#$ref" else ""
  }

  override def version: String = ???

  override def queryString: Map[String, Seq[String]] = {
    val x = for {
      nvPair <- url.getQuery.split("&").toSeq
    } yield {
      val Array(name, value) = nvPair.split("=")
      (name, Seq(value))
    }
    x.toMap
  }

  override def headers: Headers = ???

  override def remoteAddress: String = ???

  override def secure: Boolean = ???

  override def clientCertificateChain: Option[Seq[X509Certificate]] = ???
}
