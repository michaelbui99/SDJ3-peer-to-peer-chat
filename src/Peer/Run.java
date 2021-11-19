package Peer;

import AddressServer.AddressServerImpl;
import Peer.PeerImpl;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Run
{
  public static void main(String[] args) throws RemoteException {
    Scanner keyboard = new Scanner(System.in);
    boolean connected = false;
    Peer peer = new PeerImpl();
    peer.start();
    connected = true;
    while (connected){
      System.out.println("Enter receiver alias: ");
      String receiverAlias = keyboard.nextLine();
      System.out.println("Enter message: ");
      String messageContent = keyboard.nextLine();

      Message message = new Message(messageContent, receiverAlias, peer);
      peer.sendMessage(message);
    }

  }
}
