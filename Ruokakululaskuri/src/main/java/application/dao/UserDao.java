package application.dao;

import application.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements UserDaoInterface<User, Integer> {
    
    private String databaseName;
    
    public UserDao(String name) {
        this.databaseName = "jdbc:h2:./" + name;
    }

    @Override
    public void create(User user) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO User (username, password) VALUES (?,?)");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();
    }

    public boolean login(User user) throws SQLException {

        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT username, password FROM User WHERE username = ?");
        statement.setString(1, user.getUsername());
        ResultSet resultSet = statement.executeQuery();
        User databaseUser = null;
        while (resultSet.next()) {
            databaseUser = new User(resultSet.getString("username"), resultSet.getString("password"));
        }
        if (databaseUser == null) {
            return false;
        }
        if (!user.getPassword().equals(databaseUser.getPassword())) {
            return false;
        }
        return true;
    }

    public int getIdByUsername(String username) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM User WHERE username = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        int id = -1;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        return id;
    }

    public boolean searchUsername(String username) throws SQLException {

        Connection connection = DriverManager.getConnection(databaseName, "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM User WHERE username = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        int id = -1;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        return id != -1;
    }

}
