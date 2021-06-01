package server;

import common.Exceptions.InvalidArgumentValueException;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.CasesToResponse;

public class TestDatabase {
    public static void main(String[] args) throws InvalidArgumentValueException {
        CasesToResponse.initiate();
    }
}