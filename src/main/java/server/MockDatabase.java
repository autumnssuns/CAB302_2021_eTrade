package server;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public final class MockDatabase {
    static ArrayList<Object[]> users = new ArrayList();

    static ArrayList<Object[]> assets = new ArrayList();

    static ArrayList<Object[]> organisationalUnits = new ArrayList();

    static ArrayList<Object[]> stocks = new ArrayList();

    static ArrayList<Object[]> orders = new ArrayList();

    public static void initiate(){
        users.add(new Object[]{0, "Admin", "admin", "root", "admin", 0});
        users.add(new Object[]{1, "Dan Tran", "dan", "123", "user", 0});
        users.add(new Object[]{2, "Daniel Pham", "duy", "abcd", "user", 1});
        users.add(new Object[]{3, "Linh Hoang", "lyn", "password", "user", 2});
        users.add(new Object[]{4, "Rodo Nguyen", "rodo", "rodo", "user", 3});

        assets.add(new Object[]{0, "CPU Hours", "CPU for rent"});
        assets.add(new Object[]{1, "10 GB Database Server", "Remove SQL Server"});
        assets.add(new Object[]{2, "A Generic Video Game", "Nothing is more generic than this."});
        assets.add(new Object[]{3, "Coffin Dance Video", "You know what this is"});

        organisationalUnits.add(new Object[]{0, "The Justice League", 9999.0f});
        organisationalUnits.add(new Object[]{1, "The supervillains", 5555.0f});
        organisationalUnits.add(new Object[]{2, "The random civilians", 500.0f});
        organisationalUnits.add(new Object[]{3, "The brokers", 200.0f});

        stocks.add(new Object[]{0, 0, 99});
        stocks.add(new Object[]{0, 1, 99});
        stocks.add(new Object[]{0, 2, 99});
        stocks.add(new Object[]{0, 3, 99});
        stocks.add(new Object[]{1, 0, 55});
        stocks.add(new Object[]{1, 1, 55});
        stocks.add(new Object[]{1, 2, 55});
        stocks.add(new Object[]{1, 3, 55});
        stocks.add(new Object[]{2, 0, 10});
        stocks.add(new Object[]{2, 1, 10});
        stocks.add(new Object[]{2, 2, 10});
        stocks.add(new Object[]{2, 3, 10});

        orders.add(new Object[]{0, Order.Type.BUY, 0, 0, 23, 0, 22f, null, LocalDateTime.of(2021, 5, 15, 16, 52), Order.Status.PENDING});
        orders.add(new Object[]{1, Order.Type.BUY, 0, 1, 32, 0, 3f, null, LocalDateTime.of(2021, 5, 13, 13, 42), Order.Status.PENDING});
        orders.add(new Object[]{2, Order.Type.BUY, 0, 2, 45, 0, 4f, null, LocalDateTime.of(2021, 5, 14, 7, 45), Order.Status.PENDING});
        orders.add(new Object[]{3, Order.Type.BUY, 0, 3, 36, 0, 5f, null, LocalDateTime.of(2021, 5, 12, 22, 00), Order.Status.PENDING});
        orders.add(new Object[]{4, Order.Type.BUY, 1, 0, 74, 0, 6f, null, LocalDateTime.of(2021, 5, 10, 21, 52), Order.Status.PENDING});
        orders.add(new Object[]{5, Order.Type.BUY, 1, 1, 32, 0, 7f, null, LocalDateTime.of(2021, 5, 9, 15, 26), Order.Status.PENDING});
        orders.add(new Object[]{6, Order.Type.BUY, 1, 2, 45, 0, 8f, null, LocalDateTime.of(2021, 5, 12, 18, 28), Order.Status.PENDING});
        orders.add(new Object[]{7, Order.Type.BUY, 1, 3, 64, 0, 9f, null, LocalDateTime.of(2021, 5, 9, 13, 36), Order.Status.PENDING});
        orders.add(new Object[]{8, Order.Type.SELL, 2, 0, 76, 0, 10f, null, LocalDateTime.of(2021, 5, 13, 14, 45), Order.Status.PENDING});
        orders.add(new Object[]{9, Order.Type.SELL, 2, 1, 86, 0, 10.5f, null, LocalDateTime.of(2021, 5, 5, 11, 14), Order.Status.PENDING});
        orders.add(new Object[]{10, Order.Type.SELL, 2, 2, 42, 0, 11.5f, null, LocalDateTime.of(2021, 5, 2, 7, 15), Order.Status.PENDING});
        orders.add(new Object[]{11, Order.Type.SELL, 2, 3, 43, 0, 12.5f, null, LocalDateTime.of(2021, 5, 6, 4, 20), Order.Status.PENDING});
        orders.add(new Object[]{12, Order.Type.SELL, 3, 0, 56, 0, 13.5f, null, LocalDateTime.of(2021, 5, 8, 6, 21), Order.Status.PENDING});
        orders.add(new Object[]{13, Order.Type.SELL, 3, 1, 54, 0, 12.5f, null, LocalDateTime.of(2021, 5, 4, 8, 30), Order.Status.PENDING});
        orders.add(new Object[]{14, Order.Type.SELL, 3, 2, 37, 0, 14.5f, null, LocalDateTime.of(2021, 5, 11, 0, 11), Order.Status.PENDING});
        orders.add(new Object[]{15, Order.Type.SELL, 3, 3, 82, 0, 15.5f, null, LocalDateTime.of(2021, 5, 13, 3, 42), Order.Status.PENDING});
//        protected int orderId;
//        protected String orderType; //buy/sell
//        protected int unitId;
//        protected int assetId;
//        protected int placedQuantity;//cartitem
//        protected int resolvedQuantity = 0;
//        protected float price;
//        protected String finishDate = null; //Cartitem
//        protected String orderDate;
//        protected String status;
    }

    public static Response login(Request request){
        User sender = request.getUser();
        for (Object[] user : users){
            if (user[2].equals(sender.getUsername()) && user[3].equals(sender.getPassword())){
                return new Response(true, new User((int) user[0], (String)user[1], (String) user[2], (String) user[3], (String) user[4], (int) user[5]));
            }
        }
        return new Response(false, null);
    }

    public static Response queryUsers(Request request) {
        DataCollection<User> attachedUsers = new DataCollection<>();
        for (Object[] user : users){
            attachedUsers.add(new User((int) user[0], (String) user[1], (String) user[2], (String) user[3], (String) user[4], (int) user[5]));
        }
        return new Response(true, attachedUsers);
    }

    public static Response queryAssets(Request request) throws InvalidArgumentValueException {
        DataCollection<Asset> attachedAssets = new DataCollection<>();
        for (int i = 0; i < assets.size(); i++){
            Object[] asset = assets.get(i);
            attachedAssets.add(new Asset((int) asset[0], (String) asset[1], (String) asset[2]));
        }
        return new Response(true, attachedAssets);
    }

    public static Response queryOrganisations(Request request) {
        DataCollection<OrganisationalUnit> attachedOrganisationalUnit = new DataCollection<>();
        for (Object[] organisationalUnit : organisationalUnits){
            attachedOrganisationalUnit.add(new OrganisationalUnit((int) organisationalUnit[0], (String) organisationalUnit[1], (float) organisationalUnit[2]));
        }
        return new Response(true, attachedOrganisationalUnit);
    }

    public static Response queryStocks(Request request) throws InvalidArgumentValueException {
        DataCollection<Stock> attachedStocks = new DataCollection<>();
        for (Object[] organisationalUnit : organisationalUnits){
            attachedStocks.add(new Stock((int) organisationalUnit[0]));
        }
        for (Object[] organisationAsset : stocks){
            Object[] asset = assets.get((int) organisationAsset[1]);
            Asset newAsset = new Asset((int) asset[0], (String) asset[1], (String) asset[2]);
            attachedStocks.get((int) organisationAsset[0]).add(new Item(newAsset, (int) organisationAsset[2]));
        }
        return new Response(true, attachedStocks);
    }

    public static Response queryOrganisationalUnit(Request request) {
        int unitId = request.getUser().getUnitId();
        Object[] matchedUnit = organisationalUnits.get(unitId);
        OrganisationalUnit attachedUnit = new OrganisationalUnit((int) matchedUnit[0], (String) matchedUnit[1], (float) matchedUnit[2]);
        return new Response(true, attachedUnit);
    }

    public static Response queryStock(Request request) throws InvalidArgumentValueException {
        int unitId = request.getUser().getUnitId();
        Stock returnStock = new Stock(unitId);
        for (Object[] organisationalItem : stocks){
            if (organisationalItem[0].equals(unitId)){
                int assetId = (int) organisationalItem[1];
                for (Object[] asset : assets){
                    if (asset[0].equals(assetId)){
                        Asset newAsset = new Asset(assetId, (String) asset[1], (String) asset[2]);
                        Item newItem = new Item(newAsset, (int) organisationalItem[2]);
                        returnStock.add(newItem);
                    }
                }
            }
        }
        return new Response(true, returnStock);
    }

    public static Response queryOrders(Request request) {
        DataCollection<Order> returnOrders = new DataCollection<>();
        for (Object[] row : orders){
            Order newOrder = new Order((int) row[0], (Order.Type) row[1], (int) row[2], (int) row[3], (int) row[4],
                    (int) row[5], (float) row[6], (LocalDateTime) row[7], (LocalDateTime) row[8], (Order.Status) row[9]);
            returnOrders.add(newOrder);
        }
        return new Response(true, returnOrders);
    }

    /**
     * Find a user in the database, identified by ID.
     * @param user The user to find
     * @return The index of the user in the database.
     */
    private static int find(User user){
        int key = user.getUserId();
        Object[] match = new Object[]{};
        for (Object[] row : users){
            if (row[0].equals(key)){
                match = row;
                break;
            }
        }
        return users.indexOf(match);
    }

    /**
     * Find an asset in the database, identified by ID.
     * @param asset The user to find
     * @return The index of the asset in the database.
     */
    private static int find(Asset asset){
        int key = asset.getId();
        Object[] match = new Object[]{};
        for (Object[] row : assets){
            int rowKey = (int) row[0];
            if (rowKey == key){
                match = row;
                break;
            }
        }
        return assets.indexOf(match);
    }

    /**
     * Find an organisational unit in the database, identified by ID.
     * @param unit The organisational unit to find
     * @return The index of the unit in the database.
     */
    private static int find(OrganisationalUnit unit){
        int key = unit.getId();
        Object[] match = new Object[]{};
        for (Object[] row : organisationalUnits){
            if (row[0].equals(key)){
                match = row;
                break;
            }
        }
        return organisationalUnits.indexOf(match);
    }

    private static int find(Order order){
        int key = order.getOrderId();
        Object[] match = new Object[]{};
        for (Object[] row : orders){
            if (row[0].equals(key)){
                match = row;
                break;
            }
        }
        return orders.indexOf(match);
    }

    /**
     * Find a certain organisational unit - itemId relation in the stocks database, identified by IDs
     * @param organisationalUnitId The ID of the organisational unit
     * @param itemId The itemId to find
     * @return The index of the organisational unit - itemId relation.
     */
    private static int find(int organisationalUnitId, int itemId){
        int key = organisationalUnitId;
        int key2 = itemId;
        Object[] match = new Object[]{};
        for (Object[] row : stocks){
            if (row[0].equals(key) && row[1].equals(key2)){
                match = row;
                break;
            }
        }
        return stocks.indexOf(match);
    }

    /**
     * Find all the rows containing the items in stock of an organisational unit.
     * @param stock The stock of an organisational unit.
     * @return The indexes of all rows containing the stock items.
     */
    private static int[] find(Stock stock){
        ArrayList<Integer> matchIndexes = new ArrayList();
        for (Item item : stock){
            matchIndexes.add(find(stock.getUnitId(), item.getId()));
        }
        int[] returnIndexes = new int[matchIndexes.size()];
        for (int i = 0; i < matchIndexes.size(); i++){
            returnIndexes[i] = matchIndexes.get(i).intValue();
        }
        return returnIndexes;
    }

    /**
     * Find all the rows containing the unit's ID in stocks.
     * @param organisationalUnitID The unit's ID.
     * @return The indexes of all rows containing the unit's ID in stocks.
     */
    private static int[] find(int organisationalUnitID){
        ArrayList<Integer> matchIndexes = new ArrayList();
        for (int rowIndex = 0; rowIndex < stocks.size(); rowIndex++){
            Object[] row = stocks.get(rowIndex);
            if (row[0].equals(organisationalUnitID)){
                matchIndexes.add(rowIndex);
            }
        }
        int[] returnIndexes = new int[matchIndexes.size()];
        for (int i = 0; i < matchIndexes.size(); i++){
            returnIndexes[i] = matchIndexes.get(i).intValue();
        }
        return returnIndexes;
    }

    /**
     * Switches to specific overload of edit, based on the attachment's type.
     * @param <T> The type of the attachment
     * @param request The request
     * @return
     */
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
        else if (type.equals(Stock.class)){
            return edit((Stock) attachment);
        }
        else if (type.equals(Order.class)){
            return edit((Order) attachment);
        }
        return null;
    }

    /**
     * Edit a user by overriding the database.
     * @param user The user to override, can be identified by ID.
     */
    private static Response edit(User user){
        Object[] overrideRow = new Object[]{user.getUserId(), user.getFullName(), user.getUsername(), user.getPassword(), user.getAccountType(), user.getUnitId()};
        users.set(find(user), overrideRow);
        Response<User> response = new Response(true, user);
        return response;
    }

    /**
     * Edit an asset by overriding the database.
     * @param asset The asset to override, can be identified by ID.
     */
    private static Response edit(Asset asset){
        Object[] overrideRow = new Object[]{asset.getId(), asset.getName(), asset.getDescription()};
        assets.set(find(asset), overrideRow);
        Response<Asset> response = new Response(true, asset);
        return response;
    }

    /**
     * Edit an organisational unit by overriding the database.
     * @param organisationalUnit The organisational unit to override, can be identified by ID.
     */
    private static Response edit(OrganisationalUnit organisationalUnit){
        Object[] overrideRow = new Object[]{organisationalUnit.getId(), organisationalUnit.getName(), organisationalUnit.getBalance()};
        organisationalUnits.set(find(organisationalUnit), overrideRow);
        Response<OrganisationalUnit> response = new Response(true, organisationalUnit);
        return response;
    }

    /**
     * Edit the stock of an organisational unit by overriding the database.
     * @param stock The stock of the organisational unit to override, can be identified by the organisational unit's ID.
     */
    private static Response edit(Stock stock){
        for (Item item : stock){
            int matchIndex = find(stock.getUnitId(), item.getId());
            // If the current item has already exist, change it
            if (matchIndex >= 0){
                Object[] overrideRow = new Object[]{stock.getUnitId(), item.getId(), item.getQuantity()};
                stocks.set(matchIndex, overrideRow);
            }
            // If the current item does not exist, add it
            else{
                Object[] newRow = new Object[]{stock.getUnitId(), item.getId(), item.getQuantity()};
                stocks.add(newRow);
            }
        }

        // There are cases where the item exist in the database but not in stock (due to it being deleted)
        int[] matchStockItemIndexes = find(stock);
        int[] matchOrganisationalUnitIndexes = find(stock.getUnitId());
        // Loop through all the rows containing the organisational unit
        for (int i : matchOrganisationalUnitIndexes){
            boolean found = false;
            // Check if the item also exist in stock
            for (int j : matchStockItemIndexes){
                if (i == j){
                    found = true;
                }
            }
            // If it does not, remove the row.
            if (!found){
                stocks.remove(i);
            }
        }

        Response<Stock> response = new Response(true, stock);
        return response;
    }

    public static Response edit(Order order){
        Object[] overrideRow = new Object[]{order.getOrderId(), order.getOrderType(), order.getUnitId(), order.getAssetId(),
                                            order.getPlacedQuantity(), order.getResolvedQuantity(), order.getPrice(),
                                            order.getFinishDate(), order.getOrderDate(), order.getStatus()};
        orders.set(find(order), overrideRow);
        Response<Order> response = new Response(true, order);
        return response;
    }

    /**
     * Switches to specific overload of addition, based on the attachment's type.
     * @param <T> The type of the attachment
     * @param request The request
     * @return
     */
    public static <T extends IData> Response add(Request<T> request){
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return add((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return add((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return add((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)){
            return add((Order) attachment);
        }
        return null;
    }

    private static Response add(User attachment) {
        Object[] newRow = new Object[]{users.size(), attachment.getFullName(),
                attachment.getUsername(), attachment.getPassword(),
                attachment.getAccountType(), attachment.getUnitId()};
        users.add(newRow);
        Response response = new Response(true, null);
        return response;
    }

    private static Response add(Asset attachment) {
        Object[] newRow = new Object[]{assets.size(), attachment.getName(), attachment.getDescription()};
        assets.add(newRow);
        Response response = new Response(true, null);
        return response;
    }

    private static Response add(OrganisationalUnit attachment) {
        Object[] newRow = new Object[]{organisationalUnits.size(), attachment.getName(), attachment.getBalance()};
        organisationalUnits.add(newRow);
        Response response = new Response(true, null);
        return response;
    }

    private static Response add(Order attachment){
        Object[] newRow = new Object[]{orders.size(), attachment.getOrderType(), attachment.getUnitId(), attachment.getAssetId(),
                attachment.getPlacedQuantity(), attachment.getResolvedQuantity(), attachment.getPrice(),
                attachment.getFinishDate(), attachment.getOrderDate(), attachment.getStatus()};
        orders.add(newRow);
        placeOrder(attachment);
        Response response = new Response(true, null);
        // TODO: Resolve order
        return response;
    }

    private static void placeOrder(Order order){
        int unitId = order.getUnitId();
        if (order.getOrderType().equals(Order.Type.SELL)){
            int assetId = order.getAssetId();
            int newQuantity = (int) stocks.get(find(unitId, assetId))[2] - order.getPlacedQuantity();
            stocks.set(find(unitId, assetId), new Object[]{unitId, assetId, newQuantity});
        }
        else if (order.getOrderType().equals(Order.Type.BUY)){
            for (Object[] row : organisationalUnits){
                if (row[0].equals(unitId)){
                    organisationalUnits.set(organisationalUnits.indexOf(row), new Object[]{unitId, row[1], (float) row[2] - order.getPrice() * order.getPlacedQuantity()});
                    break;
                }
            }
        }
    }

    /**
     * Switches to specific overload of deletion, based on the attachment's type.
     * @param <T> The type of the attachment
     * @param request The request
     * @return
     */
    public static <T extends IData> Response delete(Request<T> request){
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
        return null;
    }

    private static Response delete(User attachment) {
        int rowToDelete = find(attachment);
        users.remove(rowToDelete);
        Response response = new Response(true, null);
        return response;
    }

    private static Response delete(Asset attachment) {
        int rowToDelete = find(attachment);
        assets.remove(rowToDelete);

        for (int i = 0; i < stocks.size(); i++){
            if (stocks.get(i)[1].equals(attachment.getId())){
                stocks.remove(i);
            }
        }

        Response response = new Response(true, null);
        return response;
    }

    private static Response delete(OrganisationalUnit attachment) {
        int rowToDelete = find(attachment);
        organisationalUnits.remove(rowToDelete);

        // Delete the unit's stock
        int[] stockIndexes = find(attachment.getId());
        for (int index : stockIndexes){
            stocks.remove(index);
        }

        Response response = new Response(true, null);
        return response;
    }
}