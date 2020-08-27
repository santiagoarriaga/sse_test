package test.services;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;

import test.Config;
import test.data.IncomingMessage;

@Configuration
public class Services
{

  Map<String, Object> CONSUMER_PROPERTIES = Collections.unmodifiableMap
  (
    new HashMap<String, Object>()
    {{
      put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS );
      put( ConsumerConfig.CLIENT_ID_CONFIG        , "sample_receiver" );
    }}
  );

  Map<String, Object> PRODUCER_PROPERTIES = Collections.unmodifiableMap
  (
    new HashMap<String, Object>()
    {{
      put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS );
      put( ProducerConfig.CLIENT_ID_CONFIG        , "sample_sender"   );
    }}
  );

  private Flux<IncomingMessage> _liveFlux;

  @Bean
  public KafkaMessageProducer kafkaMessageProducer()
  {
    return new KafkaMessageProducer( PRODUCER_PROPERTIES, Config.TOPIC );
  }

  @Bean
  public KafkaMessageSource kafkaMessageSource()
  {
    return new KafkaMessageSource( CONSUMER_PROPERTIES, Config.TOPIC );
  }

  @Bean Flux<IncomingMessage> flow( @Autowired KafkaMessageSource source )  
  {
    Flux<IncomingMessage> shared = source.run().share();

    _liveFlux = Flux.from( shared );
    _liveFlux.subscribe();

    return shared;
  }

}
