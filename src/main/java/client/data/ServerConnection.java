package client.data;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import server.IServer;
import server.MockServer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

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
        Properties props = new Properties();
        InputStream in;
        try{
            in = this.getClass().getClassLoader().getResourceAsStream("client-config.properties");
            props.load(in);
            in.close();

            // specify the data source, username and password
            address = props.getProperty("host");
            port = Integer.parseInt(props.getProperty("port"));

            pingServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the connection
     * @throws IOException
     */
    public void Start() throws IOException {
        socket = new Socket(address, port);
        // Temporary prompt message
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Checks if the server is active
     * @return true if a connection can be established, false otherwise
     */
    public boolean pingServer() {
        try{
            Start();
            Close();
            return true;
        } catch (IOException e) {
            return false;
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
            System.out.println("Sending request " + request.getActionType() + " on " + request.getObjectType());
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