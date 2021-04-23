package client.data.localDatabase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class LocalDatabase extends LinkedHashMap<String, LocalDataTable> {
    public LocalDatabase(){
        addTable("users", new String[]{"username", "password", "organisation", "role"});
    }

    private void addTable(String tableName, String[] attributes){
        LocalDataTable newTable = new LocalDataTable();
        LocalDataTuple headingRow = new LocalDataTuple();
        for (String attribute : attributes){
            headingRow.put(attribute, attribute);
        }
        newTable.put(0, headingRow);
        this.put("users", new LocalDataTable());
    }

    private <V> void addToTable(String tableName, V[] values){
        LocalDataTuple newTuple = new LocalDataTuple();
        Set<String> keys = this.get(tableName).get(0).keySet();

    }
}
