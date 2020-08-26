package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import test.services.KafkaMessageProducer;

@RestController
@RequestMapping( "/send" )
public class SendController
{

  @Autowired
  private KafkaMessageProducer _producer;

  @PostMapping
  public void send( @RequestParam String text )
  {
    System.out.println( "Sending message: " + text );

    _producer.sendMessage( text );
  }

}
