package com.cspinetta.service

import cats.effect.IO
import com.cspinetta.utils.LogSupport
import org.http4s.Response
import org.http4s.dsl.io._

class HealthService extends LogSupport {

  def healthCheck: IO[Response[IO]] = {
    log.info(s"health-check invoked")
    Ok("App is running")
  }
}

object HealthService {

  def apply(): HealthService = new HealthService
}
