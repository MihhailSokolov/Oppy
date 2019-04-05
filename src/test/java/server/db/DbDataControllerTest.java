package server.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import server.model.Preset;
import server.model.User;
import server.repository.ActionRepository;
import server.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testGetTop50() {
        List<User> users = dbDataController.getTop50Users();
        assertNotNull(users);
    }

    @Test
    public void testFindIndexByPresetName() {
        Preset preset1 = new Preset("preset1", new ArrayList<>());
        Preset preset2 = new Preset("preset2", new ArrayList<>());
        List<Preset> presets = new ArrayList<>();
        presets.add(preset1);
        presets.add(preset2);
        int index = dbDataController.findIndexByPresetName(presets, "preset1");
        assertEquals(0, index);
    }

    @Test
    public void testFindIndexByWrongPresetName() {
        Preset preset1 = new Preset("preset1", new ArrayList<>());
        Preset preset2 = new Preset("preset2", new ArrayList<>());
        List<Preset> presets = new ArrayList<>();
        presets.add(preset1);
        presets.add(preset2);
        int index = dbDataController.findIndexByPresetName(presets, "preset3");
        assertEquals(-1, index);
    }

    @Test
    public void testSearchUser() {
        assertNull(dbDataController.searchUser("user-which-should-not-exist"));
    }

    @Test
    public void testDeleteUserFromAllUsersFriends() {
        User userToBeDeleted = new User("delete-me", "pass", "mail", 0, new Date());
        User userWithFriends = new User("user-for-testing", "password", "email", 0, new Date());
        List<User> friends = new ArrayList<>();
        friends.add(userToBeDeleted);
        userWithFriends.setFriends(friends);
        userRepository.save(userWithFriends);
        userRepository.save(userToBeDeleted);
        dbDataController.deleteUserFromAllUsersFriends(userToBeDeleted.getUsername());
        userWithFriends = userRepository.findFirstByUsername(userWithFriends.getUsername());
        userRepository.delete(userToBeDeleted);
        userRepository.delete(userWithFriends);
        assertFalse(userWithFriends.getFriends().contains(userToBeDeleted));
    }
}
