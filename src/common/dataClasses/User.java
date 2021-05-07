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
    private int organisationId;

    /**
     * Initiates a user without a role and organisation.
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
     * @param organisationId The ID of the organisation the user belongs to.
     */
    public User(String username, String password, String accountType, int organisationId){
        this.username = username;
        setPassword(password);
        setAccountType(accountType);
        setOrganisation(organisationId);
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
     * @return The ID of the organisation the user belongs to.
     */
    public int getOrganisationId(){
        return organisationId;
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
     * Sets the user's organisation, identified by the organisation's ID.
     * @param organisationId
     */
    public void setOrganisation(int organisationId){
        this.organisationId = organisationId;
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
        return organisationId == user.organisationId && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(accountType, user.accountType);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, accountType, organisationId);
    }
}
