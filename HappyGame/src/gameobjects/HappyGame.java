package gameobjects;

import java.util.ArrayList;
import java.util.Random;

import game.ClientMain;
import game.ServerMain;
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
  
  ArrayList<GamePolygon> gamePolyList = new ArrayList<>();
  private ArrayList<Player> playerList = new ArrayList<>();
  
  Canvas background = new Canvas(1280, 800);
  GraphicsContext gfx = background.getGraphicsContext2D();
//  StackPane pane;
  
//  GraphicsContext gfx = background.getGraphicsContext2D();
  StackPane pane = new StackPane();
  public boolean paused = false;
  Random r = new Random();
  
  public int screenWidth = 640;
  public int screenHeight = 400;


  int currentPlayers = 0;
  int pendingPlayers = 0;
  
  Scene gameScene;
  Scene startScene;
  private ClientMain c;
  
  
  public HappyGame(ServerMain s)
  {
    
  }
  
  public HappyGame()
  {
    pane = new StackPane(background);
    for (int i = 0; i<20; i++)
    {
      GamePolygon gamePolygon = new GamePolygon(r.nextInt(14)+4, r.nextInt(4), r.nextInt(20)+10, r.nextDouble(), r.nextDouble()*2*Math.PI);
      gamePolygon.getPolygon().setFill(Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255)));;
      gamePolygon.getPolygon().setStroke(Color.BLACK);;
      pane.getChildren().add(gamePolygon.getPolygon());
      gamePolyList.add(gamePolygon);
    }
    
    for(int i = 0; i<4; i++)
    {
      Player player;
      if(i==0)
      {
        player = new Player(true, this);
      }
      else
      {
        player = new Player(true, this);
      }
      player.xpos +=i*50;
      player.ypos +=i*50;
      playerList.add(player);
      Polygon[] polyList = player.getPentagonList();
      pane.getChildren().addAll(polyList[4], polyList[0], polyList[1], polyList[2], polyList[3]);
    }

  }
  
  
  public AnimationTimer timer = new AnimationTimer()
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
//        c.clientPlayerX = playerList.get(0).xpos;
//        c.clientPlayerY = playerList.get(0).ypos;
//      for(Player p: playerList)
//      {
//      }
//        gfx.setFill(Color.BLACK);
//        gfx.fillRect(player.xpos+screenWidth/2-player.radius/2, player.ypos+screenHeight/2-player.radius/2, 20, 20);
      }
    }
  };
  public int xpos;
  public int ypos;
  
  public StackPane getStackPane()
  {
    return this.pane;
  }
  
  public void setStackPane(StackPane pane)
  {
    this.pane = pane;
  }
  
  public void setPlayerPosition(int plr, int x, int y)
  {
    playerList.get(plr).xpos = x;
    playerList.get(plr).ypos = y;
  }
  
  public void addPlayer(Boolean clientPlayer)
  {
    Player newPlayer = new Player(clientPlayer, this);
    playerList.add(newPlayer);
    Polygon[] polyList = newPlayer.getPentagonList();
    newPlayer.tick();
  }
  
  public ArrayList<Player> getPlayerList()
  {
    return playerList;
  }
  
  //Quick workaround to not kill the rest of the program
  @Override
  public void run()
  {
    String[] a = new String[0];
    launch(a);
  }
  
  public Scene getScene()
  {
    return gameScene;
  }

  public void setClient(ClientMain c)
  {
    this.c = c;
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
    timer.start();
  }
}
