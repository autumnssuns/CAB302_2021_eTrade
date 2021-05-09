package server;

import common.Request;
import common.Response;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.Stock;

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

            case "query organisationalUnits":
                response = mockdb.queryOrganisations(request);
                break;

            case "query stocks":
                response = mockdb.queryStocks(request);
                break;

            case "query organisational unit":
                response = mockdb.queryOrganisationalUnit(request);
                break;

            case "query stock":
                response = mockdb.queryStock(request);
                break;

            case "query orders":
                response = mockdb.queryOrders(request);
                break;
        }
        return response;
    }
}