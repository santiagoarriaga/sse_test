package test;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping( "/messages" )
public class MessagesController
{

  private ServerSentEvent<String> messageFor( long sequence )
  {
    return ServerSentEvent.<String>builder()
    . id( String.valueOf(sequence) )
    . event ( "message" )
    . data( "SSE - " + LocalTime.now() )
    . build()
    ;
  }

  @GetMapping
  public Flux<ServerSentEvent<String>> messages()
  {
    return Flux.interval( Duration.ofSeconds(1) )
    . map( sequence -> messageFor(sequence) )
    ;
  }

}
