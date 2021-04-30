package client.data.localDatabase;

import java.util.LinkedHashMap;
import java.util.Set;

public class LocalDatabase extends LinkedHashMap<String, LocalDataTable> {
    public LocalDatabase(){
        addTable("users", new String[]{"username", "password", "organisation", "role"});
        addTable("organisations", new String[]{"id", "name", "credit"});
        addTable("assets", new String[]{"id", "name", "description"});
        addTable("stock", new String[]{"assetId", "organisationId", "quantity"});
    }

    private void addTable(String tableName, String[] attributes){
        LocalDataTable newTable = new LocalDataTable();
        LocalDataTuple headingRow = new LocalDataTuple();
        for (String attribute : attributes){
            headingRow.put(attribute, attribute);
        }
        newTable.put(0, headingRow);
        this.put("users", newTable);
    }

    private <V> void addToTable(String tableName, V[] values){
        LocalDataTuple newTuple = new LocalDataTuple();

        Set<String> keySet = this.get(tableName).get(0).keySet();
        String[] keyArray = keySet.toArray(new String[keySet().size()]);

        for (int i = 0; i < keyArray.length; i++){
            String tempKey = keyArray[i];
            V tempValue = values[i];
            newTuple.put(tempKey, tempValue);
        }
    }
}
