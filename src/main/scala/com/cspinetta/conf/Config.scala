package com.cspinetta.conf

import com.cspinetta.utils.ConfigurationSupport

import scala.concurrent.duration.FiniteDuration

trait ConfigSupport {
  val config: Config.type = Config
}

object Config extends ConfigurationSupport {

  import model._

  lazy val server: Server = pureconfig.loadConfigOrThrow[Server](config.getConfig("server"))
  lazy val client: Client = pureconfig.loadConfigOrThrow[Client](config.getConfig("client"))
  lazy val proxy: Proxy = pureconfig.loadConfigOrThrow[Proxy](config.getConfig("proxy"))


  object model {
    case class Server(port: Int,
                      host: String,
                      threads: Int)

    case class Client(maxTotalConnections: Int,
                      idleTimeout: FiniteDuration,
                      requestTimeout: FiniteDuration)

    case class Proxy(destination: String,
                     port: Int)
  }
}


