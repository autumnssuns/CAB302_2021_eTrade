package server;

import client.data.sessionalClasses.Stock;
import common.Request;
import common.Response;
import common.dataClasses.Asset;
import common.dataClasses.Item;
import common.dataClasses.Organisation;
import common.dataClasses.User;

public class MockServer implements IServer{
    MockDatabase mockdb;

    public MockServer(){
        mockdb = new MockDatabase();
    }

    @Override
    public Response sendResponse(Request request) {
        // Unidentified requests are denied by default
        Response response = new Response(false, null);
        switch (request.getAction()){
            case "login":
                String[] attachment = (String[])request.getAttachment();
                String username = attachment[0];
                String password = attachment[1];

                Object[][] conditions = new Object[][]{{0, username},{1, password}};
                MockDatabase.MockDataTable findResult = findInDB(mockdb.USERS, conditions);

                if(!findResult.isEmpty()){
                    Object[] userInDB = findResult.get(0);
                    User user = new User((String)userInDB[0], (String)userInDB[1], (String)userInDB[2], (int)userInDB[3]);
                    response = new Response(true, user);
                }
                break;

            case "query organisation":
                String senderName = request.getSenderName();
                int organisationId = -1;

                conditions = new Object[][]{{0, senderName}};
                findResult = findInDB(mockdb.USERS, conditions);

                if(!findResult.isEmpty()){
                    Object[] userInDB = findResult.get(0);
                    organisationId = (int) userInDB[3];

                    conditions = new Object[][]{{0, organisationId}};
                    findResult = findInDB(mockdb.ORGANISATIONS, conditions);
                    if(!findResult.isEmpty()){
                        Object[] organisationInDB = findResult.get(0);
                        Organisation organisation = new Organisation(organisationId, (String) organisationInDB[1], (float) ((Integer)organisationInDB[2]).intValue());
                        response = new Response(true, organisation);
                    }
                }
                break;

            case "query stock":
                senderName = request.getSenderName();
                organisationId = -1;

                conditions = new Object[][]{{0, senderName}};
                findResult = findInDB(mockdb.USERS, conditions);

                if(!findResult.isEmpty()){
                    Object[] userInDB = findResult.get(0);
                    organisationId = (int) userInDB[3];

                    conditions = new Object[][]{{0, organisationId}};
                    MockDatabase.MockDataTable stockInDB = findInDB(mockdb.STOCK, conditions);
                    Stock stock = new Stock();

                    for (Object[] item : stockInDB){
                        conditions = new Object[][]{{0, item[1]}};
                        Object[] assetInDB = findInDB(mockdb.ASSETS, conditions).get(0);
                        stock.add(new Item( new Asset((int) assetInDB[0], (String) assetInDB[1], (String) assetInDB[2]), (int) item[2]));
                    }

                    if (!stockInDB.isEmpty()){
                        response = new Response(true, stock);
                    }
                }
                break;

            case "delete":
                break;

            case "edit":
                break;
        }
        return response;
    }

    private MockDatabase.MockDataTable findInDB(MockDatabase.MockDataTable table, Object[][] conditions){
        MockDatabase.MockDataTable returnTable = new MockDatabase.MockDataTable();
        for (Object[] tuple : table){
            boolean match = true;
            for (Object[] condition : conditions){
                int index = ((Integer)condition[0]).intValue();
                String tupleColumnString = tuple[index].toString();
                String matchingString = condition[1].toString();
                match = match & tupleColumnString.equals(matchingString);
            }
            if (match){
                returnTable.add(tuple);
            }
        }
        return returnTable;
    }
}