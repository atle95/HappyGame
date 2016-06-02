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

public class HappyGame extends Application implements Runnable
{
  
//  Player player;
  ArrayList<GamePolygon> gamePolyList = new ArrayList<>();
  public ArrayList<Player> playerList = new ArrayList<>();
  Canvas background = new Canvas(1280, 800);
  GraphicsContext gfx = background.getGraphicsContext2D();
  StackPane pane = new StackPane();
  public boolean paused = false;
  Random r = new Random();
  public int screenWidth = 640;
  public int screenHeight = 400;
  int currentPlayers = 0;
  int pendingPlayers = 0;
  Scene gameScene;
  Scene startScene;
  
  public HappyGame()
  {
    pane.getChildren().add(background);
    
    for (int i = 0; i<20; i++)
    {
      GamePolygon gamePolygon = new GamePolygon(r.nextInt(14)+4, r.nextInt(4), r.nextInt(20)+10, r.nextDouble(), r.nextDouble()*2*Math.PI);
      gamePolygon.getPolygon().setFill(Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255)));;
      gamePolygon.getPolygon().setStroke(Color.BLACK);;
      pane.getChildren().add(gamePolygon.getPolygon());
      gamePolyList.add(gamePolygon);
    }
    
    addPlayer(true);
    addPlayer(false);
//    Player player = new Player(this, true);
//    playerList.add(player);
    for(Player p: playerList)
    {
      Polygon[] polyList = p.getPentagonList();
      pane.getChildren().addAll(polyList[4], polyList[0], polyList[1], polyList[2], polyList[3]);
    }

    timer.start();
//    System.out.println("happyGame Constructor ends");
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
        for(Player element: playerList)
        {
          element.tick();
        }
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
  
  public void addPlayer(Boolean clientPlayer)
  {
    Player newPlayer = new Player(this, clientPlayer);
    playerList.add(newPlayer);
    Polygon[] polyList = newPlayer.getPentagonList();
    newPlayer.tick();
  }
  
  public short getPlayerControl()
  {
//    System.out.println(playerList.get(0).getControls());
    return playerList.get(0).getControls();
    
  }
  
  //Quick workaround to not kill the rest of the program
  @Override
  public void run()
  {
    String[] a = new String[0];
    launch(a);
  }

  @Override
  public void start(Stage primaryStage) throws Exception 
  {
    
    gameScene = new Scene(pane, screenWidth, screenHeight);
    gameScene.addEventHandler(KeyEvent.KEY_PRESSED,  new KeyboardEventHandler(playerList.get(0)));
    gameScene.addEventHandler(KeyEvent.KEY_RELEASED, new KeyboardEventHandler(playerList.get(0)));

    startScene = new Scene(new StackPane(), screenWidth, screenHeight);
    primaryStage.setScene(gameScene);
    primaryStage.show();
  }

}
