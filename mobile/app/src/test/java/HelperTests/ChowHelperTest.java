package HelperTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import helpers.ChowHelper;
import objects.Chows;

import static org.junit.Assert.assertEquals;

public class ChowHelperTest {
    private Chows testChow;
    private static final SimpleDateFormat ISO_DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Before
    public void setup() {
        testChow = new Chows();
    }

    @Test
    public void testCategories() {
        ArrayList<String> categories = ChowHelper.getAllCategories();
        assertEquals("None",categories.get(0));
        assertEquals("Chinese",categories.get(1));
        assertEquals("American",categories.get(2));
        assertEquals("Italian",categories.get(3));
        assertEquals("Greek",categories.get(4));
    }

    @Test
    public void testNewChow() {
        testChow = new Chows();
        testChow = ChowHelper.ensureChowFields(testChow);
        assertEquals(0, testChow.getId());
        assertEquals("",testChow.getFood());
        assertEquals(false, testChow.isDeleted());
        assertEquals("",testChow.getLastUpdated());
        assertEquals("",testChow.getMeetLocation());
        assertEquals("",testChow.getMeetTime());
        assertEquals("",testChow.getNotes());
        assertEquals("None",testChow.getCategory());
        assertEquals("",testChow.getPosterName());
        assertEquals("",testChow.getPosterUser());
        assertEquals("",testChow.getPosterEmail());
    }

    @After
    public void tearDown() {

    }
}
