package server.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PresetTest {

    @Test
    public void constructorTest1() {
        Preset preset = new Preset("my preset", new ArrayList<>());
        assertNotNull(preset);
    }

    @Test
    public void constructorTest2() {
        List<String> list = new ArrayList<>();
        list.add("vegetable meal");
        list.add("reuse bottle");
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
        List<String> list = new ArrayList<>();
        list.add("vegetable meal");
        list.add("reuse bottle");
        preset.setActionList(list);
        assertEquals(list, preset.getActionList());
    }

    @Test
    public void testEquals() {
        List<String> list = new ArrayList<>();
        list.add("vegetable meal");
        list.add("reuse bottle");
        Preset preset1 = new Preset("preset", list);
        Preset preset2 = new Preset("preset", list);
        Preset preset3 = new Preset("different preset", new ArrayList<>());
        assertEquals(preset1, preset2);
        assertEquals(preset1, preset1);
        assertNotEquals(preset1, preset3);
        assertNotEquals(null, preset1);
        assertNotEquals("String test", preset1);
        assertNotEquals(new User("user", "pass", "mail", 10, new Date()), preset1);
    }
}
