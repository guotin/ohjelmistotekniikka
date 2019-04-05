
package application.domain;

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
        purchaseService = new PurchaseService();
    }
    
    @Test
    public void purchaseIsNotCreatedWithNegativeSum() {
        assertEquals(false, purchaseService.createPurchase("-10", LocalDate.now()));
    }
    
    
    
}
