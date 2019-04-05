package application.domain;

import application.dao.DatabaseCreatorDao;
import application.dao.PurchaseDao;
import application.dao.UserDao;
import application.domain.Purchase;

import java.sql.SQLException;
import java.time.LocalDate;

public class PurchaseService {

    private DatabaseCreatorDao databaseCreator;
    private Purchase purchase;
    private PurchaseDao purchaseDao;
    private User user;
    private UserDao userDao;
    private int currentUserId;

    public PurchaseService() throws SQLException {
        this.databaseCreator = new DatabaseCreatorDao();
        this.purchaseDao = new PurchaseDao();
        this.userDao = new UserDao();
        this.currentUserId = -1;
    }

    public boolean createPurchase(String sum, LocalDate date) {
        if (Integer.parseInt(sum) < 0) {
            return false;
        }
        purchase = new Purchase(Integer.parseInt(sum), date);
        try {
            purchaseDao.create(purchase, currentUserId);
        } catch (SQLException e) {
            return false;
        }
        return true;
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

}
