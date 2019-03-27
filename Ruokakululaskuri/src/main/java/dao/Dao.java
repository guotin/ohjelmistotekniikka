
package dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    
    void create(T object) throws SQLException;
    
}
