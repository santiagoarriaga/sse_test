package test.controllers;

import org.springframework.web.bind.annotation.*;

import test.Config;
import test.services.KafkaMessageProducer;

@RestController
@RequestMapping( "/send" )
public class SendController
{

  private final KafkaMessageProducer _producer =
    new KafkaMessageProducer( Config.PRODUCER_PROPERTIES, Config.TOPIC );

  @PostMapping
  public void send( @RequestParam String text ) throws InterruptedException
  {
    System.out.println( "Sending message: " + text );

    _producer.sendMessage( text );
  }

}
