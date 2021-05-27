package server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses;

import common.Request;
import common.Response;
import common.dataClasses.*;

public class CasesToResponse {
    //Todo: Add comment / description

    public static Response Login(String userName)
    {
        UserDataSource userdata = new UserDataSource();
        User sender = userdata.getUser(userName);
        Response serverResponse = new Response(true,sender);
        return  serverResponse;
    }
    //Main type of methods to response to request
    // (each type contains classes: Asset, Organisation, Order,
    // stock, transaction(considering) and User)

    //Todo: Overload Add method
    public static <T extends IData> Response add(Request<T> request){
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if(type.equals(User.class)) {
            return add((User) attachment);
        }
        else if(type.equals(OrganisationalUnit.class)){
            return add((OrganisationalUnit) attachment);
        }
        else if (type.equals(Asset.class)){
            return  add((Asset) attachment);
        }
        else if (type.equals(Order.class)){
            return add((Order) attachment);
        }
        else if (type.equals(Stock.class)){
            return add((Stock) attachment);
        }
        return null;
    }

    //User type
    public static Response add(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.addUser(attachment);
        Response response = new Response(true, null);
        return response;
    }

    //Organisational Unit Type
    public static Response add(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.addOrganisation(attachment);
        Response response = new Response(true, null);
        return response;
    }
    //Asset Type
    public static Response add(Asset attachment) {
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(attachment);
        Response response = new Response(true, null);
        return response;
    }
    //Order Type
    public static Response add(Order attachment){
            OrderDataSource orderDataSource = new OrderDataSource();
            orderDataSource.addOrder(attachment);
            Response response = new Response(true, null);
            return response;
        }

    public static Response addAnItem(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.AddAnItem(attachment);
        Response response = new Response(true,null);
        return response;
    }

    /**
     * Add a stock to an org unit
     * @param attachment
     * @return Response object
     */
    public static Response add(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.UpdateUnitStock(attachment);
        Response response = new Response(true, null);
        return response;
    }



    //Todo: Overload Edit method
    public static <T extends IData> Response edit(Request<T> request){
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return edit((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return edit((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return edit((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)){
            return edit((Order) attachment);
        }
        else if (type.equals(Stock.class)){
            return edit((Stock) attachment);
        }
        return null;
    }
    //User Type
    public static Response edit(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.editUser(attachment);
        Response response = new Response(true, attachment);
        return response;
    }
    //Organisational Unit Type
    public static Response edit(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.editOrganisation(attachment);
        Response response = new Response(true, attachment);
        return response;
    }
    //Asset Type
    public static Response edit(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.editAsset(attachment);
        Response response = new Response(true, attachment);
        return response;
    }
    //Order Type
    public static Response edit(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.editOrder(attachment);
        Response response = new Response(true, attachment);
        return response;
    }

    /**
     * Edit quantity of an item in a stock of an org unit
     * @param attachment
     * @return Response object
     */
    public static Response edit(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.EditItemQuantity(attachment);
        Response response = new Response(true, attachment);
        return response;
    }

    //Todo: Overload Query method
    public static <T extends IData> Response query(Request<T> request) {
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return query((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return query((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return query((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)) {
            return query((Order) attachment);
        }

        return null;

    }
    //User Type
    public static Response query(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        attachment = userDataSource.getUser(attachment.getUsername());
        Response response = new Response(true, attachment);
        return response;
    }
    //Organisational Unit Type
    public static Response query(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        attachment = organisationsDataSource.getOrganisation(attachment.getId());
        Response response = new Response(true, attachment);
        return response;
    }
    //Asset Type
    public static Response query(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        attachment = assetsDataSource.getAsset(attachment.getId());
        Response response = new Response(true, attachment);
        return response;
    }
    //Order Type
    public static Response query(Order attachment) {
        OrderDataSource orderDataSource = new OrderDataSource();
        attachment = orderDataSource.getOrder(attachment.getOrderId());
        Response response = new Response(true, attachment);
        return response;
    }


    /**
     * Query all stock from an org unit
     * @param attachment
     * @return Response object
     */
    public static Response queryStock(User attachment){
        StockDataSource stockDataSource = new StockDataSource();
        Stock unitStock = stockDataSource.GetStock(attachment);
        Response response = new Response(true, unitStock);
        return response;
    }


    //Todo: Overload Delete method
    public static <T extends IData> Response delete(Request<T> request) {
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return delete((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return delete((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return delete((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)){
            return delete((Order) attachment);
        }
        else if (type.equals(Stock.class)){
            return delete((Stock) attachment);
        }
        return null;
    }
    //User Type
    public static Response delete(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.deleteUser(attachment.getUserId());
        Response response = new Response(true, null);
        return response;
    }

    //Organisational Unit Type
    public static Response delete(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.deleteOrganisation(attachment.getId());
        Response response = new Response(true, null);
        return response;
    }

    //Asset Type
    public static Response delete(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.deleteAsset(attachment.getId());
        Response response = new Response(true, null);
        return response;
    }

    //Order Type
    public static Response delete(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.deleteOrder(attachment.getOrderId());
        Response response = new Response(true, null);
        return response;
    }

    /**
     * Delete a stock of an org unit
     * @param attachment
     * @return Response object
     */
    public static Response delete(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.DeleteStock(attachment);
        Response response = new Response(true, null);
        return response;
    }

    public static Response deleteAnItem(Request attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.DeleteAnItem((Stock) attachment.getAttachment());
        Response response = new Response(true, null);
        return  response;
    }
}
