package gameobjects;

import java.util.ArrayList;

import game.ClientMain;

public class EntityManager
{
  ClientMain main;
  
  public ArrayList<Player> playerList;
  public ArrayList<GamePolygon> gameStuff;
  
  
  public EntityManager(ClientMain main)
  {
    this.main = main;
  }
  
  public void createEntities()
  {
    playerList = createPlayers(4);
    gameStuff = createGameStuff(20);
  }
  
  public ArrayList<Player> createPlayers(int numPlayers)
  {
    ArrayList<Player> tempList = new ArrayList<Player>();
    for(int i = 0; i< numPlayers; i++)
    {
      tempList.add(new Player(false, null));
    }
    return tempList;
  }
  
  public ArrayList<GamePolygon> createGameStuff(int numGamePolygons)
  {
    ArrayList<GamePolygon> tempList = new ArrayList<GamePolygon>();
    for(int i = 0; i< numGamePolygons; i++)
    {
      tempList.add(new GamePolygon(i, i, i, i, i));
    }
    return tempList;
  }

  public void tick() 
  {
      for(GamePolygon element: gameStuff)
      {
        element.tick();
      }
      for(Player element: playerList)
      {
        element.tick();
      }    
  }

}
