package AddressServer;

import java.rmi.RemoteException;

public class RunAddressServer
{
  public static void main(String[] args) throws RemoteException
  {
    AddressServer addressServer = new AddressServerImpl();
    addressServer.start();
  }
}
