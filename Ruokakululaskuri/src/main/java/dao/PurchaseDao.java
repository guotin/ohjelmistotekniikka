
package dao;

import domain.Purchase;
import domain.PurchaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseDao implements Dao<Purchase, Integer> {
    
    public PurchaseDao() {
        
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
    
    public int getSumSpent() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./foodpurchases", "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT sum(sum) AS allsums FROM Purchase");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer sum  = resultSet.getInt("allsums");
            return sum;
        }
        
        return 0;
    }

    @Override
    public void create(Purchase purchase) throws SQLException {
        
        Connection connection = DriverManager.getConnection("jdbc:h2:./foodpurchases", "sa", "");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Purchase(sum, date) VALUES(?, ?)");
        statement.setDouble(1, purchase.getSum());
        statement.setDate(2, java.sql.Date.valueOf(purchase.getDate()));
        statement.executeUpdate();
    }
    
}

    
    

