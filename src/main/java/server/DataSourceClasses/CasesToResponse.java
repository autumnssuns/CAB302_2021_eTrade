package server.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class CasesToResponse  {

    /**
     * Initiate a mini database (for testing purpose)
     * @throws InvalidArgumentValueException
     */
    public static void initiate() throws InvalidArgumentValueException {
        try {
            add(new User(0, "Admin", "admin", "root", "admin", null).hashPassword());
            add(new User(1, "Dan Tran", "dan", "123", "user", 0).hashPassword());
            add(new User(2, "Daniel Pham", "duy", "abcd", "user", 1).hashPassword());
            add(new User(3, "Linh Hoang", "lyn", "password", "user", 2).hashPassword());
            add(new User(4, "Rodo Nguyen", "rodo", "rodo", "user", 3).hashPassword());

            add(new Asset(0, "CPU Hours", "CPU for rent"));
            add(new Asset(1, "10 GB Database Server", "Remove SQL Server"));
            add(new Asset(2, "A Generic Video Game", "Nothing is more generic than this."));
            add(new Asset(3, "Coffin Dance Video", "You know what this is"));

            DataCollection<Asset> assets = (DataCollection<Asset>) queryAssets().getAttachment();

            add(new OrganisationalUnit(0, "The Justice League", 9999.0f));
            add(new OrganisationalUnit(1, "The supervillains", 5555.0f));
            add(new OrganisationalUnit(2, "The random civilians", 3000.0f));
            add(new OrganisationalUnit(3, "The brokers", 3000.0f));

            Stock stock0 = new Stock(0);
            Stock stock1 = new Stock(1);
            Stock stock2 = new Stock(2);
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

            add(new Order(0, Order.Type.SELL, 0, 0, 99, 0, 10f, null, LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING));
            add(new Order(1, Order.Type.SELL, 0, 1, 99, 0, 3f, null, LocalDateTime.of(2021, 5, 6, 13, 42), Order.Status.PENDING));
            add(new Order(2, Order.Type.SELL, 0, 2, 99, 0, 4f, null, LocalDateTime.of(2021, 5, 6, 7, 45), Order.Status.PENDING));
            add(new Order(3, Order.Type.SELL, 0, 3, 99, 0, 5f, null, LocalDateTime.of(2021, 5, 6, 22, 00), Order.Status.PENDING));
            add(new Order(4, Order.Type.SELL, 1, 0, 55, 0, 8f, null, LocalDateTime.of(2021, 5, 7, 21, 52), Order.Status.PENDING));
            add(new Order(5, Order.Type.SELL, 1, 1, 55, 0, 7f, null, LocalDateTime.of(2021, 5, 7, 15, 26), Order.Status.PENDING));
            add(new Order(6, Order.Type.SELL, 1, 2, 55, 0, 8f, null, LocalDateTime.of(2021, 5, 7, 18, 28), Order.Status.PENDING));
            add(new Order(7, Order.Type.SELL, 1, 3, 50, 0, 9f, null, LocalDateTime.of(2021, 5, 7, 13, 36), Order.Status.PENDING));
            add(new Order(8, Order.Type.BUY, 2, 0, 40, 0, 10f, null, LocalDateTime.of(2021, 5, 8, 14, 45), Order.Status.PENDING));
            add(new Order(9, Order.Type.BUY, 2, 1, 40, 0, 10.5f, null, LocalDateTime.of(2021, 5, 8, 11, 14), Order.Status.PENDING));
            add(new Order(10, Order.Type.BUY, 2, 2, 40, 0, 11.5f, null, LocalDateTime.of(2021, 5, 8, 7, 15), Order.Status.PENDING));
            add(new Order(11, Order.Type.BUY, 2, 3, 40, 0, 12.5f, null, LocalDateTime.of(2021, 5, 8, 4, 20), Order.Status.PENDING));
            add(new Order(12, Order.Type.BUY, 3, 0, 50, 0, 13.5f, null, LocalDateTime.of(2021, 5, 9, 6, 21), Order.Status.PENDING));
            add(new Order(13, Order.Type.BUY, 3, 1, 50, 0, 12.5f, null, LocalDateTime.of(2021, 5, 9, 8, 30), Order.Status.PENDING));
            add(new Order(14, Order.Type.BUY, 3, 2, 50, 0, 14.5f, null, LocalDateTime.of(2021, 5, 9, 0, 11), Order.Status.PENDING));
            add(new Order(15, Order.Type.BUY, 3, 3, 50, 0, 15.5f, null, LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));

        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }

    }

    /**
     * Empties the database
     * @return a Response object
     */
    public static Response<IData> cleanDatabase(){
        NotificationDataSource notificationData = new NotificationDataSource();
        AssetsDataSource assetData = new AssetsDataSource();
        UserDataSource userData = new UserDataSource();
        OrderDataSource orderData = new OrderDataSource();
        StockDataSource stockData = new StockDataSource();
        OrganisationsDataSource organisationalUnitData = new OrganisationsDataSource();
        notificationData.deleteAll();
        stockData.deleteAll();
        orderData.deleteAll();
        userData.deleteAll();
        assetData.deleteAll();
        organisationalUnitData.deleteAll();
        return new Response<>(true, null);
    }

    /**
     * Process login Request
     * @param request a Request object
     * @return a Response object
     */
    public static Response login(Request request)
    {
        Response<User> serverResponse = new Response<>(false, null);
        User sender = request.getUser();
        UserDataSource userdata = new UserDataSource();
        User userInData = userdata.getUser(sender.getUsername());
        if(userInData != null)
        {
            if(sender.getUsername().equals(userInData.getUsername())
                    && sender.getPassword().equals(userInData.getPassword()))
            {
                serverResponse = new Response<>(true,userInData);
            }
        }
        return serverResponse;
    }

    //ServerMain type of methods to response to request
    // (each type contains classes: Asset, Organisation, Order,
    // stock, transaction(considering) and UserGUI)

    //Todo: Overload Add method
    /**
     * Classify what kind of request and process accordingly
     * @param request Request
     * @param <T> A type of IData
     * @return a Response object or null
     * @throws InvalidArgumentValueException
     */
    public static <T extends IData> Response<IData> add(Request<T> request) throws InvalidArgumentValueException {
        T attachment = request.getAttachment();
        Request.ObjectType type = request.getObjectType();
        if(type.equals(Request.ObjectType.USER)) {
            return add((User) attachment);
        }
        else if(type.equals(Request.ObjectType.ORGANISATIONAL_UNIT)){
            return add((OrganisationalUnit) attachment);
        }
        else if (type.equals(Request.ObjectType.ASSET)){
            return  add((Asset) attachment);
        }
        else if (type.equals(Request.ObjectType.ORDER)){
            return add((Order) attachment);
        }
        else if (type.equals(Request.ObjectType.STOCK)){
            return add((Stock) attachment);
        }
        return null;
    }

    /**
     * Process add user request
     * @param attachment a User object
     * @return a Response object
     */
    public static Response add(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.addUser(attachment);
        return new Response<>(true, null);
    }

    /**
     * Process add OrganisationUnit request
     * @param attachment a OrganisationUnit object
     * @return a Response object
     */
    public static Response add(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.addOrganisation(attachment);
        return new Response<>(true, null);
    }
    //Asset Type
    public static Response<IData> add(Asset attachment) {
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(attachment);
        return new Response<>(true, null);
    }

    /**
     * Process when a user place an Order: Reduce their stock when sell or reduce their balance when buy
     * @param order an Order object
     * @throws InvalidArgumentValueException
     */
    private static void placeOrder(Order order) throws InvalidArgumentValueException {
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
                    item.setQuantity(itemInfor.getQuantity() - order.getPlacedQuantity());
                    stockDataSource.editStock(unitStock);
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


    /**
     * Get an asset information from an organisation
     * @param unitId
     * @param assetId
     * @return an Item object or null
     */
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

    /**
     * Process add Order
     * @param attachment an Order object
     * @return a Response object
     * @throws InvalidArgumentValueException
     */
    public static Response<IData> add(Order attachment) throws InvalidArgumentValueException {
        OrderDataSource orderDataSource = new OrderDataSource();
        attachment.setOrderId(orderDataSource.getNextId());
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
                //add order into database
                orderDataSource.addOrder(attachment);
                //Analyse the orders' information with other existed orders on database then update.
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
                //add order into the database
                orderDataSource.addOrder(attachment);
                //analyse the orders' information with other existed orders then update
                placeOrder(attachment);
                reconcileOrder(attachment);
            }
            else {System.out.println("Insufficient balance.");}
        }

        return new Response<>(true, null);
    }

    /**
     * Add a stock to an org unit
     * @param attachment
     * @return a Response object
     */
    public static Response<IData> add(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.editStock(attachment);
        return new Response<>(true, null);
    }

    /**
     * Identity what class to edit and process the request
     * @param request A Request object
     * @param <T> a type of IData
     * @return a Response object or null
     * @throws InvalidArgumentValueException
     */
    public static <T extends IData> Response<IData> edit(Request<T> request) throws InvalidArgumentValueException {
        // If a request is not valid, it is denied immediately
        if (!isValid(request)){
            return new Response(false, null);
        }

        T attachment = request.getAttachment();
        Request.ObjectType type = request.getObjectType();
        if (type.equals(Request.ObjectType.USER)){
            return edit((User) attachment);
        }
        else if (type.equals(Request.ObjectType.ASSET)){
            return edit((Asset) attachment);
        }
        else if (type.equals(Request.ObjectType.ORGANISATIONAL_UNIT)){
            return edit((OrganisationalUnit) attachment);
        }
        else if (type.equals(Request.ObjectType.ORDER)){
            return edit((Order) attachment);
        }
        else if (type.equals(Request.ObjectType.STOCK)){
            return edit((Stock) attachment);
        }
        else if (type.equals(Request.ObjectType.NOTIFICATION)){
            return edit((DataCollection<Notification>) attachment, request.getUser());
        }
        return null;
    }

    /**
     * Edit a User
     * @param attachment
     * @return a Response object
     */
    public static Response<IData> edit(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.editUser(attachment);
        return new Response<>(true, attachment);
    }

    /**
     * Edit an OrganisationUnit
     * @param attachment an OrganisationUnit object
     * @return a Response object
     */
    public static Response<IData> edit(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.editOrganisation(attachment);
        return new Response<>(true, attachment);
    }

    /**
     * Edit an Asset
     * @param attachment an Asset object
     * @return a Response object
     */
    public static Response<IData> edit(Asset attachment){

        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.editAsset(attachment);
        return new Response<>(true, attachment);
    }

    /**
     * Edit quantity of an item in a stock of an org unit
     * @param attachment a Stock object
     * @return Response object
     */
    public static Response<IData> edit(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.editStock(attachment);
        return new Response<>(true, attachment);
    }

    //Order Type
    public static Response<IData> edit(Order attachment) throws InvalidArgumentValueException {
        //if orders' status is CANCELLED, need to do some refunds to clients
        if (attachment.getStatus().equals(Order.Status.CANCELLED)){
            cancelOrder(attachment);
        }
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.editOrder(attachment);
        return new Response<>(true, attachment);
    }

    /**
     * Edit multiple notifications in the database
     * @param notifications The overriding notification
     * @return The response containing the state if the request was fulfilled, as well as an attachment (after change)
     */
    public static Response edit(DataCollection<Notification> notifications, User sender){
        NotificationDataSource notificationDataSource = new NotificationDataSource();
        for (Notification notification : notifications){
            notificationDataSource.edit(notification);
        }
        DataCollection<Notification> returningNotifications = notificationDataSource.getFromUnitId(sender.getUnitId());
        return new Response<>(true, returningNotifications);
    }

    //Todo: implement this to use in database.
    /**
     * Cancel an Order
     * @param order
     * @throws InvalidArgumentValueException
     */
    public static void cancelOrder(Order order) throws InvalidArgumentValueException {
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
            for(Item item : unitStock)
            {
                if(item.getId().equals(order.getAssetId()))
                {
                    item.setQuantity(item.getQuantity() + returnQuantity);
                    stockDataSource.editStock(unitStock);
                    break;
                }
            }
        }
    }

    /**
     * Checks the request's previous state with the current state in the server's database if it is valid.
     * A request is valid only if its previous state matches the state in the server's database.
     * This is implemented to handle two admins updating the same data, the latter must reload before they can commit
     * their changes.
     * @param request The request containing the object's to check
     * @param <T> The type of the attachment
     * @return true if the request's previous state is valid with the current state of that object in the server's database.
     */
    public static <T extends IData> boolean isValid(Request<T> request){
        T serverCurrentState = (T) query(request).getAttachment();
        return request.getPreviousObjectState() == null || request.getPreviousObjectState().equals(serverCurrentState);
    }

    //Todo: Overload Query method
    /**
     * Classify the IData type and process that query according to its type
     * @param request
     * @param <T>
     * @return a Response object or null
     */
    public static <T extends IData> Response<IData> query(Request<T> request) {
        T attachment = request.getAttachment();
        Request.ObjectType type = request.getObjectType();
        switch (type){
            case USER:
                return query((User) attachment);
            case ASSET:
                return query((Asset) attachment);
            case ORGANISATIONAL_UNIT:
                return queryOrganisationalUnit(request.getUser());
            case ORDER:
                return query((Order) attachment);
            case STOCK:
                return queryStock(request.getUser());
            case NOTIFICATION:
                return queryNotifications(request);
        }
        return null;
    }

    /**
     * Query a user row
     * @param attachment a User object
     * @return a Response object
     */
    public static Response<IData> query(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        attachment = userDataSource.getUser(attachment.getUsername());
        return new Response<>(true, attachment);
    }

    /**
     * Query an organisation unit row based on the current user
     * @param sender an Organisation object
     * @return a Response object
     */
    public static Response<IData> queryOrganisationalUnit(User sender){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        OrganisationalUnit attachment = organisationsDataSource.getOrganisation(sender.getUnitId());
        return new Response<>(true, attachment);
    }

    /**
     * Query an asset row
     * @param attachment an Asset object
     * @return a Response object
     */
    public static Response<IData> query(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        attachment = assetsDataSource.getAsset(attachment.getId());
        return new Response<>(true, attachment);
    }

    /**
     * Query an order row
     * @param attachment object
     * @return a Response object
     */
    public static Response<IData> query(Order attachment) {
        OrderDataSource orderDataSource = new OrderDataSource();
        attachment = orderDataSource.getOrder(attachment.getOrderId());
        return new Response<>(true, attachment);
    }

    /**
     * Query all stock from an org unit
     * @param attachment a User object
     * @return a Response object
     */
    public static Response<IData> queryStock(User attachment){
        Integer unitId = attachment.getUnitId();
        StockDataSource stockDataSource = new StockDataSource();
        Stock unitStock = stockDataSource.getStock(unitId);
        return new Response<>(true, unitStock);
    }

    /**
     * Query all stocks
     * @return a Response object
     */
    public static Response<DataCollection<Stock>> queryStocks()
    {
        StockDataSource stockDataSource = new StockDataSource();
        DataCollection<Stock> stocks = stockDataSource.getStockList();
        return new Response<>(true, stocks);
    }

    /**
     * Query all organisations
     * @return a Response object
     */
    public static Response<DataCollection<OrganisationalUnit>> queryOrganisations()
    {
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        DataCollection<OrganisationalUnit> attachment = organisationsDataSource.getOrganisationList();
        return new Response<>(true, attachment);
    }

    /**
     * Query all orders
     * @return a Response object
     */
    public static Response<DataCollection<Order>> queryOrders()
    {
        OrderDataSource orderDataSource = new OrderDataSource();
        DataCollection<Order> attachment = orderDataSource.getOrderList();
        return new Response<>(true, attachment);
    }

    /**
     * Query all assets
     * @return a Response object
     */
    public  static Response<DataCollection<Asset>> queryAssets() {
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        DataCollection<Asset> attachment = assetsDataSource.getAssetList();
        return new Response<>(true, attachment);
    }

    /**
     * Query all users
     * @return a Response object
     */
    public static Response<DataCollection<User>> queryUsers() {
        UserDataSource userDataSource = new UserDataSource();
        DataCollection<User> attachment = userDataSource.getUserList();
        return new Response<>(true, attachment);
    }

    /**
     * Creates a response for a request querying the notifications for a unit
     * @return A response containing the notifications for an organisational unit
     */
    public static Response<IData> queryNotifications(Request request){
        Integer unitId = request.getUser().getUnitId();
        NotificationDataSource notificationDataSource = new NotificationDataSource();
        DataCollection<Notification> attachment = notificationDataSource.getFromUnitId(unitId);
        return new Response(true, attachment);
    }

    /**
     * Identify the type of data and delete a row
     * @param request
     * @param <T>
     * @return a Response object or null
     */
    public static <T extends IData> Response<IData> delete(Request<T> request) {
        // If a request is not valid, it is denied immediately
        if (!isValid(request)){
            return new Response(false, null);
        }

        T attachment = request.getAttachment();
        Request.ObjectType type = request.getObjectType();
        if (type.equals(Request.ObjectType.USER)){
            return delete((User) attachment);
        }
        else if (type.equals(Request.ObjectType.ASSET)){
            return delete((Asset) attachment);
        }
        else if (type.equals(Request.ObjectType.ORGANISATIONAL_UNIT)){
            return delete((OrganisationalUnit) attachment);
        }
        else if (type.equals(Request.ObjectType.ORDER)){
            return delete((Order) attachment);
        }

        return null;
    }

    /**
     * Delete a user row
     * @param attachment
     * @return a Response object
     */
    public static Response<IData> delete(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.deleteUser(attachment.getId());
        return new Response<>(true, null);
    }

    /**
     * Delete an organisation row
     * @param attachment
     * @return a Response object
     */
    public static Response<IData> delete(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        StockDataSource stockDataSource = new StockDataSource();
        organisationsDataSource.deleteOrganisation(attachment.getId());
        //delete all stock of the organisation if delete the unit
        stockDataSource.deleteStock(stockDataSource.getStock(attachment.getId()));
        return new Response<>(true, null);
    }

    /**
     * Delete an asset row
     * @param attachment
     * @return a Response object
     */
    public static Response<IData> delete(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.deleteAsset(attachment.getId());
        return new Response<>(true, null);
    }

    /**
     * Delete an order row
     * @param attachment
     * @return a Response object
     */
    public static Response<IData> delete(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.deleteOrder(attachment.getOrderId());
        return new Response<>(true, null);
    }

    /**
     * Match two orders on the following conditions:
     * - SELL order with BUY order from different unit
     * - SELL order price less than or equal to BUY order's price
     * @param order ORDER needed to compare
     * @return an Order object or null if the order is fully filled
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
                        transType == matchType                          // Condition: Match type
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
     * @param order an Order object
     */
    private static void reconcileOrder(Order order) throws InvalidArgumentValueException {
        //Prepare data sources
        OrderDataSource orderDataSource = new OrderDataSource();
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        StockDataSource stockDataSource = new StockDataSource();

        Order matchOrder = matchOrder(order);
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
            boolean itemExistence = false; //item exists in the stock
            // and the buyer's stock
            DataCollection<Stock> stocks = stockDataSource.getStockList();
            for (Stock stock : stocks){
                for(Item item : stock){
                    //Check if the unit already have this stock.
                    //If existed: increase the quantity of the item
                    if (stock.getUnitId() == buyerId && item.getId().equals(order.getAssetId())){
                        Asset asset = assetsDataSource.getAsset(item.getId());
                        Item newItem = new Item (asset,reconcileQuantity);
                        stock.add(newItem);
                        itemExistence = true;
                        stockDataSource.editStock(stock);
                        break;
                    }
                }
            }
            //If not create new
            if(!itemExistence)
            {
                for(Stock stock : stocks)
                {
                    if(stock.getUnitId() == buyerId)
                    {
                        stock.add(new Item(assetsDataSource.getAsset(order.getAssetId()),reconcileQuantity));
                        stockDataSource.editStock(stock);
                        break;
                    }
                }
            }

            // Push the notifications to the associated organisational units
            // based off the orders
            pushNotification(order);
            pushNotification(matchOrder);

            // make another attempt to reconcile
            reconcileOrder(order);
        }
    }

    /**
     * Pushes a notification for an organisational unit,
     * based on the order triggering the notification
     */
    private static void pushNotification(Order order){
        // Link the order with an asset
        NotificationDataSource notificationDataSource = new NotificationDataSource();
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        Asset linkedAsset = assetsDataSource.getAsset(order.getAssetId());
        // Only push when the order is reconciled
        if (order.getStatus().equals(Order.Status.COMPLETED)){
            String message = "Your order #" + order.getOrderId()
                    + " to " + order.getOrderType()
                    + " " + order.getPlacedQuantity()
                    + " " + linkedAsset.getName()
                    + " has been resolved.";
            try {
                Notification notification = new Notification()
                        .setMessage(message)
                        .addReceiverUnit(order.getUnitId());
                notificationDataSource.add(notification);
            } catch (InvalidArgumentValueException e) {
                e.printStackTrace();
            }
        }
    }
}