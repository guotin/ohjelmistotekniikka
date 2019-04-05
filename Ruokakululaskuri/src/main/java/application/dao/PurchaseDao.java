package application.dao;

import application.domain.Purchase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseDao implements PurchaseDaoInterface<Purchase, Integer> {

    public PurchaseDao() {

    }

    public int getSumSpent(int userId) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./foodpurchases", "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT sum(sum) AS allsums FROM Purchase WHERE user_id = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer sum = resultSet.getInt("allsums");
            return sum;
        }

        return 0;
    }

    @Override
    public void create(Purchase purchase, Integer currentUserId) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:./foodpurchases", "sa", "");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Purchase(sum, date, user_id) VALUES(?, ?, ?)");
        statement.setDouble(1, purchase.getSum());
        statement.setDate(2, java.sql.Date.valueOf(purchase.getDate()));
        statement.setInt(3, currentUserId);
        statement.executeUpdate();
    }

}
