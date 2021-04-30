package common.dataClasses;

import java.util.ArrayList;

/**
 * Represents a collection of items owned by an organisation.
 */
public class Stock extends ArrayList<Item> implements IData{
    private int organisationId;

    /**
     * Initiates the stock by declaring the organisation owning it.
     * @param organisationId The ID of the organisation owning the stock.
     */
    public Stock(int organisationId){
        this.organisationId = organisationId;
    }

    /**
     * Attempts to adds a new item to the stock. If the current item has already exist, the newly added item is
     * added on top of the current one (increasing its quantity).
     * @return true if the item is added successfully, false otherwise.
     */
    public boolean add(Item newItem){
        boolean result = false;

        // Checks if the item has already exist. If yes, add the new item on top.
        for(int i = 0; i < this.size(); i++){
            Item currentItem = this.get(i);
            if (currentItem.getName() == newItem.getName()){
                this.get(i).add(newItem.getQuantity());
                result = true;
                break;
            }
        }

        result = result ? result : super.add(newItem);
        return result;
    }
}
