package Peer;

import Peer.Peer;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private String receiverAlias;
    private Peer peer;

    public Message(String message, String receiverAlias, Peer peer) {
        this.message = message;
        this.receiverAlias = receiverAlias;
        this.peer = peer;
    }

    public String getReceiverAlias() {
        return receiverAlias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Peer getPeer() {
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", receiverAlias='" + receiverAlias + '\'' +
                ", peer=" + peer +
                '}';
    }
}
