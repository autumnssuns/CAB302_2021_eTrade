package client.data;

import common.Request;
import common.Response;

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
        InputStream in = null;
        try{
            in = this.getClass().getClassLoader().getResourceAsStream("client-config.properties");
            props.load(in);
            in.close();

            // specify the data source, username and password
            address = props.getProperty("host");
            port = Integer.parseInt(props.getProperty("port"));

            Start();
            sendRequest(new Request(null, "test"));
            Close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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