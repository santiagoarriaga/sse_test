package test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;

import test.Config;
import test.data.IncomingMessage;

@Configuration
public class Services
{

  private Flux<IncomingMessage> _liveFlux;

  @Bean
  public KafkaMessageProducer kafkaMessageProducer()
  {
    return new KafkaMessageProducer( Config.PRODUCER_PROPERTIES, Config.TOPIC );
  }

  @Bean
  public KafkaMessageSource kafkaMessageSource()
  {
    return new KafkaMessageSource( Config.CONSUMER_PROPERTIES, Config.TOPIC );
  }

  @Bean Flux<IncomingMessage> flow( @Autowired KafkaMessageSource source )  
  {
    Flux<IncomingMessage> shared = source.run().share();

    _liveFlux = Flux.from( shared );
    _liveFlux.subscribe();

    return shared;
  }

}
