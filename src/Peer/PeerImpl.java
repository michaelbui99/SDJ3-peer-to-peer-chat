package Peer;

import AddressServer.AddressServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PeerImpl implements Peer
{
  private AddressServer server;
  private String alias;
  private Peer connectedPeer;

  public PeerImpl()
  {
    try
    {
      UnicastRemoteObject.exportObject(this, 0);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void start()
  {
    Registry registry = null;
    try
    {
      registry = LocateRegistry.getRegistry(1099);
      server = (AddressServer) registry.lookup("AddressServer");

    }
    catch (RemoteException | NotBoundException e)
    {
      e.printStackTrace();
    }

  }

  @Override public void sendMessage(String message)
  {
    try
    {
      connectedPeer.messageReceived(message + " From " + alias);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void connect(String alias)
  {
    try
    {
      connectedPeer = server.findPeer(alias);
      if (connectedPeer!= null){
        System.out.println("Connected to " + connectedPeer.getAlias());
      }
      else{
        System.out.println("Failed connection");
      }
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override public String getAlias()
  {
    return alias;
  }

  @Override public void setAlias(String alias)
  {
    this.alias = alias;
    try
    {
      server.registerPeer(this);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override public List<String> getAllRegisteredPeers()
  {
    try
    {
      return server.findAll();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void messageReceived(String message)
  {
    System.out.println(message);
  }
}
