package application.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PurchaseDaoInterface<T, K> {

    void create(T object, K key) throws SQLException;
    
    int getSumSpent(K key) throws SQLException;
    
    List<T> getPurchasesBetweenTimeframe(LocalDate firstDay, LocalDate lastDay, K key) throws SQLException;

}
