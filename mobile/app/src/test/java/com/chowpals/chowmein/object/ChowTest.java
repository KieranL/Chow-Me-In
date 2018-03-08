package com.chowpals.chowmein.object;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import objects.Chows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ChowTest {
    private Chows testChow;

    @Before
    public void setup() {
        testChow = new Chows(1, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555");
    }

    @Test
    public void testBoundsId() {
        assertEquals(Integer.MAX_VALUE, new Chows(Integer.MAX_VALUE, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555").getId());
        assertEquals(Integer.MIN_VALUE, new Chows(Integer.MIN_VALUE, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555").getId());
    }

    @Test
    public void testNewChow() {
        testChow = new Chows();

        //fresh end  should be null
        assertEquals(0,testChow.getId());
        assertNull(testChow.getCreatedBy());
        assertNull(testChow.getCreatedTime());
        assertNull(testChow.getFood());
        assertEquals(false,testChow.isDeleted());
        assertNull(testChow.getLastUpdated());
        assertNull(testChow.getMeetLocation());
        assertNull(testChow.getMeetTime());
        assertNull(testChow.getNotes());
        assertNull(testChow.getCategory());
        assertNull(testChow.getPosterUser());
        assertNull(testChow.getPosterName());
        assertNull(testChow.getPosterPhone());

        // testChow start time is the same
        testChow.setCreatedTime("2018-03-05T03:52:43");
        testChow.setLastUpdated("2018-03-05T03:52:43");
        assertTrue(testChow.getCreatedTime().compareTo(testChow.getLastUpdated()) == 0);

        // start and end times are not the same
        testChow.setCreatedTime("2018-03-05T03:52:43");
        testChow.setLastUpdated("2018-03-05T05:52:43");
        assertNotEquals(testChow.getCreatedTime(), testChow.getLastUpdated());

    }

    @Test
    public void testFoodNameChanged() {
        // set the start time after grabbing it
        testChow = new Chows(1, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555");

        assertEquals("Donuts", testChow.getFood());

        testChow.setFood("Timbits");

        assertEquals("Timbits", testChow.getFood());
    }

    @Test
    public void testDeletedChanged() {
        // set the start time after grabbing it
        testChow = new Chows(1, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555");

        assertEquals(false, testChow.isDeleted());

        testChow.setDeleted(true);

        assertEquals(true, testChow.isDeleted());
    }


    @After
    public void tearDown() {

    }
}
