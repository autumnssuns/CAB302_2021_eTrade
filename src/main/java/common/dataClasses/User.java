package common.dataClasses;

import common.HashPassword;

import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements IData {
    private Integer userId;
    private String fullName;
    private String username;
    private String password;
    private String accountType;
    private Integer unitId;

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
    public User(Integer userId, String fullName, String username, String password, String accountType, Integer unitId){
        setUserId(userId);
        setUsername(username);
        setFullName(fullName);
        setPassword(password);
        setAccountType(accountType);
        setOrganisation(unitId);
    }

    /**
     * Sets the user's ID
     * @param userId The user's ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Get username of the instance
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
    public Integer getUnitId(){
        return unitId;
    }

    /**
     * Set the user's password
     * @param password The new password.
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Hashes the user's password
     * @return
     */
    public User hashPassword(){
        this.password = HashPassword.HashPassword(this.password);
        return this;
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
    public void setOrganisation(Integer unitId){
        this.unitId = unitId;
    }

    /**
     * Returns the user id.
     * @return The user id.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Returns the user's fullname
     * @return The user's fullname
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the fullname of the user to a new value
     * @param fullName The new fullname for the user
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Set the unit id the user is working for.
     * @param unitId The new unit id.
     */
    public void setUnitId(Integer unitId) {
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
        return Objects.equals(userId, user.userId) && Objects.equals(unitId,user.unitId) && Objects.equals(fullName, user.fullName) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(accountType, user.accountType);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, fullName, username, password, accountType, unitId);
    }
}
