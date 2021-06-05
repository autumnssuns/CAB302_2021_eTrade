package server;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.IData;
import common.dataClasses.User;
import server.DataSourceClasses.CasesToResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Server implements IServer{
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int port;
    private boolean firstRun = true;

    /**
     * this is the timeout in between accepting clients, not reading from the socket itself.
     */
    private static final int SOCKET_ACCEPT_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);
    /**
     * Initiate the server and
     * //TODO initiate the database addess
     * @throws InvalidArgumentValueException
     */
    public Server() throws InvalidArgumentValueException {
        Properties props = new Properties();
        InputStream in = null;
        try{
            in = this.getClass().getClassLoader().getResourceAsStream("server-config.properties");
            props.load(in);
            in.close();

            // specify the data source, username and password
            port = Integer.parseInt(props.getProperty("port"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstRun = true;
    }

    /**
     * Starts listening for client.
     * @throws IOException
     */
    public void Start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
            // Temporary prompt message
            System.out.println("Running on port " + port + "...");
            int connectionNumber = 0;
            while (true) {
                if (!running.get()) {
                    break;
                }

                try {
                    final Socket socket = serverSocket.accept();
                    System.out.println(String.format("Accepted client connection - # %d", connectionNumber));
                    final int currentNumber = connectionNumber;
                    final Thread clientThread = new Thread(() -> handleConnection(socket));
                    connectionNumber++;
                    clientThread.start();
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the server is still running
                } catch (Exception e) {
                    // We will report other exceptions by printing the stack trace, but we
                    // will not shut down the server. A exception can happen due to a
                    // client malfunction (or malicious client)
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a single socket connection
     * @param socket The socket to handle
     */
    private void handleConnection(Socket socket) {
        try {
            // Initiates the IO streams
            final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Wait until the requests is sent through
            while (true) {
                try {
                    // Create a response and write to stream
                    final Request request = (Request) inputStream.readObject();
                    System.out.println(request.getActionType());
                    Response response = createResponse(request);
                    outputStream.flush();
                    outputStream.writeObject(response);
                    // Closes the socket after writing
                    socket.close();
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (InvalidArgumentValueException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            System.out.println(String.format("Connection %s closed", socket.toString()));
        }
    }

    /**
     * Closes the server (stop listening).
     * @throws IOException
     */
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
     * Create a response based on the request.
     * @param request The request sent by client.
     * @return An appropriate response.
     * @throws InvalidArgumentValueException
     */
    @Override
    public Response createResponse(Request request) throws InvalidArgumentValueException {
        // Unidentified requests are denied by default
        //Get senders' information
        User sender = request.getUser();
        IData attachment = request.getAttachment();
        Response response = new Response(false, null);
        switch (request.getActionType()){
            case TEST:
                if (firstRun){
                    CasesToResponse.initiate();
                    firstRun = false;
                    response = new Response(true, null);
                }
                break;

            case LOGIN:
                response = CasesToResponse.login(request);
                break;

            case READ_ALL:
                switch (request.getObjectType()){
                    case USER:
                        response = CasesToResponse.queryUsers();
                        break;

                    case ASSET:
                        response = CasesToResponse.queryAssets();
                        break;

                    case ORGANISATIONAL_UNIT:
                        response = CasesToResponse.queryOrganisations();
                        break;

                    case STOCK:
                        response = CasesToResponse.queryStocks();
                        break;

                    case ORDER:
                        response = CasesToResponse.queryOrders();
                        break;
                }
                break;

            case READ:
                response = CasesToResponse.query(request);
                break;


            case CREATE:
                response = CasesToResponse.add(request);
                break;

            case UPDATE:
                response = CasesToResponse.edit(request);
                break;

            case DELETE:
                response = CasesToResponse.delete(request);
                break;
        }
//            case Request.ActionType.TEST:
//                if (firstRun){
//                    CasesToResponse.initiate();
//                    firstRun = false;
//                    response = new Response(true, null);
//                }
//                break;
//
//            case Request.ActionType.LOGIN:
//                response = CasesToResponse.login(request);
//                break;
//
//            case Request.ActionType.READ_ALL).setAttachmentType(Request.ObjectType.USER:
//                response = CasesToResponse.queryUsers();
//                break;
//
//            case Request.ActionType.READ_ALL).setAttachmentType(Request.ObjectType.ASSET:
//                response = CasesToResponse.queryAssets();
//                break;
//
//            case Request.ActionType.READ_ALL).setAttachmentType(Request.ObjectType.ORGANISATIONAL_UNIT:
//                response = CasesToResponse.queryOrganisations();
//                break;
//
//            case Request.ActionType.READ_ALL).setAttachmentType(Request.ObjectType.STOCK:
//                response = CasesToResponse.queryStocks();
//                break;
//
//            case Request.ActionType.READ).setAttachmentType(Request.ObjectType.ORGANISATIONAL_UNIT:
//                OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
//                OrganisationalUnit unit = organisationsDataSource.getOrganisation(sender.getUnitId());
//                response = CasesToResponse.query(unit);
//                break;
//
//            case Request.ActionType.READ).setAttachmentType(Request.ObjectType.STOCK:
//                response = CasesToResponse.queryStock(sender);
//                break;
//
//            case Request.ActionType.READ_ALL).setAttachmentType(Request.ObjectType.ORDER:
//                response = CasesToResponse.queryOrders();
//                break;
//
//            case Request.ActionType.READ).setAttachmentType(Request.ObjectType.NOTIFICATION:
//                response = CasesToResponse.queryNotifications(request);
//                break;
//
//            case Request.ActionType.CREATE:
//                response = CasesToResponse.add(request);
//                break;
//
//            case Request.ActionType.UPDATE:
//                response = CasesToResponse.edit(request);
//                break;
//
//            case Request.ActionType.DELETE:
//                response = CasesToResponse.delete(request);
//                break;
//
//            case Request.ActionType.CLEAN:
//                response = CasesToResponse.cleanDatabase();
//                break;
//        }
        return response;
    }
}