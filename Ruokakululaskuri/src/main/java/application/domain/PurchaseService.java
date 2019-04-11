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

import java.util.List;


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
    
    public int createPurchase(String sum, LocalDate date) {
        // return values:
        // Dao fails -> return -1
        // Not a number -error -> return 0
        // negative sum -> return 1
        // success -> return 2
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

    public boolean loginUser(String username, String password) {
        user = new User(username, password);
        try {
            currentUserId = userDao.getIdByUsername(username);
            return userDao.login(user);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean usernameExists(String username) {
        try {
            return userDao.searchUsername(username);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean createUser(String username, String password) {
        User userToCreate = new User(username, password);
        try {
            userDao.create(userToCreate);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public int lastDayOfMonth() {
        return LocalDate.now().lengthOfMonth();
    }

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
    

}
