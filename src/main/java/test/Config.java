package test;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

public interface Config
{

  String BOOTSTRAP_SERVERS = "localhost:9092";
  String TOPIC = "thl-events";

  Map<String, Object> CONSUMER_PROPERTIES = Collections.unmodifiableMap
  (
    new HashMap<String, Object>()
    {{
      put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS );
      put( ConsumerConfig.CLIENT_ID_CONFIG        , "sample_receiver" );
    }}
  );

  Map<String, Object> PRODUCER_PROPERTIES = Collections.unmodifiableMap
  (
    new HashMap<String, Object>()
    {{
      put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS );
      put( ProducerConfig.CLIENT_ID_CONFIG        , "sample_sender"   );
    }}
  );

}
