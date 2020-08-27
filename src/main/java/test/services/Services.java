package test.services;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;

import test.data.IncomingMessage;

@Configuration
public class Services
{

  private Flux<IncomingMessage> _liveFlux;

  @Value( "${APP_BOOTSTRAP_SERVERS}" )
  private String bootstrapServers;

  @Value( "${APP_TOPIC}" )
  private String topic;

  @Bean
  public KafkaMessageProducer kafkaMessageProducer()
  {
    var properties = Collections.unmodifiableMap
    (
      new HashMap<String, Object>()
      {{
        put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers );
        put( ProducerConfig.CLIENT_ID_CONFIG        , "sample_sender"  );
      }}
    );

    return new KafkaMessageProducer( properties, topic );
  }

  @Bean
  public KafkaMessageSource kafkaMessageSource()
  {
    var properties = Collections.unmodifiableMap
    (
      new HashMap<String, Object>()
      {{
        put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers  );
        put( ConsumerConfig.CLIENT_ID_CONFIG        , "sample_receiver" );
      }}
    );

    return new KafkaMessageSource( properties, topic );
  }

  @Bean Flux<IncomingMessage> flow( @Autowired KafkaMessageSource source )  
  {
    Flux<IncomingMessage> shared = source.run().share();

    _liveFlux = Flux.from( shared );
    _liveFlux.subscribe();

    return shared;
  }

}
