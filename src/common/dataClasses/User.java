package common.dataClasses;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User extends Object implements IData {
    private String username;
    private String password;
    private String accountType;
    private int unitId;

    /**
     * Initiates a user without a role and organisational unit.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password){
        this.username = username;
        setPassword(password);
    }

    /**
     * Initiates a user with all parameters.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param accountType The account type of the user.
     * @param unitId The ID of the organisational unit the user belongs to.
     */
    public User(String username, String password, String accountType, int unitId){
        this.username = username;
        setPassword(password);
        setAccountType(accountType);
        setOrganisation(unitId);
    }

    /**
     * @return The username of the user.
     */
    public String getUsername(){
        return username;
    }

    /**
     * @return set username of the user.
     */
    public void setUsername(String UserName) { this.username = UserName; }

    /**
     * @return The password of the user.
     */
    public String getPassword(){
        return password; //TODO returns the hashed password instead
    }

    /**
     * @return The account type of the user.
     */
    public String getAccountType(){
        return accountType;
    }

    /**
     * @return The ID of the organisational unit the user belongs to.
     */
    public int getunitId(){
        return unitId;
    }

    /**
     * Sets the password of the user to a new value.
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Sets the account type of the user to a new level.
     * @param accountType
     */
    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    /**
     * Sets the user's organisational unit, identified by the organisational unit's ID.
     * @param unitId
     */
    public void setOrganisation(int unitId){
        this.unitId = unitId;
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
        User user = (User) o;
        return unitId == user.unitId && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(accountType, user.accountType);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, accountType, unitId);
    }
}
