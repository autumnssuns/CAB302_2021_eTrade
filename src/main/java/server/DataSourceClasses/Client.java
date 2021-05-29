package server.DataSourceClasses;

import common.Request;
import common.Response;
import common.dataClasses.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Provides functions for client to interact with the server from the GUI
 */
public class Client implements Serializable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String address = "127.0.0.1";
    private int port = 5678;

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

    public  void Close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends request to server using an ObjectOutputStream instance
     * @param request The user's request
     * @return the Response as an Object from server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Response sendRequest(Request request) throws IOException, ClassNotFoundException {
        out.writeObject(request);
        return (Response) in.readObject();
    }
    public static void main (String args[]) throws IOException, ClassNotFoundException {
        Client client = new Client();
        while (client.socket == null){
            try{
                System.out.println("Attempt to connect!");
                client.Start();
            }
            catch (IOException e2){
                e2.printStackTrace();
            }
        }

        User testUser = new User("Duy", "123");
        Request loginRequest = new Request(testUser,"Login" );
        Response serverResponse = client.sendRequest(loginRequest);
        User information = (User) serverResponse.getAttachment();
//        information.getAccountType();
//        information.getPassword();
        System.out.println(information.getPassword());
    }
}
