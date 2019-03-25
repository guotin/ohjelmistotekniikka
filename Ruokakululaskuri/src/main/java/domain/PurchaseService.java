
package domain;

import dao.PurchaseDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseService {
    
    private Purchase purchase;
    
    public PurchaseService() throws SQLException {
        
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./foodpurchases", "sa", "")) {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS Purchase (\n"
                    + "    id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                    + "    sum INTEGER,\n"
                    + "    date DATE,\n"
                    + ");").executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
