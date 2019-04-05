package application.dao;

import java.sql.SQLException;

public interface PurchaseDaoInterface<T, K> {

    void create(T object, K key) throws SQLException;

}
