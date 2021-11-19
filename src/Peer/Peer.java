package Peer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Peer extends Remote
{
  public void start() throws RemoteException;
  public void sendMessage(Message message) throws RemoteException;
  public String getAlias() throws RemoteException;
  public void setAlias(String alias) throws RemoteException;
  public void messageReceived(Message message) throws RemoteException;
  public List<Peer> getRoutingOverlay() throws RemoteException;
}
