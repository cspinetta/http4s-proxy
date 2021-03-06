package com.cspinetta.utils

import org.slf4j.{Logger, LoggerFactory}

trait LogSupport {
  val log: Logger = LoggerFactory.getLogger(this.getClass)
}
