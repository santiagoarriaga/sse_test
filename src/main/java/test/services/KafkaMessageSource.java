package test.services;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

import java.util.*;

import org.apache.kafka.common.serialization.StringDeserializer;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import test.data.IncomingMessage;

public class KafkaMessageSource
{

  private final ReceiverOptions<String, String> _options;
  private final Collection<String> _topics;

  public KafkaMessageSource(Map<String, Object> basicProperties, String topic)
  {
    var properties = new HashMap<>(basicProperties);

    properties.put( GROUP_ID_CONFIG                , "sample_group"           );
    properties.put( KEY_DESERIALIZER_CLASS_CONFIG  , StringDeserializer.class );
    properties.put( VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
    properties.put( AUTO_OFFSET_RESET_CONFIG       , "earliest"               );

    _options = ReceiverOptions.create(properties);
    _topics = Collections.singleton(topic);
    ;
  }

  public Flux<IncomingMessage> run()
  {
    var options = _options.subscription(_topics);

    return KafkaReceiver.create(options).receive()
    . map( record ->
      new IncomingMessage
      (
        record.receiverOffset().offset(),
        record.key(),
        record.value()
      )
    );
  }

}
