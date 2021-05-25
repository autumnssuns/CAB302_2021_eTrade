package server.Excluded_PUT_ALL_EXCLUSIONS_HERE.Features;

import common.Request;
import common.Response;
import common.dataClasses.DataCollection;
import common.dataClasses.IData;
import common.dataClasses.Stock;
import common.dataClasses.User;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.*;

/**
 * Provides functions for server to interact with the database and
 * return results to the client
 */
public class Server implements Serializable {
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int port = 5678;

    public  void Start() throws  IOException{
        try {
            serverSocket = new ServerSocket(port);
            // Temporary prompt message
            System.out.println("Server started and waiting for client...");
            socket = serverSocket.accept();
            System.out.println("Client accepted.");

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Close() throws IOException{
        try {
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Waits for request from client and process it, the server only waits for
     * request from the client at the socket so there is no parameter needed
     * @throws IOException
     */
    public Response SendResponse() throws IOException, ClassNotFoundException {

        // For handler to assign value to serverResponse
        //setup the shell
        IData attachment;
        //dummy response - Response from server
        Response serverResponse = new Response(false, null);
        //Read the client request
        final Request clientRequest = (Request) in.readObject();
        //Get senders' information
        User sender = clientRequest.getUser();
        //Get the command string from the sender
        String command = clientRequest.getAction();
        switch (command){

            case "Login":
                String userName = sender.getUsername();
                String password = sender.getPassword();
                Boolean status = LoginSystem.login(userName,password);
                if(status){
                    serverResponse = CasesToResponse.Login(userName, password);
                    out.writeObject(serverResponse);
                }
                break;

                // Use overloaded method for these cases: Query, Delete, Edit and Add
            case "Query an Object":
                serverResponse = CasesToResponse.query(clientRequest);
                out.writeObject(serverResponse);
                break;

            case "Query Users":
                UserDataSource userDataSource = new UserDataSource();
                DataCollection<User> userList = userDataSource.getUserList();
                serverResponse = new Response(true, userList);
                out.writeObject(serverResponse);
                userDataSource.close();
                break;

            case  "query Stock":
                serverResponse = CasesToResponse.queryStock(sender);
                out.writeObject(serverResponse);
                break;

            case "Query Stocks":

                break;

            case "Query Organisational Units":
                OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
                attachment = organisationsDataSource.getOrganisationList();
                serverResponse = new Response(true, attachment);
                out.writeObject(serverResponse);
                organisationsDataSource.close();
                break;

            case "Query Orders":
                OrderDataSource orderDataSource = new OrderDataSource();
                attachment = orderDataSource.getOrderList();
                serverResponse = new Response(true, attachment);
                out.writeObject(serverResponse);
                orderDataSource.close();
                break;

            case "Query Assets":
                AssetsDataSource assetsDataSource = new AssetsDataSource();
                attachment = assetsDataSource.getAssetList();
                serverResponse = new Response(true, attachment);
                out.writeObject(serverResponse);
                assetsDataSource.close();
                break;

            case "delete":
                serverResponse = CasesToResponse.delete(clientRequest);
                out.writeObject(serverResponse);
                break;

            case "edit":
                serverResponse = CasesToResponse.edit(clientRequest);
                out.writeObject(serverResponse);
                break;

            case "add":
                serverResponse = CasesToResponse.add(clientRequest);
                out.writeObject(serverResponse);
                break;
        }

        return serverResponse;
    }

    public static void main (String args[]) throws IOException, ClassNotFoundException {
        Server server = new Server();
        server.Start();
        server.SendResponse();
    }
}
