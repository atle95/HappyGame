package gameobjects;

import javafx.scene.shape.Polygon;

public class GamePolygon
{
  Polygon polygon;
  double angle = Math.PI;
  double velocity = 1;
  double distance = 20;
  int positionX = -60;
  int positionY = -60;
  int radius = 20;
  int star = 4;
  double randomIncrement = 0.1;
  
  GamePolygon(int input, int star, int radius, double velocity, double angle)
  {
    this.angle = angle;
    this.velocity = 2*velocity;
    this.radius = radius;
    this.star = star;
    int numPoints = input*2;
    Double [] heptagonLayout = new Double[numPoints];
    polygon = new Polygon();
    for(int i = 0; i<numPoints; i+=2)
    {
      heptagonLayout[i] = Math.sin(star*Math.PI/(numPoints/2)*i)*radius;
      heptagonLayout[i+1] = Math.cos(star*Math.PI/(numPoints/2)*i)*radius;
    }
    polygon.getPoints().setAll(heptagonLayout);
  }
  
  Polygon getPolygon()
  {
    return polygon;
  }

  void tick()
  {
    polygon.setRotate(randomIncrement);
    polygon.setTranslateX(polygon.getTranslateX()+Math.cos(angle)*velocity);
    polygon.setTranslateY(polygon.getTranslateY()+Math.sin(angle)*velocity);
    
    randomIncrement+=1;
  }
}
