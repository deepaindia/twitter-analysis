
package com.twitter.stream

import org.apache.spark.{SparkConf}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.Seconds
import java.time.format.DateTimeFormatter
import com.cybozu.labs.langdetect.DetectorFactory
import com.twitter.stream.util.LogUtils
import com.twitter.stream.util.SentimentAnalysisUtils
import scala.util.Try
import org.elasticsearch.spark._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._






  object analytics {


    def main(args: Array[String]): Unit = {

      if (args.length < 4) {
        System.err.println("Usage: TwitterPopularTags <consumer key> <consumer secret> " + "<access token> <access token secret> [<filters>]")
        System.exit(1)
      }

      LogUtils.setStreamingLogLevels()
      DetectorFactory.loadProfile("src/main/resources/profiles")


      // API key:    ywnrz5aGZ4TbL14fiGFZB2K9O

      //API secret key: MDHFakuRRw4j9lXhApQQ5c8Eryp1zzPQ157xGVO0nk748UyzMB

      //Access Token: 887145308-Yg3Lkl157n7vr5jzicQPo8aLCcJ5BefixgPtFnRX

      //Access Token Secret:   zMLEkvmzf09jOuOvPZYcns46FVKe5wjNRvUYlfFk3ABZA


      val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
      val filters = args.takeRight(args.length - 4)

      // Set the system properties so that Twitter4j library used by twitter stream
      // Use them to generate OAuth credentials
      System.setProperty("twitter4j.oauth.consumerKey", "GWT4av6gSsQqqqtCnNfhQw6Wm")
      System.setProperty("twitter4j.oauth.consumerSecret", "Nncna7TBqmyINuKAjbGOh5Y1637wwj1VcIWiSUmpyUiUbKJQjZ")
      System.setProperty("twitter4j.oauth.accessToken", "887145308-BqFurnbkue3NGzEwUUmAEokxgdF4NWGsqEuzHiem")
      System.setProperty("twitter4j.oauth.accessTokenSecret", "FZYvC42i6zQ3DdoGFwcrbdXKXI1CiUZr15yw7S9oQbJTe")

      val sparkConf = new SparkConf().setAppName("twitterSentiment").setMaster("local[2]")
      sparkConf.set("es.nodes", "localhost")
      sparkConf.set("es.port", "9200")
      sparkConf.set("spark.es.nodes.discovery","false")
      sparkConf.set("spark.es.nodes.wan.only","true")
      sparkConf.set("spark.es.index.auto.create","true")


      val ssc = new StreamingContext(sparkConf, Seconds(5))
      val stream = TwitterUtils.createStream(ssc, None,filters)
      stream.print()

     stream.foreachRDD { (rdd, time) =>
        rdd.map(t => {
          Map(
            "user" -> t.getUser.getScreenName,
            "created_at" -> t.getCreatedAt.toInstant.toString,
            "location" -> Option(t.getGeoLocation).map(geo => {
              s"${geo.getLatitude},${geo.getLongitude}"
            }),
            "text" -> t.getText,
            "hashtags" -> t.getHashtagEntities.map(_.getText),
            "retweet" -> t.getRetweetCount,
            "language" -> detectLanguage(t.getText),
            "sentiment" -> com.twitter.stream.util.SentimentAnalysisUtils.detectSentiment(t.getText).toString
          )
        }).saveToEs("twitter/tweet")
      }

      ssc.start()
      ssc.awaitTermination()


    }


    def detectLanguage(text: String) : String = {

      Try {
        val detector = DetectorFactory.create()
        detector.append(text)
        detector.detect()
      }.getOrElse("unknown")

    }
  }




