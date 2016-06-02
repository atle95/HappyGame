package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import gameobjects.HappyGame;
import gameobjects.Player;

public class Client
{
  private Socket clientSocket;
  private PrintWriter write;
  private BufferedReader reader;
  private long startNanoSec;
  private Scanner keyboard;
  private ClientListener listener;
  public int screenWidth = 640;
  public int screenHeight = 400;
  Player player;
  HappyGame game;

  public Client(String host, int portNumber)
  {
    startNanoSec = System.nanoTime();
//    System.out.println("Starting Client: " + System.nanoTime());

    keyboard = new Scanner(System.in);
    
    while (!openConnection(host, portNumber))
    {
      
    }
    
    listener = new ClientListener();
//    System.out.println("Client(): Starting listener = : " + listener);
    listener.start();
    
    game = new HappyGame();
//    game.addPlayer(false);
//    game.addPlayer(false);
//    game.addPlayer(false);
//    write.println( startNanoSec + ": Starting Game" );
    
    Thread t = new Thread(game);
        
    t.start();
    
//    System.out.println("Number of Players: "+game.playerList.size());
    
    listenToUserRequests();
    closeAll();

  }
  
  private boolean openConnection(String host, int portNumber)
  {

    try
    {
      clientSocket = new Socket(host, portNumber);
    }
    catch (UnknownHostException e)
    {
      System.err.println("Client Error: Unknown Host " + host);
      e.printStackTrace();
      return false;
    }
    catch (IOException e)
    {
      System.err.println("Client Error: Could not open connection to " + host
          + " on port " + portNumber);
      e.printStackTrace();
      return false;
    }

    try
    {
      OutputStream os = clientSocket.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os);
      BufferedWriter bw = new BufferedWriter(osw);
      write = new PrintWriter(clientSocket.getOutputStream(), true);
//      write.println("Hello from Client!" + this.game.playerList.get(0).isClientPlayer);
    }
    catch (IOException e)
    {
      System.err.println("Client Error: Could not open output stream");
      e.printStackTrace();
      return false;
    }
    try
    {
      InputStream is = clientSocket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    catch (IOException e)
    {
      System.err.println("Client Error: Could not open input stream");
      e.printStackTrace();
      return false;
    }
    return true;

  }
  
  private void listenToUserRequests()
  {
    while (true)
    {
//      String cmd = keyboard.nextLine();
//      short cmd = game.getPlayerControl();
//      if( cmd !=0 )
//      System.out.println("doing "+cmd);
//      if (cmd == null) continue;
//      if (cmd.length() < 1) continue;

//      char c = cmd.charAt(0);
//      if (c == 'q') System.exit(0);;

//      write.println(""+cmd);
    }
  }

  public void closeAll()
  {
    System.out.println("Client.closeAll()");

    if (write != null) write.close();
    if (reader != null)
    {
      try
      {
        reader.close();
        clientSocket.close();
      }
      catch (IOException e)
      {
        System.err.println("Client Error: Could not close");
        e.printStackTrace();
      }
    }

  }

  private String timeDiff()
  {
    long namoSecDiff = System.nanoTime() - startNanoSec;
    double secDiff = (double) namoSecDiff / 1000000000.0;
    return String.format("%.6f", secDiff);

  }

  public static void main(String[] args)
  {
    
    String host = null;
    int port = 0;
   
    
   
    try
    {
      host = InetAddress.getLocalHost().getHostName();//args[0];
      port = 5555;//Integer.parseInt(args[1]);
      if (port < 1) throw new Exception();
    }
    catch (Exception e)
    {
      System.out.println("Usage: Client hostname portNumber");
      System.exit(0);
    }
    
//    System.out.println("new Client(host, port);");
    Client c = new Client(host, port);
    
    

  }

  class ClientListener extends Thread
  {
    public void run()
    {
      System.out.println("ClientListener.run()");
      while (true)
      {
        read();
      }
    }

    private void read()
    {
      try
      {
//        System.out.println("Client: listening to socket");
        String msg = reader.readLine();
        System.out.print(msg);
//        System.out.println( timeDiff() + ": " + msg);

      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }

  }


}