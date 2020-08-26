package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import test.data.IncomingMessage;

@RestController
@RequestMapping( "/messages" )
public class MessagesController
{

  @Autowired
  private Flux<IncomingMessage> _flux;  

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
    return Flux.from( _flux )
    . map( message -> outputFor(message) )
    ;
  }

}
