
package domain;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;


public class PurchaseTest {
    
    Purchase purchase;
    
    @Before
    public void setUp() {
        LocalDate date = LocalDate.now();
        purchase = new Purchase(10, date);
    }
    
    @Test
    public void sumIsCorrectAtStart() {
        assertEquals(10, purchase.getSum());
    }
}
