# twitter-analysis
Realtime Sentiment Analysis on Twitter based on Stanford NLP


INSTRUCTIONS TO RUN THE CODE:

TWITTER:
        1. Apply Twitter Developer Account by Registering the following link:

        https://developer.twitter.com/en/application/use-case
        
        2. Wait for Review & Approval
        
        3. Create the Twitter App using the following link
        
        https://developer.twitter.com/en/apps
        
        4. Generate Twitter Api Client Credentials for the following keys
        
          <consumer_key> <consumer_secret> <access_token> <access_token_secret>
          
          Url: https://developer.twitter.com/en/apps/12[*****]


SOFTWARE DOWNLOADS: 

SPARK 2.4.3 DOWNLOAD :

      https://www.apache.org/dyn/closer.lua/spark/spark-2.4.3/spark-2.4.3-bin-hadoop2.7.tgz

ELASTIC SEARCH DOWNLOAD:

     https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.1.1-linux-x86_64.tar.gz

KIBANA DOWNLOAD:

    https://artifacts.elastic.co/downloads/kibana/kibana-7.1.1-linux-x86_64.tar.gz

Elastic Search Head Download:

    git clone git://github.com/mobz/elasticsearch-head.git
    cd elasticsearch-head
    npm install
    npm run start
    open http://localhost:9100/
    
IntelliJ IDE Download - Community Edition

    https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC
    

Run Elastic Search:

    >elastic7.1/bin/elasticsearch -d
    
Run Elastic Search:

    >kibana.7.1/bin/kibana -d

Run the Spark Code:

You Can USe the Intelliji IDE to run the code:

        By Running com.twitter.analytics.strem file

You Can subit the Bundled/Compiled code using Spark Submit:
 
        ../spark-2.4.3/bin/spark-submit --class com.twitter.analytics.stream --master local[2] target/scala-2.10/com.twitter-analytics stream-assembly-0.1-SNAPSHOT.jar <consumer_key> <consumer_secret> <access_token> <access_token_secret>


