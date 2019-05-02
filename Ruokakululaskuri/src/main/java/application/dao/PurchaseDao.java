package application.dao;

import application.domain.Purchase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles the database actions related to purchases
 */
public class PurchaseDao implements PurchaseDaoInterface<Purchase, Integer> {
    
    private String databaseName;
    
    /**
     * Constructor that defines which database to access
     * @param name is the database name
     */
    public PurchaseDao(String name) {
        this.databaseName = "jdbc:h2:./" + name;
    }
    
    /**
     * Gets the amount of money a person has spent 
     * @param userId is the user's personal id in the database
     * @return amount of money spent
     * @throws SQLException if there was an error with the database connection
     */
    @Override
    public int getSumSpent(Integer userId) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseName);
        PreparedStatement statement = connection.prepareStatement("SELECT sum(sum) AS allsums FROM Purchase WHERE user_id = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer sum = resultSet.getInt("allsums");
            return sum;
        }

        return 0;
    }
    
    /**
     * Gets the purchases between a specified timeframe for a specified person
     * @param firstDay is the start date
     * @param lastDay is the end date
     * @param userId is the user's personal id in the database
     * @return a list of wanted purchases
     * @throws SQLException if there was an error with the database connection
     */
    @Override
    public List<Purchase> getPurchasesBetweenTimeframe(LocalDate firstDay, LocalDate lastDay, Integer userId) throws SQLException {
        List<Purchase> purchases = new ArrayList<>();
        
        Connection connection = DriverManager.getConnection(databaseName);
        PreparedStatement statement = connection.prepareStatement("SELECT sum, date FROM Purchase WHERE date >= ? AND date <= ? AND user_id = ?");
        statement.setDate(1, java.sql.Date.valueOf(firstDay));
        statement.setDate(2, java.sql.Date.valueOf(lastDay));
        statement.setInt(3, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer sum = resultSet.getInt("sum");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            purchases.add(new Purchase(sum, date));
        }
        return purchases;
    }
    /**
     * Gets all of the purchases for a specified person
     * @param userId is the user's personal id in the database
     * @return a list of all of the user's purchases
     * @throws SQLException if the was an error with the database connection
     */
    @Override
    public List<Purchase> getAllPurchases(Integer userId) throws SQLException {
        List<Purchase> purchases = new ArrayList<>();
        
        Connection connection = DriverManager.getConnection(databaseName);
        PreparedStatement statement = connection.prepareStatement("SELECT sum, date FROM Purchase WHERE user_id = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer sum = resultSet.getInt("sum");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            purchases.add(new Purchase(sum, date));
        }
        return purchases;
    }
    
    /**
     * Stores a new purchase into the database
     * @param purchase is the purchase to be stored
     * @param currentUserId is the user's id that has made the purchase
     * @throws SQLException if there was an error with the database connection
     */
    @Override
    public void create(Purchase purchase, Integer currentUserId) throws SQLException {

        Connection connection = DriverManager.getConnection(databaseName);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Purchase(sum, date, user_id) VALUES(?, ?, ?)");
        statement.setDouble(1, purchase.getSum());
        statement.setDate(2, java.sql.Date.valueOf(purchase.getDate()));
        statement.setInt(3, currentUserId);
        statement.executeUpdate();
    }   

}
