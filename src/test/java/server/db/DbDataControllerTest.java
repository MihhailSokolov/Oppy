package server.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import server.repository.ActionRepository;
import server.repository.UserRepository;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DbDataControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    DbDataController dbDataController;


    @Test
    public void testGetActionPoints() {
        assertEquals(0, dbDataController.getActionPoints("actionthatdoesnotactuallyexist"));
    }

    @Test
    public void testGetAllActions() {
        assertNotNull(dbDataController.getAllActions());
    }

    @Test
    public void testDeleteUser() {
        assertFalse(dbDataController.deleteUser("thisusershouldnotexist"));
    }
}
