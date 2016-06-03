package gameobjects;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Player
{
  public static final int WALKINGSPEED = 5;
  public Polygon polyArr[] = new Polygon[5];
  int layoutDistance = 40;
  boolean opening = true;
  private int openingSpeed = 3;
  public int radius;

  public Boolean shiftPressed;
  public Boolean wDown = false;
  public Boolean aDown = false;
  public Boolean sDown = false;
  public Boolean dDown = false;
  short control = 0;
  public double rotateRight = 0;
  public double rotateLeft = 0;
  double rotateSpeed = 10;
  
  public int xpos = 0;
  public int ypos = 0;
  Double [] pentagonLayout;
  
  public HappyGame game;
  public boolean isClientPlayer;
  
  public Player(boolean isClientPlayer, HappyGame game)
  {
    this.isClientPlayer = isClientPlayer;
    this.game = game;
    Double unit = 20.0;
    radius = 20;
    
    GamePolygon face = new GamePolygon(12, 1, radius, 0, 0);
    polyArr[4] = face.getPolygon();
    polyArr[4].setFill(Color.GRAY);
    polyArr[4].setStroke(Color.BLACK);
    
    pentagonLayout = new Double[]
        {
        0.0,    0.0,
        unit,   0.0,
        unit,   unit/2,
        unit/2, unit,
        0.0,    unit
        };
      
    for(int i = 0; i < 4; i++)
    {
      Polygon polygon = new Polygon();
      polygon.getPoints().setAll(pentagonLayout);
      polygon.setRotate(i*90);
      polygon.setStroke(Color.BLACK);
      polyArr[i] = (polygon);
      
    }
    
    polyArr[0].setFill(Color.RED);
    polyArr[1].setFill(Color.GREEN);
    polyArr[2].setFill(Color.BLUE);
    polyArr[3].setFill(Color.YELLOW);
    
    polyArr[1].setTranslateX(layoutDistance);
    polyArr[2].setTranslateY(layoutDistance);
    polyArr[2].setTranslateX(layoutDistance);
    polyArr[3].setTranslateY(layoutDistance);
    
    
  }
  
  Polygon[] getPentagonList()
  {
    return polyArr;
  }

  public void tick()
  {
    if (opening && layoutDistance <= 40)
    {
      layoutDistance += openingSpeed ;
    }
    else if(!opening && layoutDistance > 21)
    {
      layoutDistance -=openingSpeed;
    }
    
    polyArr[1].setTranslateX(layoutDistance);
    
    if(isClientPlayer)
    {
      if(wDown){ypos-= WALKINGSPEED;}
      if(aDown){xpos-= WALKINGSPEED;}
      if(sDown){ypos+= WALKINGSPEED;}
      if(dDown){xpos+= WALKINGSPEED;}
      StackPane pane = game.getStackPane();
      if(wDown){pane.setTranslateY(pane.getTranslateY()+WALKINGSPEED);}
      if(aDown){pane.setTranslateX(pane.getTranslateX()+WALKINGSPEED);}
      if(sDown){pane.setTranslateY(pane.getTranslateY()-WALKINGSPEED);}
      if(dDown){pane.setTranslateX(pane.getTranslateX()-WALKINGSPEED);}
//      System.out.println(xpos+" "+ypos);
      if(wDown||aDown||sDown||dDown)
      {
//        System.out.println(getPosition());
      }
    }
    
    
    
    
    if (rotateRight>0)
    {
      for(int i = 0; i < polyArr.length-1; i++)
      {
        rotateRight -= rotateSpeed;
        polyArr[i].setRotate(polyArr[i].getRotate()+rotateSpeed);
        polyArr[i].setTranslateX(Math.cos(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+getXpos());
        polyArr[i].setTranslateY(Math.sin(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+ypos);
      }
    }
    else if (rotateLeft>0)
    {
      for(int i = 0; i < polyArr.length-1; i++)
      {
        rotateLeft -= rotateSpeed;
        polyArr[i].setRotate(polyArr[i].getRotate()-rotateSpeed);
        polyArr[i].setTranslateX(Math.cos(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+getXpos());
        polyArr[i].setTranslateY(Math.sin(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+ypos);
      }
    }
    else
    {
      for(int i = 0; i < polyArr.length-1; i++)
      {
        polyArr[i].setTranslateX(Math.cos(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+getXpos());
        polyArr[i].setTranslateY(Math.sin(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+ypos);
      }
    }
    polyArr[4].setTranslateX(getXpos());
    polyArr[4].setTranslateY(ypos);
  }
  
  public short getControls()
  {
      if(wDown){control ^= 0x80;}
      if(aDown){control ^= 0x40;}
      if(sDown){control ^= 0x20;}
      if(dDown){control ^= 0x10;}
      return control;
  }
  
  public String getPosition()
  {
    return getXpos()+" "+getYpos()+"\n";
  }
  
  public HappyGame getGame()
  {
    return game;
  }

  public void setGame(HappyGame game)
  {
    this.game = game;
  }

  public int getXpos()
  {
    return xpos;
  }

  public void setXpos(int xpos)
  {
    this.xpos = xpos;
  }
  
  public int getYpos()
  {
    return ypos;
  }
  
  public void setYpos(int ypos)
  {
    this.ypos = ypos;
  }
}
