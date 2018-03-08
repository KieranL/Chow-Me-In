package com.chowpals.chowmein.object;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import objects.APISuccessObject;
import objects.Chows;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class APISuccessObjectTest {

    private APISuccessObject testAPISuccessObject;

    @Before
    public void setup() {
        testAPISuccessObject = new APISuccessObject(true,new Chows(1, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555"));
    }

    @Test
    public void testNewAPISuccessObject() {
        testAPISuccessObject = new APISuccessObject();
        assertEquals(false,testAPISuccessObject.isSuccess());
        assertNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testSuccess() {
        testAPISuccessObject = new APISuccessObject(true,new Chows(1, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555"));
        assertEquals(true,testAPISuccessObject.isSuccess());
        assertNotNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testFailure() {
        testAPISuccessObject = new APISuccessObject(false,new Chows(1, "MobileGuest", "2018-03-05T03:52:43", false, "Donuts", "2018-03-05T03:52:43", "University of Manitoba", "2018-03-05T05:52:43", "No notes", "American", "MobileGuest", "mbilgust1", "+15555555555"));
        assertEquals(false,testAPISuccessObject.isSuccess());
        assertNotNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testSuccessNull() {
        testAPISuccessObject = new APISuccessObject(true,null);
        assertEquals(true,testAPISuccessObject.isSuccess());
        assertNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testFailureNull() {
        testAPISuccessObject = new APISuccessObject(false,null);
        assertEquals(false,testAPISuccessObject.isSuccess());
        assertNull(testAPISuccessObject.getChow());
    }

    @After
    public void tearDown() {

    }
}
