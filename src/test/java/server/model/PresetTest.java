package server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PresetTest {

    private List<String> list;
    private Preset preset1;
    private Preset preset2;
    private Preset preset3;
    private Preset preset4;

    @Before
    public void setUp(){
        list = new ArrayList<>();
        list.add("vegetable meal");
        list.add("reuse bottle");
        preset1 = new Preset("preset", list);
        preset2 = new Preset("preset", list);
        preset3 = new Preset("different preset", new ArrayList<>());
        preset4 = new Preset("preset", new ArrayList<>());
    }

    @Test
    public void constructorTest1() {
        Preset preset = new Preset("my preset", new ArrayList<>());
        assertNotNull(preset);
    }

    @Test
    public void constructorTest2() {
        Preset preset = new Preset("my preset", list);
        assertNotNull(preset);
    }

    @Test
    public void getName() {
        Preset preset = new Preset("my preset", new ArrayList<>());
        assertEquals("my preset", preset.getName());
    }

    @Test
    public void getActionList() {
        Preset preset = new Preset("my preset", new ArrayList<>());
        assertNotNull(preset.getActionList());
    }

    @Test
    public void setActionList() {
        Preset preset = new Preset("my preset", new ArrayList<>());
        preset.setActionList(list);
        assertEquals(list, preset.getActionList());
    }

    @Test
    public void testEqualsSimilar() {
        assertEquals("A preset should be equal to another preset with the same values for fields.", preset1, preset2);
    }

    @Test
    public void testEqualsSelf() {
        assertEquals("A preset should be equal to itself.", preset1, preset1);
    }

    @Test
    public void testEqualsCompletelyDifferent() {
        assertNotEquals("A preset with different field values should not be equal.", preset1, preset3);
    }

    @Test
    public void testEqualsNameDifferent() {
        assertNotEquals("If a preset has a different name, it should not be equal.", preset3, preset4);
    }

    @Test
    public void testEqualsListDifferent() {
        assertNotEquals("If a preset has a different list, it should not be equal.", preset1, preset4);
    }

    @Test
    public void testEqualsNotNull() {
        assertNotEquals("A preset should not be equal to null.", preset1, null);
    }

    @Test
    public void testEqualsNotString() {
        assertNotEquals("A preset should not be equal to a String.", preset1, "String");
    }

    @Test
    public void testEqualsNotUser() {
        assertNotEquals("A preset should not be equal to a User.", preset1, new User("user", "pass", "mail", 10, new Date()));
    }
}
