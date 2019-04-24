package application.dao;

import java.sql.SQLException;

public interface UserDaoInterface<T, K> {

    void create(T object) throws SQLException;
    
    boolean login(T object) throws SQLException;
    
    int getIdByUsername(String username) throws SQLException;
    
    boolean searchUsername(String username) throws SQLException;
}
