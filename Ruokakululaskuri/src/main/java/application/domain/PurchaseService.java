package application.domain;

import application.dao.DatabaseCreatorDao;
import application.dao.PurchaseDao;
import application.dao.UserDao;
import application.domain.Purchase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * A class that handles the main logic of this application
 */

public class PurchaseService {

    private DatabaseCreatorDao databaseCreator;
    private Purchase purchase;
    private PurchaseDao purchaseDao;
    private User user;
    private UserDao userDao;
    private int currentUserId;

    public PurchaseService(PurchaseDao purchaseDao, UserDao userDao, DatabaseCreatorDao databasecreatorDao) throws SQLException {
        this.databaseCreator = databasecreatorDao;
        this.purchaseDao = purchaseDao;
        this.userDao = userDao;
        this.currentUserId = -1;
    }
    
    /**
     * Creates a purchase and calls PurchaseDao to store the purchase to the database
     * 
     * @param sum is the amount of money spent on a single purchase
     * @param date is the date of purchase
     * @return 
     * -1 = save to database failed ///
     * 0 = sum is not a number ///
     * 1 = negative or zero sum ///
     * 2 = purchase successfully stored
     */
    public int createPurchase(String sum, LocalDate date) {
        try {
            Integer.parseInt(sum);
        } catch (NumberFormatException e) {
            return 0;
        }
        if (Integer.parseInt(sum) <= 0) {
            return 1;
        }
        purchase = new Purchase(Integer.parseInt(sum), date);
        try {
            purchaseDao.create(purchase, currentUserId);
        } catch (SQLException e) {
            return -1;
        }
        return 2;
    }
    
    /**
     * Returns the amount of money the user has spent to purchase food
     * @return amount of money spent
     */
    public int getMoneySpent() {
        try {
            int moneySpent = 0;
            if (currentUserId != -1) {
                moneySpent = purchaseDao.getSumSpent(currentUserId);
            }
            return moneySpent;
        } catch (SQLException e) {
            return 0;
        }
    }
    
    /**
     * Checks whether a pair of given username and password exists in the database
     * @param username is the username 
     * @param password is the password
     * @return whether login was successful
     */
    public boolean loginUser(String username, String password) {
        user = new User(username, password);
        try {
            currentUserId = userDao.getIdByUsername(username);
            return userDao.login(user);
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Checks whether a given username exists in the database
     * @param username is the username
     * @return username exists
     */
    public boolean usernameExists(String username) {
        try {
            return userDao.searchUsername(username);
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Stores a new user to the database
     * @param username is the username
     * @param password is the password
     * @return if user was created or not
     */
    public boolean createUser(String username, String password) {
        User userToCreate = new User(username, password);
        try {
            userDao.create(userToCreate);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    /**
     * @return the numerical value of the last day of current month
     */
    public int lastDayOfMonth() {
        return LocalDate.now().lengthOfMonth();
    }
    
    /**
     * Gets the purchases of the current month for the logged in user
     * @return list of current month purchases
     */
    public List getPurchasesOfCurrentMonth() {
        LocalDate current = LocalDate.now();
        LocalDate firstDay = current.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = current.with(TemporalAdjusters.lastDayOfMonth());
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseDao.getPurchasesBetweenTimeframe(firstDay, lastDay, currentUserId);
        } catch (SQLException e) {
        }
        Collections.sort(purchases);
        return purchases;
    }
    
    /**
     * Gets the purchases of current year for the logged in user
     * @return a map where the key is the numerical value of the month and value is the money spent that month
     */
    public Map getPurchasesOfCurrentYear() {
        LocalDate current = LocalDate.now();
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseDao.getPurchasesBetweenTimeframe(current.with(TemporalAdjusters.firstDayOfYear()), current.with(TemporalAdjusters.lastDayOfYear()), currentUserId);
        } catch (SQLException e) {
        }
        Collections.sort(purchases);
        Map<Integer, Integer> purchaseSums = new HashMap<>();
        for (Purchase purchaseInList : purchases) {
            int month = purchaseInList.getDate().getMonthValue();
            if (purchaseSums.containsKey(month)) {
                int sum = purchaseSums.get(month);
                purchaseSums.put(month, sum + purchaseInList.getSum());
            } else {
                purchaseSums.put(month, purchaseInList.getSum());
            }
        }
        return purchaseSums;      
    }
    

}
