package test.services;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import reactor.core.publisher.Flux;
import reactor.kafka.sender.*;

public class KafkaMessageProducer
{

  private final KafkaSender<String, String> _sender;
  private final String _topic;

  public KafkaMessageProducer
    ( Map<String, Object> basicProperties, String topic )
  {
    var properties = new HashMap<>( basicProperties );

    properties.put( ACKS_CONFIG                  , "all"                  );
    properties.put( KEY_SERIALIZER_CLASS_CONFIG  , StringSerializer.class );
    properties.put( VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class );

    var options = SenderOptions.<String, String>create( properties );

    _sender = KafkaSender.create( options );
    _topic = topic;
  }

  public void sendMessage( String text )
  {
    var correlation = System.currentTimeMillis();
    var record =
      SenderRecord.create( _topic, null, null, (String)null, text, correlation );

    var flux = Flux.just(record )
    .doOnError( e -> e.printStackTrace() );

    _sender.send( flux )
    .subscribe( message ->
    {
      var offset = message.recordMetadata().offset();

      System.out.printf("Message %d sent successfully\n", offset );
    });
  }

  public void close()
    { _sender.close(); }

}
