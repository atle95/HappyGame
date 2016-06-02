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
      if(event.isShiftDown()) 
      {
        player.shiftPressed = true;
      }
      if(event.getCode()==KeyCode.W) 
      {
        player.wDown = true;
      }
      if(event.getCode()==KeyCode.S) 
      {
        player.sDown = true;
      }
      if(event.getCode()==KeyCode.A) 
      {
        player.aDown = true;
      }
      if(event.getCode()==KeyCode.D) 
      {
        player.dDown = true;
      }
      if(event.getCode()==KeyCode.E) 
      {
        player.rotateRight += 360;
      }
      if(event.getCode()==KeyCode.Q) 
      {
        player.rotateLeft += 360;
      }
      if(event.getCode()==KeyCode.SPACE) 
      {
        player.opening = false;
      }
      if(event.getCode()==KeyCode.ESCAPE)
      {
        happyGame.paused = !happyGame.paused;
      }
    }
    else if (event.getEventType() == KeyEvent.KEY_RELEASED)
    {
      if(event.getCode()==KeyCode.W)
      {
        player.wDown = false;
      }
      if(event.getCode()==KeyCode.S)
      {
        player.sDown = false;
      }
      if(event.getCode()==KeyCode.A)
      {
        player.aDown=false;
      }
      if(event.getCode()==KeyCode.D)
      {
        player.dDown=false;
      }
      if(event.getCode()==KeyCode.E) 
      {
//        player.rotateRight = false;
      }
      if(event.getCode()==KeyCode.Q) 
      {
//        player.rotateLeft = false;
      }
      if(event.getCode()==KeyCode.SPACE) 
      {
        player.opening = true;
      }
      if(event.getCode()==KeyCode.ESCAPE)
      {
        happyGame.paused = !happyGame.paused;
      }
      if(event.getCode()==KeyCode.SHIFT)
      {
        player.shiftPressed=false;
      }
    }
  }
}