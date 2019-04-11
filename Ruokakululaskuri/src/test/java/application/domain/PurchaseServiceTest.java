
package application.domain;

import application.dao.DatabaseCreatorDao;
import application.dao.PurchaseDao;
import application.dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PurchaseServiceTest {
    
    PurchaseService purchaseService;
    
    @Before
    public void setUp() throws SQLException {
        
        PurchaseDao purchaseDao = new PurchaseDao("testdatabase");
        UserDao userDao = new UserDao("testdatabase");
        DatabaseCreatorDao databasecreatorDao = new DatabaseCreatorDao("testdatabase");
        databasecreatorDao.resetTables();
        purchaseService = new PurchaseService(purchaseDao, userDao, databasecreatorDao);
    }
    
    @Test
    public void purchaseIsNotCreatedWithBadInput() {
        assertEquals(0, purchaseService.createPurchase("test", LocalDate.now()));
    }
    
    @Test
    public void purchaseIsNotCreatedWithNegativeSum() {
        assertEquals(1, purchaseService.createPurchase("-10", LocalDate.now()));
    }
    
    @Test
    public void userIsCreated() {
        assertEquals(true, purchaseService.createUser("test", "test"));
    }
    
    @Test
    public void createdUserExists() {
        purchaseService.createUser("test", "test");
        assertEquals(true, purchaseService.usernameExists("test"));
    }
    
    @Test
    public void unknownUserDoesNotExist() {
        assertEquals(false, purchaseService.usernameExists("nope"));
    } 
    
    @Test
    public void loginWorksForCreatedUser() {
        purchaseService.createUser("test", "test");
        assertEquals(true, purchaseService.loginUser("test", "test"));
    }
    
    @Test
    public void purchaseIsCreatedIfLoggedIn() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("50", LocalDate.now());
    }
 
    
}
