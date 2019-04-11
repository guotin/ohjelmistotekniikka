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

public class PurchaseDao implements PurchaseDaoInterface<Purchase, Integer> {
    
    private String databaseName;
    
    public PurchaseDao(String name) {
        this.databaseName = "jdbc:h2:./" + name;
    }

    public int getSumSpent(int userId) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT sum(sum) AS allsums FROM Purchase WHERE user_id = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer sum = resultSet.getInt("allsums");
            return sum;
        }

        return 0;
    }
    
    public List<Purchase> getPurchasesBetweenTimeframe(LocalDate firstDay, LocalDate lastDay, int userId) throws SQLException {
        List<Purchase> purchases = new ArrayList<>();
        
        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT sum, date FROM Purchase WHERE date > ? AND date < ? AND user_id = ?");
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

    @Override
    public void create(Purchase purchase, Integer currentUserId) throws SQLException {

        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Purchase(sum, date, user_id) VALUES(?, ?, ?)");
        statement.setDouble(1, purchase.getSum());
        statement.setDate(2, java.sql.Date.valueOf(purchase.getDate()));
        statement.setInt(3, currentUserId);
        statement.executeUpdate();
    }
    

}
