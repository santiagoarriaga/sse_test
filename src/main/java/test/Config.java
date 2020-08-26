package test;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;

public interface Config
{

  String BOOTSTRAP_SERVERS = "localhost:9092";
  String TOPIC = "thl-events";

  Map<String, Object> RECEIVER_PROPERTIES = Collections.unmodifiableMap(
    new HashMap<String, Object>()
    {{
      put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS );
      put( ConsumerConfig.CLIENT_ID_CONFIG, "sample_receiver" );
      put( ConsumerConfig.GROUP_ID_CONFIG, "sample_group" );
    }}
  );

}
