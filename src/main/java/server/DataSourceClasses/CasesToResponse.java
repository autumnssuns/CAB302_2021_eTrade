package server.DataSourceClasses;

import common.Request;
import common.Response;
import common.dataClasses.*;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

public class CasesToResponse {

    public static void initiate() throws Exception {

        add(new User(1, "Dan Tran", "dan", "123", "user", 0));
        add(new User(2, "Daniel Pham", "duy", "abcd", "user", 1));
        add(new User(3, "Linh Hoang", "lyn", "password", "user", 2));
        add(new User(4, "Rodo Nguyen", "rodo", "rodo", "user", 3));

        add(new Asset(0, "CPU Hours", "CPU for rent"));
        add(new Asset(1, "10 GB Database Server", "Remove SQL Server"));
        add(new Asset(2, "A Generic Video Game", "Nothing is more generic than this."));
        add(new Asset(3, "Coffin Dance Video", "You know what this is"));
        DataCollection<Asset> assets = (DataCollection<Asset>) queryAssets().getAttachment();

        add(new OrganisationalUnit(0, "The Justice League", 10000));
        add(new OrganisationalUnit(1, "The supervillains", 5555.0f));
        add(new OrganisationalUnit(2, "The random civilians", 3000.0f));
        add(new OrganisationalUnit(3, "The brokers", 3000.0f));

        Stock stock0 = new Stock(0);
        Stock stock1 = new Stock(1);
        Stock stock2 = new Stock(2);
        Stock stock3 = new Stock(3);
        stock0.add(new Item(assets.get(0), 99));
        stock0.add(new Item(assets.get(1), 99));
        stock0.add(new Item(assets.get(2), 99));
        stock0.add(new Item(assets.get(3), 99));
        add(stock0);
        stock1.add(new Item(assets.get(0), 99));
        stock1.add(new Item(assets.get(1), 99));
        stock1.add(new Item(assets.get(2), 99));
        stock1.add(new Item(assets.get(3), 99));
        add(stock1);
        stock2.add(new Item(assets.get(0), 10));
        stock2.add(new Item(assets.get(1), 10));
        stock2.add(new Item(assets.get(2), 10));
        stock2.add(new Item(assets.get(3), 10));
        add(stock2);

        }


    //Todo: Add comment / description

    public static Response login(Request request)
    {
        Response serverResponse = new Response(false, null);
        User sender = request.getUser();
        UserDataSource userdata = new UserDataSource();
        User userInData = userdata.getUser(request.getUser().getUsername());
        if(userInData != null)
        {
            if(sender.getUsername().equals(userInData.getUsername())
            && HashPassword.HashPassword(sender.getPassword()).equals(userInData.getPassword()))
            {
                serverResponse = new Response(true,userInData);
            }
        }

        return  serverResponse;
    }
    //Main type of methods to response to request
    // (each type contains classes: Asset, Organisation, Order,
    // stock, transaction(considering) and User)

