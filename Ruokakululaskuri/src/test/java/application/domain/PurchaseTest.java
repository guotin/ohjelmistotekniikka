
package application.domain;

import java.time.LocalDate;
import static org.junit.Assert.*;
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
    
    @Test
    public void purchasesDifferentWithDifferentDates() {
        Purchase purchase1 = new Purchase(10, LocalDate.now());
        Purchase purchase2 = new Purchase(10, LocalDate.MAX);
        assertFalse(purchase1.equals(purchase2));
    }
    
    @Test
    public void purchasesDifferentWithDifferentSums() {
        Purchase purchase1 = new Purchase(10, LocalDate.now());
        Purchase purchase2 = new Purchase(20, LocalDate.now());
        assertFalse(purchase1.equals(purchase2));
    }
    
    @Test
    public void printOfPurchaseWorks() {
        Purchase purchaseMaxDate = new Purchase(600, LocalDate.MAX);
        assertEquals("Purchase date: +999999999-12-31 - Purchase sum: 600", purchaseMaxDate.toString());
    }
}
