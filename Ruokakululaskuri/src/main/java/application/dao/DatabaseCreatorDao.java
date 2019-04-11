package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseCreatorDao {
    
    private String databaseName;
    
    public DatabaseCreatorDao(String name) {
        this.databaseName = "jdbc:h2:./" + name;
        createUserTable();
        createPurchaseTable();
    }

    public void createUserTable() {
        try (Connection conn = DriverManager.getConnection(databaseName, "sa", "")) {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS User (\n"
                    + "    id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                    + "    username VARCHAR(100),\n"
                    + "    password VARCHAR(100),\n"
                    + ");").executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreatorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createPurchaseTable() {
        try (Connection conn = DriverManager.getConnection(databaseName, "sa", "")) {
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
    
    public void resetTables() {
        try (Connection conn = DriverManager.getConnection(databaseName, "sa", "")) {
            conn.prepareStatement("DROP TABLE User;").executeUpdate();
            conn.prepareStatement("DROP TABLE Purchase;").executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreatorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        createUserTable();
        createPurchaseTable();
    }
}
