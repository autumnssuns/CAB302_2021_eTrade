package common.dataClasses;
import common.Request;
import common.Response;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an organisation.
 */
public class Organisation implements Serializable, IData{
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
     * Set the id of the organisation to a new value.
     * @param organisationId The new value for the organisation.
     */
    public void setId(int organisationId){
        this.organisationId = organisationId;
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

    /**
     * Indicates if some object is equal to this instance.
     * @param o The object to compare.
     * @return true if the object is equal to the instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return organisationId == that.organisationId && Float.compare(that.balance, balance) == 0 && Objects.equals(organisationName, that.organisationName);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(organisationId, organisationName, balance);
    }
}
