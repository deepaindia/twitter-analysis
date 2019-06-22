package com.twitter.stream.util

import org.apache.log4j.{Level, Logger}

object LogUtils {

  /** Set reasonable logging levels for streaming if the user has not configured log4j. */
  def setStreamingLogLevels() {

    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      // We first log something to initialize Spark's default logging, then we override the
      // logging level.

      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }

}
