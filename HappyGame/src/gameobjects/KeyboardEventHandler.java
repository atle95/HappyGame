package gameobjects;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardEventHandler implements EventHandler<KeyEvent>
{
  private Player player;
  private HappyGame happyGame;
  
  public KeyboardEventHandler(Player player)
  {
    this.player = player;
  }
  
  @Override
  public void handle(KeyEvent event)
  {
    if (event.getEventType() == KeyEvent.KEY_PRESSED)
    {
//      if(event.isShiftDown()) 
//      {
//        player.shiftPressed = true;
//      }
      if(event.getCode()==KeyCode.W) 
      {
        player.wDown = true;
//        player.control ^= 0x80; 
      }
      if(event.getCode()==KeyCode.A) 
      {
        player.aDown = true;
//        player.control ^= 0x40;
      }
      if(event.getCode()==KeyCode.S) 
      {
        player.sDown = true;
//        player.control ^= 0x20; 
      }
      if(event.getCode()==KeyCode.D) 
      {
        player.dDown = true;
//        player.control ^= 0x10; 
      }
      if(event.getCode()==KeyCode.E) 
      {
        player.rotateRight += 360;
//        player.control ^= 0x08; 
      }
      if(event.getCode()==KeyCode.Q) 
      {
        player.rotateLeft += 360;
//        player.control ^= 0x04; 
      }
      if(event.getCode()==KeyCode.SPACE) 
      {
        player.opening = false;
//        player.control ^= 0x02; 
      }
      if(event.getCode()==KeyCode.ESCAPE)
      {
        happyGame.paused = !happyGame.paused;
//        player.control ^= 0x01; 
      }
//      if (player.control != 0)
//      {
//        System.out.println(""+player.control);
//      }
    }
    else if (event.getEventType() == KeyEvent.KEY_RELEASED)
    {
//      System.out.println(""+player.control);
      if(event.getCode()==KeyCode.W)
      {
        player.wDown = false;
//        player.control &= 0x7f; 
      }
      if(event.getCode()==KeyCode.A)
      {
        player.aDown=false;
//        player.control &= 0xbf; 
      }
      if(event.getCode()==KeyCode.S)
      {
        player.sDown = false;
//        player.control &= 0xdf; 
      }
      if(event.getCode()==KeyCode.D)
      {
        player.dDown=false;
//        player.control &= 0xef; 
      }
      if(event.getCode()==KeyCode.Q) 
      {
//        player.rotateLeft = false;
//        player.control &= 0xf7; 
      }
      if(event.getCode()==KeyCode.E) 
      {
//        player.rotateRight = false;
//        player.control &= 0xfb; 
      }
      if(event.getCode()==KeyCode.SPACE) 
      {
        player.opening = true;
//        player.control &= 0xfd; 
      }
      if(event.getCode()==KeyCode.ESCAPE)
      {
        happyGame.paused = !happyGame.paused;
//        player.control &= 0xfe; 
      }
//      if(event.getCode()==KeyCode.SHIFT)
//      {
//        player.shiftPressed=false;
//      }
    }
  }
}