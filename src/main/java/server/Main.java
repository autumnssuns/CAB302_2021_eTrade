package server;

import common.Exceptions.InvalidArgumentValueException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidArgumentValueException, IOException {
        Server server = new Server();
        server.Start();
    }
}
