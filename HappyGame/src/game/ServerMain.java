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
    
    server.bind(port);
    server.addListener(new Listener()
        {
          @Override
          public void connected(Connection c)
          {
            System.out.println("Sending Message from server");
            PacketMessage pm = new PacketMessage();
            pm.message = "Time: " + new Date().toString();
            c.sendTCP(pm);
            connected++;
            System.out.println("Hi Lady! connected: "+ connected);
            PlayerPacket playerPacket = new PlayerPacket();
            
            PacketMessage packet = new PacketMessage();
            packet.id = c.getID();
            server.sendToAllExceptTCP(c.getID(), packet);
            
//            for(Player p : game.getPlayerList()){
//              PacketMessage msg = new PacketMessage();
//              c.sendTCP(msg);
//            }
            
            System.out.println("Connection received.");
          }
          
          @Override
          public void received(Connection c, Object o)
          {
            if( o instanceof PacketMessage)
            {
              PacketMessage pm = (PacketMessage) o;
//              System.out.println(pm.message);
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
//      upx.x = playerx;
      upx.x = upx.x+10;
      server.sendToAllTCP(upx);
      
//      int playery = serverPlayerY;
//      System.out.println(playery);
      UpdatePlayerY upy = new UpdatePlayerY();
//      upy.y = playery;
      upy.y = upy.y+10;
      server.sendToAllTCP(upy);
      
      game.getPlayerList().get(0).xpos+=1;
      game.getPlayerList().get(0).ypos+=1;
      
    }
  };
  
  public static void main(String args[]) throws IOException, InterruptedException
  {
    new ServerMain(port);
    
    game = new HappyGame();
    
    while(!messageRecieved)
    {
//      System.out.println(game.playerList.get(0).xpos);
//      System.out.println(game.playerList.get(0).ypos);
//      System.out.print(".");
      Thread.sleep(1000);
    }
  }
}