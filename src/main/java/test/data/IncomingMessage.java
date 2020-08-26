package test.data;

public class IncomingMessage
{

  public final long offset;
  public final String key;
  public final String value;

  public IncomingMessage( long offset, String key, String value )
  {
    this.offset = offset;
    this.key = key;
    this.value = value;
  }
  
}
