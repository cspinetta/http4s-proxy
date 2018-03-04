package com.cspinetta.service

import cats.effect._
import com.cspinetta.conf.ConfigSupport
import com.cspinetta.utils.LogSupport
import org.http4s.Uri.Scheme
import org.http4s._
import org.http4s.client.{Client, DisposableResponse}

case class ProxyService(httpClient: Client[IO]) extends LogSupport with ConfigSupport {

  def forward(request: Request[IO]): IO[Response[IO]] = {
    val newUri = Uri(scheme = Some(Scheme.http),
      authority = Some(Uri.Authority(host = Uri.IPv4(config.proxy.destination), port = Some(config.proxy.port))),
      path = request.uri.path,
      query = request.uri.query,
      fragment = request.uri.fragment)

    val newRequest = request.withUri(newUri)

    val response: IO[Response[IO]] = httpClient.open
      .map {
        case DisposableResponse(underlyingResponse, dispose) =>
          underlyingResponse.copy(body = underlyingResponse.body.onFinalize(dispose))
      }.apply(newRequest)

    response
  }

}
