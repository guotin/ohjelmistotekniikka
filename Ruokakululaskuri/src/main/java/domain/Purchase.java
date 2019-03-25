
package domain;

import dao.PurchaseDao;
import java.time.LocalDate;

public class Purchase {
    
    private Double sum;
    private LocalDate date;
    private PurchaseDao purchaseDao;
    
    public Purchase(Double sum, LocalDate date) {
        this.sum = sum;
        this.date = date;
        purchaseDao = new PurchaseDao();
    }
}
