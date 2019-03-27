
package domain;

import dao.PurchaseDao;
import domain.Purchase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseService {
    
    private Purchase purchase;
    private PurchaseDao purchaseDao;
    
    public PurchaseService(PurchaseDao purchaseDao) throws SQLException {
        this.purchaseDao = purchaseDao;
          
    }
    
    public boolean createPurchase(String sum, LocalDate date) {
        if (Integer.parseInt(sum) < 0) {
            return false;
        }
        
        Purchase purchase = new Purchase(Integer.parseInt(sum), date);
        
        try {
            purchaseDao.create(purchase); 
        } catch(SQLException e) {
            return false;
        }
         
        return true;
        
    }
    public int getMoneySpent() {
        try {
           int moneySpent =  purchaseDao.getSumSpent();
           return moneySpent;
        } catch(SQLException e) {
            return 0;
        }
        
    }
    
}
