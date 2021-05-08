package common.dataClasses;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an organisational unit.
 */
public class OrganisationalUnit implements Serializable, IData{
    private int unitId;
    private String unitName;
    public float balance;

    /**
     * Initialises the organisational unit.
     * @param unitId The ID of the organisational unit.
     * @param unitName The name of the organisational unit.
     * @param balance The balance of the organisational unit.
     */
    public OrganisationalUnit(int unitId, String unitName, float balance){
        this.unitId = unitId;
        setName(unitName);
        setBalance(balance);
    }

    /**
     * Returns the ID of the organisational unit
     * @return The ID of the organisational unit.
     */
    public int getId(){
        return unitId;
    }

    /**
     * Set the id of the organisational unit to a new value.
     * @param unitId The new value for the organisational unit.
     */
    public void setId(int unitId){
        this.unitId = unitId;
    }

    /**
     * Returns the name of the organisational unit.
     * @return The name of the organisational unit.
     */
    public String getName(){
        return unitName;
    }

    /**
     * Returns the balance of the organisational unit.
     * @return The balance of the organisational unit.
     */
    public float getBalance(){
        return balance;
    }

    /**
     * Sets the name of the organisational unit to a new value.
     * @param name The new name for the organisational unit.
     */
    public void setName(String name){
        unitName = name;
    }

    /**
     * Sets the balance of the organisational unit to a new value.
     * @param balance The new balance for the organisational unit.
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
        OrganisationalUnit that = (OrganisationalUnit) o;
        return unitId == that.unitId && Float.compare(that.balance, balance) == 0 && Objects.equals(unitName, that.unitName);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(unitId, unitName, balance);
    }
}
