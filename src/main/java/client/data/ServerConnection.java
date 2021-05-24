package client.data;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import server.IServer;
import server.MockServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public final class ServerConnection implements IServerConnection{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String address;
    private int port;

    /**
     * Initiate the host and port to connect to
     */
    public ServerConnection() {
        address = "127.0.0.1";
        port = 5678;
        try{
            Start();
            sendRequest(new Request(null, "test"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the connection
     * @throws IOException
     */
    public void Start() throws IOException {
        try {
            socket = new Socket(address, port);
            // Temporary prompt message
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
    * Sends request to server using an ObjectOutputStream instance
    * @param request The user's request
    * @return the Response as an Object from server
    */
    @Override
    public Response sendRequest(Request request) {
        try{
            out.writeObject(request);
            return (Response) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Response(false, null);
    }

    /**
     * Closes the connection
     */
    @Override
    public void Close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}