    //Todo: Overload Add method
    public static <T extends IData> Response add(Request<T> request) throws Exception {
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
    //Todo: implement this
    private static void placeOrder(Order order) throws Exception {
        int unitId = order.getUnitId();
        //if order is SELL: reduce seller stock's item quantity
        if (order.getOrderType().equals(Order.Type.SELL)){
            int assetId = order.getAssetId();
            StockDataSource stockDataSource = new StockDataSource();
            //get units' stock
            Stock unitStock = stockDataSource.getStock(order.getUnitId());
            //Check item in stock
            Item itemInfor;
            for(Item item : unitStock)
            {
                if(item.getId() == assetId)
                {
                    itemInfor = item;
                    unitStock.setAssetId(itemInfor.getId());
                    unitStock.setAssetQuantity(itemInfor.getQuantity() - order.getPlacedQuantity());
                    edit(unitStock);
                    break;
                }
            }

        }
        //if order is BUY: reduce organisation's balance
        else if (order.getOrderType().equals(Order.Type.BUY)){
            OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
            OrganisationalUnit sellerUnit = organisationsDataSource.getOrganisation(order.getUnitId());
            if(sellerUnit != null)
            {
                sellerUnit.setBalance(sellerUnit.getBalance() - order.getPrice() * order.getPlacedQuantity());
                organisationsDataSource.editOrganisation(sellerUnit);
            }
        }
    }
    public static Item findItem(int unitId, int assetId)
    {
        StockDataSource stockDataSource = new StockDataSource();
        Stock unitStock = stockDataSource.getStock(unitId);
        for(Item item : unitStock)
        {
            if(item.getId() == assetId){
                return item;
            }
        }
    return null;
    }

    public static Response add(Order attachment) throws Exception {
            OrderDataSource orderDataSource = new OrderDataSource();
            OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
            //If SELLER: check if the asset quantity is enough.
            if(attachment.getOrderType() == Order.Type.SELL)
            {
                int validQuantity = -1;
                Item item = findItem(attachment.getUnitId(), attachment.getAssetId());
                if(item != null) {
                    validQuantity = item.getQuantity() - attachment.getPlacedQuantity();
                }

                if(validQuantity >= 0)
                {
                    orderDataSource.addOrder(attachment);
                    //Todo: implement this function
                    placeOrder(attachment);
                    reconcileOrder(attachment);
                }
                else {System.out.println("Asset's quantity is not enough!");}
            }

            else if(attachment.getOrderType() == Order.Type.BUY)
            {
                OrganisationalUnit unit = organisationsDataSource.getOrganisation(attachment.getUnitId());
                if(unit.getBalance() > attachment.getPlacedQuantity()*attachment.getPrice())
                {
                    orderDataSource.addOrder(attachment);
                    //Todo: implement this function
                    placeOrder(attachment);
                    reconcileOrder(attachment);
                }
                else {System.out.println("Insufficient balance.");}
            }

            return new Response(true, null);
        }

    public static Response addAnItem(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.addAnItem(attachment);
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
        stockDataSource.updateUnitStock(attachment);
        Response response = new Response(true, null);
        return response;
    }



    //Todo: Overload Edit method
    public static <T extends IData> Response edit(Request<T> request) throws Exception {
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


    //Todo: implement this to use in database.
    public static void cancelOrder(Order order) throws Exception {
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        if (order.getOrderType().equals(Order.Type.BUY)){
            // Refund = Available * Price
            float refundTotal = (order.getPlacedQuantity() - order.getResolvedQuantity()) * order.getPrice();

            // Update the buyer's balance
            OrganisationalUnit orgNew = organisationsDataSource.getOrganisation(order.getUnitId());
            if(orgNew != null) {
                orgNew.setBalance(orgNew.getBalance() + refundTotal);
                organisationsDataSource.editOrganisation(orgNew);
            }
        }
        else{
            int returnQuantity = order.getPlacedQuantity() - order.getResolvedQuantity();
            StockDataSource stockDataSource = new StockDataSource();
            // Update the seller's stock
            Stock unitStock = stockDataSource.getStock(order.getUnitId());
            Item changedItem;
            for(Item item : unitStock)
            {
                if(item.getId() == order.getAssetId())
                {
                    unitStock.setAssetId(item.getId());
                    unitStock.setAssetQuantity(item.getQuantity() + returnQuantity);
                    edit(unitStock);
                    break;
                }

            }
        }
    }
    //Order Type
    public static Response edit(Order attachment) throws Exception {
        //Todo: Cancel order condition
        if (attachment.getStatus().equals(Order.Status.CANCELLED)){
            cancelOrder(attachment);
        }
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
        stockDataSource.editItemQuantity(attachment);
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
        Stock unitStock = stockDataSource.getStock(attachment.getUnitId());
        Response response = new Response(true, unitStock);
        return response;
    }


    public static Response queryStocks()
    {
        StockDataSource stockDataSource = new StockDataSource();
        DataCollection<Stock> stocks = stockDataSource.getStockList();
        Response response = new Response(true, stocks);
        return response;
    }

    public static Response queryOrganisations()
    {
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        DataCollection<OrganisationalUnit> attachment = organisationsDataSource.getOrganisationList();
        Response response = new Response(true, attachment);
        return response;
    }

    public static Response queryOrders()
    {
        OrderDataSource orderDataSource = new OrderDataSource();
        DataCollection<Order> attachment = orderDataSource.getOrderList();
        Response response = new Response(true, attachment);
        return response;
    }

    public  static Response queryAssets() {
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        DataCollection<Asset> attachment = assetsDataSource.getAssetList();
        Response response = new Response(true, attachment);
        return response;
    }

    public static Response queryUsers() {
        UserDataSource userDataSource = new UserDataSource();
        DataCollection<User> attachment = userDataSource.getUserList();
        Response response = new Response(true, attachment);
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
        StockDataSource stockDataSource = new StockDataSource();
        organisationsDataSource.deleteOrganisation(attachment.getId());
        //delete all stock of the organisation if delete the unit
        stockDataSource.deleteStock(stockDataSource.getStock(attachment.getId()));
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
     * Delete an asset in stock table
     * @param attachment
     * @return
     */
    public static Response deleteAnItem(Request attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.deleteAnItem((Stock) attachment.getAttachment());
        Response response = new Response(true, null);
        return  response;
    }

    /**
     * Match two orders on the following conditions:
     * - SELL order with BUY order from different unit
     * - SELL order price less than or equal to BUY order's price
     * @param order
     */
    private static Order matchOrder(Order order)
    {
        OrderDataSource orderDataSource = new OrderDataSource();
        DataCollection<Order> orders = orderDataSource.getOrderList();
     if(order.getStatus().equals(Order.Status.PENDING))
     {
         // Initiate a lowest selling price
         float lowestSellPrice = Float.MAX_VALUE;
         int matchedOrderId = -1;
         // Loop through all the orders
         for(Order transaction : orders){
             // If the order is a BUY, the match must be SELL and vice versa
             Order.Type matchType = order.getOrderType() == Order.Type.BUY ? Order.Type.SELL : Order.Type.BUY;
             float buyPrice = order.getOrderType() == Order.Type.BUY ? order.getPrice() : transaction.getPrice();
             float sellPrice = order.getOrderType() == Order.Type.SELL ? order.getPrice() : transaction.getPrice();

             int transAssetId = transaction.getAssetId();
             Order.Type transType = transaction.getOrderType();
             Order.Status transStatus = transaction.getStatus();
             int transUnitId = transaction.getUnitId();

             // Match condition
             boolean isMatch =
                     transType == matchType                 // Condition: Match type
                     && transStatus == Order.Status.PENDING // Condition: Is pending
                     && transAssetId == order.getAssetId()  // Condition: Same asset
                     && transUnitId != order.getUnitId()    // Condition: Not from same unit
                     && sellPrice <= buyPrice               // Condition: good price
                     ;

             // If a match is found, also check if it is lower than the currently known lowest price
             // If it is lower, it becomes the chosen one
             if (isMatch && sellPrice < lowestSellPrice){
                 lowestSellPrice = sellPrice;
                 matchedOrderId = transaction.getOrderId();
             }
         }

         //return the matched transaction if found, otherwise null.
         return matchedOrderId == -1 ? null : orderDataSource.getOrder(matchedOrderId);

     }
     return null;
    }


    /**
     * Attempt to reconcile an order:
     * - Finds the quantity to reconcile
     * - Increases the resolved quantity of both orders
     * - Marks one of the order as "Approved"
     * - Adds credit to seller
     * - Adds assets to buyer
     */
    private static void reconcileOrder(Order order) throws Exception {
        OrderDataSource orderDataSource = new OrderDataSource();
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        Order matchOrder = matchOrder(order);
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        StockDataSource stockDataSource = new StockDataSource();
        DataCollection<OrganisationalUnit> organisationalUnits = organisationsDataSource.getOrganisationList();

        if (matchOrder != null){
            int orderAvailability = order.getPlacedQuantity() - order.getResolvedQuantity();
            int matchOrderAvailability = matchOrder.getPlacedQuantity() - matchOrder.getResolvedQuantity();
            // Chooses the one with lower availability
            int reconcileQuantity = Math.min(orderAvailability, matchOrderAvailability);
            float price = order.getOrderType() == Order.Type.SELL ? order.getPrice() : matchOrder.getPrice();
            float total = reconcileQuantity * price;

            // Update the resolved quantity of both orders & write edits to database
            order.setResolvedQuantity(order.getResolvedQuantity() + reconcileQuantity);
            matchOrder.setResolvedQuantity(matchOrder.getResolvedQuantity() + reconcileQuantity);
            orderDataSource.editOrder(order);
            orderDataSource.editOrder(matchOrder);

            // Find the seller and buyer IDs
            int sellerId = order.getOrderType() == Order.Type.SELL ? order.getUnitId() : matchOrder.getUnitId();
            int buyerId = order.getOrderType() == Order.Type.BUY ? order.getUnitId() : matchOrder.getUnitId();

            // to update the seller's balance
            for (OrganisationalUnit organisation : organisationalUnits){
                if (organisation.getId() == sellerId){
                    organisation.setBalance( organisation.getBalance() + total );
                    organisationsDataSource.editOrganisation(organisation);
                    break;
                }
            }
            Boolean itemExistence = false; //item exists in the stock
            // and the buyer's stock
            DataCollection<Stock> stocks = stockDataSource.getStockList();
            for (Stock stock : stocks){
                for(Item item : stock){
                    //Check if the unit already have this stock.
                    //If existed: increase the quantity of the item
                    if (stock.getUnitId() == buyerId && item.getId() == order.getAssetId()){
                        Asset asset = assetsDataSource.getAsset(item.getId());
                        Item newItem = new Item (asset,reconcileQuantity);
                        stock.add(newItem);
                        itemExistence = true;
                        stockDataSource.updateUnitStock(stock);
                        break;
                    }
                }
            }
            //If not create new
            if(itemExistence == false)
            {
                for(Stock stock : stocks)
                {
                    if(stock.getUnitId() == buyerId)
                    {
                        stock.add(new Item(assetsDataSource.getAsset(order.getAssetId()),reconcileQuantity));
                        stockDataSource.updateUnitStock(stock);
                        break;
                    }
                }
            }

            // make another attempt to reconcile
            reconcileOrder(order);
        }
    }

}
