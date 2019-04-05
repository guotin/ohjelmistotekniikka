package application.dao;

import java.sql.SQLException;

public interface UserDaoInterface<T, K> {

    void create(T object) throws SQLException;

}
