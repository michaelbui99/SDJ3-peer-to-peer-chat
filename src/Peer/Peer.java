package Peer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Peer extends Remote
{
  public void start() throws RemoteException;
  public void sendMessage(String message) throws RemoteException;
  public void connect(String alias) throws RemoteException;
  public String getAlias() throws RemoteException;
  public void setAlias(String alias) throws RemoteException;
  public List<String> getAllRegisteredPeers() throws RemoteException;
  public void messageReceived(String message) throws RemoteException;
}
