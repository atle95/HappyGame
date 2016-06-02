package gameobjects;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class HappyGame extends Application
{
  
  Player player;
  ArrayList<GamePolygon> gamePolyList = new ArrayList<>();
  Canvas background = new Canvas(1280, 800);
  GraphicsContext gfx = background.getGraphicsContext2D();
  StackPane pane = new StackPane();
  public boolean paused = false;
  Random r = new Random();
  public int screenWidth = 1280;
  public int screenHeight = 800;
  
  public HappyGame()
  {
    
    for (int i = 0; i<20; i++)
    {
      GamePolygon gamePolygon = new GamePolygon(r.nextInt(14)+4, r.nextInt(4), r.nextInt(20)+10, r.nextDouble(), r.nextDouble()*2*Math.PI);
      gamePolygon.getPolygon().setFill(Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255)));;
      gamePolygon.getPolygon().setStroke(Color.BLACK);;
      pane.getChildren().add(gamePolygon.getPolygon());
      gamePolyList.add(gamePolygon);
    }
    pane.getChildren().add(background);
    player = new Player(this);
    Polygon[] polyList = player.getPentagonList();
    pane.getChildren().addAll(polyList[4], polyList[0], polyList[1], polyList[2], polyList[3]);
//    pane.getChildren().addAll(gamePolygon.getPolygon());
  }

  @Override
  public void start(Stage primaryStage) throws Exception 
  {
    
    
    Scene scene = new Scene(pane, 1280, 800);
    scene.addEventHandler(KeyEvent.KEY_PRESSED,  new KeyboardEventHandler(player));
    scene.addEventHandler(KeyEvent.KEY_RELEASED, new KeyboardEventHandler(player));
    
    primaryStage.setScene(scene);
    primaryStage.show();
    timer.start();
    
  }
  
  AnimationTimer timer = new AnimationTimer()
  {
    @Override
    public void handle(long now) 
    {
      if(!paused)
      {
        for(GamePolygon element: gamePolyList)
        {
          element.tick();
        }
        player.tick();
//        gfx.setFill(Color.BLACK);
//        gfx.fillRect(player.xpos+screenWidth/2-player.radius/2, player.ypos+screenHeight/2-player.radius/2, 20, 20);
      }
    }
  };
  
  public StackPane getStackPane()
  {
    return this.pane;
  }
  
  public void setStackPane(StackPane pane)
  {
    this.pane = pane;
  }
  
  public void addPlayer()
  {
    Player newPlayer = new Player(this);
    Polygon[] polyList = newPlayer.getPentagonList();
    pane.getChildren().addAll(polyList[4], polyList[0], polyList[1], polyList[2], polyList[3]);
  }
  
  public void play(String args[])
  {
    launch(args);
  }

}
