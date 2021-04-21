package common.dataClasses;

import client.data.sessionalClasses.Stock;
import common.Request;
import common.Response;

/**
 * Represents an organisation.
 */
public class Organisation {
    private int organisationId;
    private String organisationName;
    public float balance;

    /**
     * Initialises the organisation.
     * @param organisationId The ID of the organisation.
     * @param organisationName The name of the organisation.
     * @param balance The balance of the organisation.
     */
    public Organisation(int organisationId, String organisationName, float balance){
        this.organisationId = organisationId;
        setName(organisationName);
        setBalance(balance);
    }

    /**
     * Returns the ID of the organisation
     * @return The ID of the organisation.
     */
    public int getId(){
        return organisationId;
    }

    /**
     * Returns the name of the organisation.
     * @return The name of the organisation.
     */
    public String getName(){
        return organisationName;
    }

    /**
     * Returns the balance of the organisation.
     * @return The balance of the organisation.
     */
    public float getBalance(){
        return balance;
    }

    /**
     * Sets the name of the organisation to a new value.
     * @param name The new name for the organisation.
     */
    public void setName(String name){
        organisationName = name;
    }

    /**
     * Sets the balance of the organisation to a new value.
     * @param balance The new balance for the organisation.
     */
    public void setBalance(float balance){
        this.balance = balance;
    }
}
