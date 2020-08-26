package test.controllers;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;

import test.Config;
import test.data.IncomingMessage;
import test.services.KafkaMessageSource;

@RestController
@RequestMapping( "/messages" )
public class MessagesController
{

  private ServerSentEvent<String> outputFor( IncomingMessage message )
  {
    return ServerSentEvent.<String>builder()
    . id( String.valueOf(message.offset) )
    . event ( "message" )
    . data( "SSE - " + message.value )
    . build()
    ;
  }

  @GetMapping
  public Flux<ServerSentEvent<String>> messages()
  {
    var source =
      new KafkaMessageSource( Config.CONSUMER_PROPERTIES, Config.TOPIC );

    return source.run()
    . map( message -> outputFor(message) )
    ;
  }

}