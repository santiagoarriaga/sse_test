package test.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/send" )
public class SendController
{

  @PostMapping
  public void send( @RequestParam String text )
  {
    System.out.println( text );
  }

}
