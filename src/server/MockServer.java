package server;

import common.dataClasses.Stock;
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
                response = mockdb.login(request);
                break;

            case "query users":
                response = mockdb.queryUsers(request);
                break;

            case "query assets":
                response = mockdb.queryAssets(request);
                break;

            case "query organisations":
                response = mockdb.queryOrganisations(request);
                break;

            case "query stocks":
                response = mockdb.queryStocks(request);
                break;
        }
        return response;
    }
}