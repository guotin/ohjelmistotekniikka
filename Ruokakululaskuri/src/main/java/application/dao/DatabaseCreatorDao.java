package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that creates the database used in the application
 */
public class DatabaseCreatorDao {
    
    private String databaseName;
    
    /**
     * Database constructor 
     * @param name defines the database name
     */
    public DatabaseCreatorDao(String name) {
        this.databaseName = "jdbc:h2:./" + name;
        createUserTable();
        createPurchaseTable();
    }
    
    /**
     * Creates a table for users if it does not exist
     */
    public void createUserTable() {
        try (Connection conn = DriverManager.getConnection(databaseName)) {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS User (\n"
                    + "    id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                    + "    username VARCHAR(100),\n"
                    + "    password VARCHAR(100),\n"
                    + ");").executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreatorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a table for purchases if it does not exist
     */
    public void createPurchaseTable() {
        try (Connection conn = DriverManager.getConnection(databaseName)) {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS Purchase (\n"
                    + "    id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                    + "    user_id INTEGER,\n"
                    + "    sum INTEGER,\n"
                    + "    date DATE,\n"
                    + "    FOREIGN KEY (user_id) REFERENCES User(id),\n"
                    + ");").executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreatorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Resets the user and purchase tables in the database by deleting them and calling the creators
     */
    public void resetTables() {
        try (Connection conn = DriverManager.getConnection(databaseName)) {
            conn.prepareStatement("DROP TABLE User;").executeUpdate();
            conn.prepareStatement("DROP TABLE Purchase;").executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreatorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        createUserTable();
        createPurchaseTable();
    }
}
