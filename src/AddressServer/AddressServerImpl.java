package AddressServer;

import Peer.Peer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AddressServerImpl implements AddressServer
{

  private List<String> peers = new ArrayList<>();
  private Registry registry;

  public AddressServerImpl()
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
    int currentPort = 1099;
    try
    {
      registry = LocateRegistry.createRegistry(1099);
      registry.bind("AddressServer", this);
      System.out.println("AddressServer started...");
    }
    catch (AlreadyBoundException | RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void registerPeer(Peer peer)
  {
    try
    {
      peers.add(peer.getAlias());
      registry.bind(peer.getAlias(), peer);
    }
    catch (RemoteException | AlreadyBoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public List<String> findAll()
  {
    return peers;
  }

  @Override public Peer findPeer(String alias)
  {
    try
    {
      return (Peer) registry.lookup(alias);
    }
    catch (RemoteException | NotBoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }

}
