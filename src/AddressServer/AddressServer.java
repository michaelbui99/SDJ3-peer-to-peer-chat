package AddressServer;

import Peer.Peer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AddressServer extends Remote
{
 void registerPeer(Peer peer) throws RemoteException;
 List<String> findAll() throws RemoteException;
 Peer findPeer(String alias) throws RemoteException;
 void start() throws RemoteException;
}
