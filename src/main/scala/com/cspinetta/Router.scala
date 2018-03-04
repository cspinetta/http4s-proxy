package com.cspinetta

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._


object Router {

  def service(s: Services): HttpService[IO] = {

    HttpService[IO] {
      case GET -> Root => s.health.healthCheck
      case request => s.proxy.forward(request)
    }
  }

}
