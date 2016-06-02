package gameobjects;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Player
{
  public static final Double WALKINGSPEED = 10.0;
  public Polygon polyArr[] = new Polygon[5];
  int layoutDistance = 40;
  boolean opening = true;
  public Boolean shiftPressed;
  public Boolean wDown = false;
  public Boolean aDown = false;
  public Boolean sDown = false;
  public Boolean dDown = false;
  public double rotateRight = 0;
  public double rotateLeft = 0;
  double rotateSpeed = 10;
  double xpos;
  double ypos;
  Double [] pentagonLayout;
  private int openingSpeed = 3;
  HappyGame game;
  int radius;
  
  Player(HappyGame game)
  {
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
    
    
    if(wDown){ypos-= WALKINGSPEED;}
    if(aDown){xpos-= WALKINGSPEED;}
    if(sDown){ypos+= WALKINGSPEED;}
    if(dDown){xpos+= WALKINGSPEED;}
    StackPane pane = game.getStackPane();
    if(wDown){pane.setTranslateY(pane.getTranslateY()+WALKINGSPEED);}
    if(aDown){pane.setTranslateX(pane.getTranslateX()+WALKINGSPEED);}
    if(sDown){pane.setTranslateY(pane.getTranslateY()-WALKINGSPEED);}
    if(dDown){pane.setTranslateX(pane.getTranslateX()-WALKINGSPEED);}
    
    
    
    
    if (rotateRight>0)
    {
      for(int i = 0; i < polyArr.length-1; i++)
      {
        rotateRight -= rotateSpeed;
        polyArr[i].setRotate(polyArr[i].getRotate()+rotateSpeed);
        polyArr[i].setTranslateX(Math.cos(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+xpos);
        polyArr[i].setTranslateY(Math.sin(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+ypos);
      }
    }
    else if (rotateLeft>0)
    {
      for(int i = 0; i < polyArr.length-1; i++)
      {
        rotateLeft -= rotateSpeed;
        polyArr[i].setRotate(polyArr[i].getRotate()-rotateSpeed);
        polyArr[i].setTranslateX(Math.cos(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+xpos);
        polyArr[i].setTranslateY(Math.sin(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+ypos);
      }
    }
    else
    {
      for(int i = 0; i < polyArr.length-1; i++)
      {
        polyArr[i].setTranslateX(Math.cos(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+xpos);
        polyArr[i].setTranslateY(Math.sin(polyArr[i].getRotate()*Math.PI/180-Math.PI*3/4)*layoutDistance+ypos);
      }
    }
    polyArr[4].setTranslateX(xpos);
    polyArr[4].setTranslateY(ypos);
  }
}
