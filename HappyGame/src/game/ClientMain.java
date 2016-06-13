package game;

import java.io.IOException;
import java.util.Date;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import gameobjects.EntityManager;
import gameobjects.HappyGame;
import gameobjects.Player;
import javafx.animation.AnimationTimer;

public class ClientMain extends Listener
{
  //kryonet
  static Client client;
  public int id;
  static int    port = 54555;
  static String host = "localhost";
  
  private long startNanoSec;
  public int screenWidth = 640;
  public int screenHeight = 400;
  public int clientPlayerX = 0;
  public int clientPlayerY = 0;

  static HappyGame game;
  
  static Boolean messageRecieved = false;

  public ClientMain(String host, int port) throws IOException
  {
    client = new Client();
    client.getKryo().register(PacketMessage.class);
    client.getKryo().register(UpdatePlayerX.class);
    client.getKryo().register(UpdatePlayerY.class);
    client.getKryo().register(UpdatePlayerID.class);
    
    client.addListener(new Listener()
        {
          @Override
          public void connected(Connection c)
          {
            PacketMessage pm = new PacketMessage();
            pm.message = "Client: connected(Connection c)";
//            System.out.println("Sending Message ["+pm.message+"] from client");
            c.sendTCP(pm); 
          }
        
          @Override
          public void received(Connection c, Object o)
          {
            if( o instanceof PacketMessage)
            {
              PacketMessage pm = (PacketMessage) o;
              System.out.println("From server: " + pm.message);
              // endless loop
              // pm.message = "Client Recieved Server Message";
              // c.sendTCP(pm);
            }
            if( o instanceof UpdatePlayerX)
            {
              UpdatePlayerX pm = (UpdatePlayerX) o;
//              System.out.println("From server: x " + pm.x);
            }
            if( o instanceof UpdatePlayerY)
            {
              
              UpdatePlayerY pm = (UpdatePlayerY) o;
//              System.out.println("From server: y " + pm.y);
            }
            if( o instanceof UpdatePlayerID)
            {
              
              UpdatePlayerID upid = (UpdatePlayerID) o;
              id = upid.id;
            }
            messageRecieved = true;
          }
        
          @Override
          public void disconnected(Connection c)
          {
            System.out.println("Disconnected!");
            PacketMessage m = new PacketMessage();
            m.message = "Client Disconnected";
            c.sendTCP(m);
          }
        });

    client.start();
    client.connect(5000, host, port);
  }
  
  AnimationTimer timer = new AnimationTimer()
  {
    int counter = 0;
    @Override
    public void handle(long now) 
    {
//      PacketMessage pm = new PacketMessage();
//      pm.message = "Time: " + new Date().toString();
//      client.sendTCP(pm);
      
      UpdatePlayerX upx = new UpdatePlayerX();
      int playerx = clientPlayerX;
      upx.x = playerx;
      client.sendTCP(upx);
      
      
//      System.out.println(playery);
      UpdatePlayerY upy = new UpdatePlayerY();
      int playery = clientPlayerY;
      upy.y = playery;
      client.sendTCP(upy);
      
      counter++;
      game.setPlayerPosition(1, 20, 20);
      
//      System.out.printf("Player x: %d, Player y: %d\n", game.getPlayerX(), game.getPlayerY());
    }
  };
 
  public static void main(String[] args) throws IOException, InterruptedException
  {
    ClientMain c = new ClientMain(host, port);
    EntityManager em = new EntityManager(c);
    game = new HappyGame(em);
    
    Thread gameThread = new Thread(game);
    //bind properties here
    
    gameThread.start();
    
    
    while(!messageRecieved)
    {
      Thread.sleep(1000);
    }
  }

  public void startTimer()
  {
    timer.start();    
  }

  public void setClientPlayerX(int xpos)
  {
    this.clientPlayerX = xpos;
  }
  
  public void setClientPlayerY(int ypos)
  {
    this.clientPlayerY = ypos;
  }

}