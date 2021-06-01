package server;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;
import server.DataSourceClasses.CasesToResponse;

public class TestDatabase {
    public static void main(String[] args) throws InvalidArgumentValueException {
        CasesToResponse.initiate();
    }
}