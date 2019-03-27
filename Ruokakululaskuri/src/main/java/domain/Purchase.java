
package domain;

import dao.PurchaseDao;
import java.time.LocalDate;

public class Purchase {
    
    private int sum;
    private LocalDate date;
    private PurchaseDao purchaseDao;
    
    public Purchase(int sum, LocalDate date) {
        this.sum = sum;
        this.date = date;
        purchaseDao = new PurchaseDao();
    }

    public int getSum() {
        return sum;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Purchase sum: " + this.sum + ", purchase date: " + this.date;
    }
    
}
