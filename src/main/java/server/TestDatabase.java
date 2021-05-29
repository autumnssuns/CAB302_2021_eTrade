package server;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;

public class TestDatabase {
    public static void main(String[] args) throws InvalidArgumentValueException {
        Stock test = new Stock(1);
        test.add(new Item(new Asset(1,"test", "haha"),100));
        test.add(new Item(new Asset(1,"test", "haha"),100));
        System.out.println(test.size() +" "+ test.get(0).getName() +" "+ test.get(0).getQuantity());
    }
}