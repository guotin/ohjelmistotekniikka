package application.dao;

import application.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class that handles the database actions related to users
 */
public class UserDao implements UserDaoInterface<User, Integer> {
    
    private String databaseName;
    
    /**
     * Constructor that defines which database to access
     * @param name is the database name
     */
    public UserDao(String name) {
        this.databaseName = "jdbc:h2:./" + name;
    }
    
    /**
     * Stores a new user to the database
     * @param user is the user to be stored
     * @throws SQLException if there was an error with database connection
     */
    @Override
    public void create(User user) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseName);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO User (username, password) VALUES (?,?)");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();
    }
    /**
     * returns whether a specified combination username and password (defined as a user) exists in the database
     * @param user is the user
     * @return whether username and password match to an entity in the database
     * @throws SQLException if there was an error with database connection
     */
    @Override
    public boolean login(User user) throws SQLException {

        Connection connection = DriverManager.getConnection(databaseName);
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
    
    /**
     * Gets the unique id related to a specified username
     * @param username is the username
     * @return the id as an integer
     * @throws SQLException if there was an error with database connection
     */
    @Override
    public int getIdByUsername(String username) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseName);
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM User WHERE username = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        int id = -1;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        return id;
    }
    /**
     * Searches a given username from the database
     * @param username is the username
     * @return whether username exists
     * @throws SQLException if there was an error with database connection
     */
    @Override
    public boolean searchUsername(String username) throws SQLException {

        Connection connection = DriverManager.getConnection(databaseName);
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
