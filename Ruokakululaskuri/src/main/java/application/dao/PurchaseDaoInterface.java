package application.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface of data access for purchases
 * @param <T> is the purchase
 * @param <K> is the user's id
 */
public interface PurchaseDaoInterface<T, K> {
    
    /**
     * Creates a new purchase to database/file
     * @param object is the purchase
     * @param key is the user's id
     * @throws SQLException if the was an error with the connection to database
     */
    void create(T object, K key) throws SQLException;
    
    /**
     * Finds the total amount of money a user has spent
     * @param key is the user's id
     * @return amount of money spent as an integer
     * @throws SQLException if the was an error with the connection to database
     */
    int getSumSpent(K key) throws SQLException;
    
    /**
     * Gets a list of purchases between a specific timeframe
     * @param firstDay is the start of the period
     * @param lastDay is the end of the period
     * @param key is the user's id
     * @return a list of purchases
     * @throws SQLException if the was an error with the connection to database
     */
    List<T> getPurchasesBetweenTimeframe(LocalDate firstDay, LocalDate lastDay, K key) throws SQLException;
    
    /**
     * Gets a list of all the purchases a user has added
     * @param key is the user's id
     * @return a list of purchases
     * @throws SQLException if the was an error with the connection to database
     */
    List<T> getAllPurchases(K key) throws SQLException;

}
