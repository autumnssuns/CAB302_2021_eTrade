package server;

import common.Exceptions.InvalidArgumentValueException;
import server.DataSourceClasses.RequestHandler;

public class TestDatabase {
    public static void main(String[] args) throws InvalidArgumentValueException {
        RequestHandler.initiate();
    }
}