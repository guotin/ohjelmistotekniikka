
package dao;

import java.sql.SQLException;

public interface Dao<T, K> {
    
    void create(T object) throws SQLException;
    
}
