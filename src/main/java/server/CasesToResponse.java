package common;

import common.dataClasses.*;
import server.DataSourceClasses.AssetsDataSource;
import server.DataSourceClasses.OrderDataSource;
import server.DataSourceClasses.OrganisationsDataSource;
import server.DataSourceClasses.UserDataSource;

public class CasesToResponse {
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
        userDataSource.close();
        return response;
    }

    //Organisational Unit Type
    public static Response add(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.addOrganisation(attachment);
        Response response = new Response(true, null);
        organisationsDataSource.close();
        return response;
    }
    //Asset Type
    public static Response add(Asset attachment) {
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(attachment);
        Response response = new Response(true, null);
        assetsDataSource.close();
        return response;
    }
    //Order Type
    public static Response add(Order attachment){
            OrderDataSource orderDataSource = new OrderDataSource();
            orderDataSource.addOrder(attachment);
            Response response = new Response(true, null);
            orderDataSource.close();
            return response;
        }
    //Stock Type
    public static Response add(Stock attachment){
        return null;
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
        userDataSource.close();
        return response;
    }
    //Organisational Unit Type
    public static Response edit(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.editOrganisation(attachment);
        Response response = new Response(true, attachment);
        organisationsDataSource.close();
        return response;
    }
    //Asset Type
    public static Response edit(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.editAsset(attachment);
        Response response = new Response(true, attachment);
        assetsDataSource.close();
        return response;
    }
    //Order Type
    public static Response edit(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.editOrder(attachment);
        Response response = new Response(true, attachment);
        orderDataSource.close();
        return response;
    }

    //Stock Type
    public static Response edit(Stock attachment){
        return null;
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
        else if (type.equals(Stock.class)){
            return query((Stock) attachment);
        }
        return null;

    }
    //User Type
    public static Response query(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.getUser(attachment.getUserId());
        Response response = new Response(true, attachment);
        userDataSource.close();
        return response;
    }
    //Organisational Unit Type
    public static Response query(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.getOrganisation(attachment.getId());
        Response response = new Response(true, attachment);
        organisationsDataSource.close();
        return response;
    }
    //Asset Type
    public static Response query(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.getAsset(attachment.getId());
        Response response = new Response(true, attachment);
        assetsDataSource.close();
        return response;
    }
    //Order Type
    public static Response query(Order attachment) {
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.getOrder(attachment.getOrderId());
        Response response = new Response(true, attachment);
        orderDataSource.close();
        return response;
    }
    //Stock Type
    public static Response query(Stock attachment){
        return null;
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
        userDataSource.close();
        return response;
    }

    //Organisational Unit Type
    public static Response delete(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.deleteOrganisation(attachment.getId());
        Response response = new Response(true, null);
        organisationsDataSource.close();
        return response;
    }

    //Asset Type
    public static Response delete(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.deleteAsset(attachment.getId());
        Response response = new Response(true, null);
        assetsDataSource.close();
        return response;
    }

    //Order Type
    public static Response delete(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.deleteOrder(attachment.getOrderId());
        Response response = new Response(true, null);
        orderDataSource.close();
        return response;
    }

    //Stock Type
    public static Response delete(Stock attachment){
        return null;
    }


}
