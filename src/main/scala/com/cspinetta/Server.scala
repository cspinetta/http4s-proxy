package com.cspinetta

import java.util.concurrent.{ExecutorService, Executors}

import cats.effect._
import com.cspinetta.conf.{Config, ConfigSupport}
import com.cspinetta.service.{HealthService, ProxyService}
import com.cspinetta.utils.ThreadUtils._
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import org.http4s.client.Client
import org.http4s.client.blaze.{BlazeClientConfig, Http1Client}
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext

object Server extends StreamApp[IO] {

  private val executor : ExecutorService  = Executors.newFixedThreadPool(Config.server.threads, namedThreadFactory("app-server-pool"))
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(executor)

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {

    BlazeBuilder[IO]
      .bindHttp(Config.server.port, Config.server.host)
      .mountService(Router.service(new Services))
      .serve
  }
}

class Services {

  val health = HealthService()
  val proxy = ProxyService(ClientProvider.httpClient)
}

object ClientProvider extends ConfigSupport {

  private val clientConfig = BlazeClientConfig.defaultConfig.copy(
    maxTotalConnections = config.client.maxTotalConnections,
    idleTimeout = config.client.idleTimeout,
    requestTimeout = config.client.requestTimeout
  )

  val httpClient: Client[IO] = Http1Client[IO](clientConfig).unsafeRunSync()
}
