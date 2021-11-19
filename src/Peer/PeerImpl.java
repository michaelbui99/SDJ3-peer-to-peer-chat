package Peer;

import AddressServer.AddressServer;

import java.net.BindException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PeerImpl implements Peer {
    private String alias;
    private Peer connectedPeer;
    private List<Peer> cache = new ArrayList<>();
    private Registry localRegistry;
    Scanner keyboard = new Scanner(System.in);
    int currentPort = (int) (Math.random()*65000);

    public PeerImpl() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {

        Registry remoteRegistry = null;

        System.out.println("Choose alias: ");
        connectToNetwork(currentPort, keyboard);


    }

    private void connectToNetwork(int currentPort, Scanner keyboard){
        try {
            Registry remoteRegistry;
            setAlias(keyboard.nextLine());
            localRegistry = LocateRegistry.createRegistry(currentPort);
            System.out.println(currentPort);
            localRegistry.bind(alias, this);
            System.out.println("Enter alias of known peer");
            String peerAlias = keyboard.nextLine();
            System.out.println("Enter port of known peer");
            int peerPort = keyboard.nextInt();
            remoteRegistry = LocateRegistry.getRegistry(peerPort);
            Peer knownPeer = (Peer) remoteRegistry.lookup(peerAlias);
            if (knownPeer != null) {
                cache.add(knownPeer);
                List<Peer> knownPeerRoutingOverlay = knownPeer.getRoutingOverlay();
                for (Peer peer : knownPeerRoutingOverlay) {
                    if (!cache.contains(peer)) {
                        cache.add(peer);
                        System.out.println(currentPort);
                    }
                }

            } else {
                System.out.println("No peer was found");
                System.exit(-1);
            }
        } catch (RemoteException  e) {
            e.printStackTrace();
        } catch (AlreadyBoundException|NotBoundException e) {
            currentPort++;
            connectToNetwork(currentPort, keyboard);
        }
    }

    @Override
    public void sendMessage(Message message) {

        if (message == null) {
            throw new IllegalArgumentException();
        }

        Peer receiver = null;
        for (Peer peer : cache) {
            try {
                if (peer.getAlias().equals(message.getReceiverAlias())) {
                    receiver = peer;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        try {
            receiver.messageReceived(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }


    @Override
    public void messageReceived(Message message) {
        System.out.println(message);
        if (!cache.contains(message.getPeer())) {
            cache.add(message.getPeer());
        }
    }

    @Override
    public List<Peer> getRoutingOverlay() throws RemoteException {
        return cache;
    }
}
