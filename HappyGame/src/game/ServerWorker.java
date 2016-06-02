package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker extends Thread
{
  private Socket client;
  private PrintWriter clientWriter;
  private BufferedWriter bw;
  private BufferedReader clientReader;

  public ServerWorker(Socket client)
  {
    this.client = client;

    try
    {
//                PrintWriter(OutputStream out, boolean autoFlushOutputBuffer)
      OutputStream os = client.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os);
      bw = new BufferedWriter(osw);
      bw.write("Hello");
      clientWriter = new PrintWriter(client.getOutputStream(), true);
    }
    catch (IOException e)
    {
      System.err.println("Server Worker: Could not open output stream");
      e.printStackTrace();
    }
    try
    {
      InputStream is = client.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      
      clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      
    }
    catch (IOException e)
    {
      System.err.println("Server Worker: Could not open input stream");
      e.printStackTrace();
    }
  }
  
  //Called by ServerMaster
  public void send(String msg)
  {
//    System.out.println("ServerWorker.send(" + msg + ")");
//    clientWriter.println(msg);
    clientWriter.write(msg);
    
  }

  public void run()
  {
//    System.out.println("ClientListener.run()");
    while (true)
    {
      read();
    }
  }

  private void read()
  {
    try
    {
//      System.out.println("Client: listening to socket");
      String msg = clientReader.readLine();
//      System.out.println(msg);
      

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}