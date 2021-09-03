package Peer;

import AddressServer.AddressServerImpl;
import Peer.PeerImpl;

import java.rmi.RMISecurityManager;
import java.util.Scanner;

public class Run
{
  public static void main(String[] args)
  {
    System.setProperty("java.security.policy", "security.policy");
    if (System.getSecurityManager() == null){
      System.setSecurityManager(new SecurityManager());
    }
    PeerImpl p1 = new PeerImpl();
    p1.start();
    while (true)
    {
      Scanner keyboard = new Scanner(System.in);
      System.out.println("Set your alias: ");
      p1.setAlias(keyboard.nextLine());
      System.out.println("\n");
      System.out.println("Who do you want to connect to?");
      for (String alias : p1.getAllRegisteredPeers())
      {
        System.out.println(alias +  "\n");
      }

      p1.connect(keyboard.nextLine());
      System.out.println("");

      boolean connected = true;
      while (connected)
      {
        String message = keyboard.nextLine();
        if (message.equals("PEERSYSTEM.DISCONNECT")){
          connected = false;
          System.out.println("You disconnected");
        }
        else
        {
          p1.sendMessage(message);
        }
      }
    }
  }
}
