package application.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    
    @Test
    public void differentUsernamesAreDifferentUsers() {
        User user1 = new User("Matti", "salasana");
        User user2 = new User("Pekka", "salasana");
        assertFalse(user1.equals(user2));
    }
    
}
