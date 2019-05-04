
package application.domain;

import application.dao.DatabaseCreatorDao;
import application.dao.PurchaseDao;
import application.dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
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
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        assertEquals(0, purchaseService.createPurchase("test", LocalDate.now()));
    }
    
    @Test
    public void purchaseIsNotCreatedWithNegativeSum() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
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
    public void purchaseIsCreatedIfLoggedInAndGoodInput() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        assertEquals(2, purchaseService.createPurchase("50", LocalDate.now()));
    }
    
    @Test
    public void currentMonthPurchasesAreReturned() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.now());
        purchaseService.createPurchase("50", LocalDate.now());
        assertEquals(2, purchaseService.getPurchasesOfCurrentMonth().size());
    }
    
    @Test
    public void currentMonthPurchasesOnlyReturnsFromCurrentMonth() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.MAX);
        purchaseService.createPurchase("50", LocalDate.MAX);
        assertEquals(0, purchaseService.getPurchasesOfCurrentMonth().size());
    }
    
    @Test
    public void allPurchasesAreIncludedInTotalSum() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.now());
        purchaseService.createPurchase("50", LocalDate.now());
        purchaseService.createPurchase("60", LocalDate.now());
        purchaseService.createPurchase("100", LocalDate.now());
        assertEquals(240, purchaseService.getMoneySpent());
    }
    
    @Test
    public void onlyUsersPurchasesAreIncludedInTotalSum() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.now());
        purchaseService.createPurchase("50", LocalDate.now());
        purchaseService.createPurchase("60", LocalDate.now());
        purchaseService.createPurchase("100", LocalDate.now());
        purchaseService.createUser("user", "user");
        purchaseService.loginUser("user", "user");
        purchaseService.createPurchase("30", LocalDate.now());
        assertEquals(30, purchaseService.getMoneySpent());
        
    }
    
    @Test
    public void currentYearPurchasesMapsCorrectly() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.now());
        purchaseService.createPurchase("50", LocalDate.now());
        purchaseService.createPurchase("60", LocalDate.now());
        purchaseService.createPurchase("100", LocalDate.now());
        purchaseService.createPurchase("100", LocalDate.MAX);
        Map<Integer, Integer> purchaseMapTest = purchaseService.getPurchasesOfCurrentYear();
        int sum = purchaseMapTest.get(LocalDate.now().getMonthValue());
        assertEquals(240, sum);
    }
    
    @Test
    public void getAllPurchasesReturnsEverythingFromUser() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.MAX);
        purchaseService.createPurchase("50", LocalDate.MAX);
        purchaseService.createPurchase("70", LocalDate.MIN);
        purchaseService.createPurchase("50", LocalDate.MIN);
        purchaseService.createPurchase("70", LocalDate.now());
        purchaseService.createPurchase("50", LocalDate.now());
        assertEquals(6, purchaseService.getAllPurchases().size());
    }
    
    @Test
    public void getAllPurchasesOnlyReturnsUsersPurchases() {
        purchaseService.createUser("test", "test");
        purchaseService.loginUser("test", "test");
        purchaseService.createPurchase("30", LocalDate.MAX);
        purchaseService.createPurchase("50", LocalDate.MAX);
        purchaseService.createPurchase("70", LocalDate.MIN);
        purchaseService.createPurchase("50", LocalDate.MIN);
        purchaseService.createPurchase("70", LocalDate.now());
        purchaseService.createPurchase("50", LocalDate.now());
        purchaseService.createUser("user", "user");
        purchaseService.loginUser("user", "user");
        assertEquals(0, purchaseService.getAllPurchases().size());
    }
 
    
}
