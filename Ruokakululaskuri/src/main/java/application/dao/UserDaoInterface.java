package application.dao;

import java.sql.SQLException;

/**
 * Interface of data access for users
 * @param <T> is the user
 */
public interface UserDaoInterface<T> {
    
    /**
     * Creates a new user to the database
     * @param object is the user
     * @throws SQLException if the was an error with the connection to database
     */
    void create(T object) throws SQLException;
    
    /**
     * Method used for logging in a user
     * Checks whether username and password pair exists
     * @param object is the user
     * @return true if exists, else false
     * @throws SQLException if the was an error with the connection to database
     */
    boolean login(T object) throws SQLException;
    
    /**
     * Returns the id related to a username in the databae
     * @param username is the username
     * @return id as an integer
     * @throws SQLException if the was an error with the connection to database
     */
    int getIdByUsername(String username) throws SQLException;
    
    /**
     * Searches for a username from the database
     * @param username is the username
     * @return true if exits, else false
     * @throws SQLException if the was an error with the connection to database
     */
    boolean searchUsername(String username) throws SQLException;
}
