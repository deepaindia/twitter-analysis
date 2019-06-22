name := "TwitterAnalysis"

version := "0.1"

scalaVersion := "2.11.8"



libraryDependencies ++= {

  Seq(
    "org.apache.spark" %% "spark-core" % "2.4.3",
    "org.apache.spark" %% "spark-sql" % "2.4.3",
    "org.apache.spark" %% "spark-streaming" % "2.4.3",
    "org.apache.bahir" %% "spark-streaming-twitter" % "2.3.3",
    "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1",
    "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1" classifier "models",
    "org.elasticsearch" %% "elasticsearch-spark-20" % "7.1.1"
  )

  
}