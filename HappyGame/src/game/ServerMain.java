package game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import gameobjects.HappyGame;
import gameobjects.Player;
import javafx.animation.AnimationTimer;

public class ServerMain extends Listener
{
  //kryonet
  public static Server server;
  public static int port = 54555;
  private static boolean messageRecieved = false;
  
  Random r = new Random();
  static HappyGame game;
  int connected = 0;
  
  public ServerMain(int port) throws IOException
  {
    server = new Server();
    
    server.getKryo().register(PacketMessage.class);
    server.getKryo().register(UpdatePlayerX.class);
    server.getKryo().register(UpdatePlayerY.class);
    server.getKryo().register(UpdatePlayerID.class);
    
    server.bind(port);
    server.addListener(new Listener()
        {
          @Override
          public void connected(Connection c)
          {
            connected++;
            System.out.println("Hi Lady! connected: "+ connected);
            
            PacketMessage pm = new PacketMessage();
            pm.message = "ServerMain: connected(Connection c)";
            c.sendTCP(pm);
            
            UpdatePlayerID upid = new UpdatePlayerID();
            upid.id = connected;
            c.sendTCP(upid);
            
            
//            PacketMessage packet = new PacketMessage();
//            packet.id = c.getID();
//            server.sendToAllExceptTCP(c.getID(), packet);
            
          }
          
          @Override
          public void received(Connection c, Object o)
          {
            if( o instanceof PacketMessage)
            {
              PacketMessage pm = (PacketMessage) o;
              System.out.println("From Client: [" + pm.message+"]");
              // Infinite Communication Loop
              // pm.message = "serverMain: received(Connection c, Object o)";
              // c.sendTCP(pm);
            }
            if( o instanceof UpdatePlayerX)
            {
              UpdatePlayerX pm = (UpdatePlayerX) o;
//              System.out.println("Update PlayerX to "+pm.x);
              
              UpdatePlayerX upx = new UpdatePlayerX();
              upx.x = pm.x+10;
              c.sendTCP(upx);
//              System.out.print("From client playerx: "+ pm.x+"\r" );
            }
            if( o instanceof UpdatePlayerY)
            {
              UpdatePlayerY pm = (UpdatePlayerY) o;
              
              UpdatePlayerY upy = new UpdatePlayerY();
              upy.y = pm.y+10;
              c.sendTCP(upy);
//              serverPlayerY = pm.y;
              
//              System.out.print("From client playery: "+ pm.y+" \r");
            }
          }
          
          @Override
          public void disconnected(Connection c)
          {
            connected--;
            System.out.println("Ok Lady I love You Bye! num connected: "+ connected);
          }
        });
    
    server.start();
    timer.start();
    System.out.println("Loading... Done");
    
  }
  
  AnimationTimer timer = new AnimationTimer()
  {
    @Override
    public void handle(long now) 
    {
      PacketMessage pm = new PacketMessage();
      pm.message = "Time: " + new Date().toString();
      Connection[] list = server.getConnections();
      for(Connection element: list)
      {
      //server.getConnections()
        element.sendTCP(pm);
      }
      
//      int playerx = serverPlayerX;
      UpdatePlayerX upx = new UpdatePlayerX();
//      upx.x = game.getPlayerList().get(0).xpos;
      server.sendToAllTCP(upx);
      
//      int playery = serverPlayerY;
//      System.out.println(playery);
      UpdatePlayerY upy = new UpdatePlayerY();
//      upy.y = playery;
      server.sendToAllTCP(upy);
      
    }
  };
  
  public static void main(String args[]) throws IOException, InterruptedException
  {
    ServerMain s = new ServerMain(port);
    
    game = new HappyGame(s);
    
    while(!messageRecieved)
    {
//      System.out.println(game.playerList.get(0).xpos);
//      System.out.println(game.playerList.get(0).ypos);
//      System.out.print(".");
      Thread.sleep(1000);
    }
  }
}