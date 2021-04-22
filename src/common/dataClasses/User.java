package common.dataClasses;

import java.io.Serializable;

/**
 * Represents a user.
 */
public class User extends Object implements Serializable, IData {
    private String username;
    private String password;
    private String accountType;
    private int organisationId;

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

    @Override
    public boolean equals(Object o){
        User userToCompare = (User) o;
        return userToCompare.getUsername() == username && userToCompare.getPassword() == password
                && userToCompare.getAccountType() == accountType
                && userToCompare.getOrganisationId() == organisationId;
    }
}
