package server;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;

public final class MockServer implements IServer{

    boolean firstRun = true;

    public MockServer() throws InvalidArgumentValueException {
        new MockDatabase();
    }

    @Override
    public Response createResponse(Request request) throws InvalidArgumentValueException {
        // Unidentified requests are denied by default
        Response response = new Response(false, null);
        switch (request.getActionType()){
            case TEST:
                if (firstRun){
                    MockDatabase.initiate();
                    firstRun = false;
                    response = new Response(true, null);
                }
                break;

            case LOGIN:
                response = MockDatabase.login(request);
                break;

            case READ_ALL:
                switch (request.getObjectType()){
                    case USER:
                        response = MockDatabase.queryUsers(request);
                        break;

                    case ASSET:
                        response = MockDatabase.queryAssets(request);
                        break;

                    case ORGANISATIONAL_UNIT:
                        response = MockDatabase.queryOrganisations(request);
                        break;

                    case STOCK:
                        response = MockDatabase.queryStocks(request);
                        break;

                    case ORDER:
                        response = MockDatabase.queryOrders(request);
                        break;
                }
                break;

            case READ:
                switch (request.getObjectType()){
                    case ORGANISATIONAL_UNIT:
                        response = MockDatabase.queryOrganisationalUnit(request);
                        break;

                    case STOCK:
                        response = MockDatabase.queryStock(request);
                        break;
                }
                break;


            case CREATE:
                response = MockDatabase.add(request);
                break;

            case UPDATE:
                response = MockDatabase.edit(request);
                break;

            case DELETE:
                response = MockDatabase.delete(request);
                break;
        }
        return response;
    }
}