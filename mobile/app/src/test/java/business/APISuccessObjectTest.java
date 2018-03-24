package business;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.UserHelper;
import objects.APISuccessObject;
import objects.Chows;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class APISuccessObjectTest {

    private APISuccessObject testAPISuccessObject;

    @Before
    public void setup() {
        testAPISuccessObject = new APISuccessObject(true, new Chows()
                .setId(-1)
                .setPosterUser(UserHelper.getUsername())
                .setPosterName(UserHelper.getUsersName())
                .setPosterEmail(UserHelper.getUserEmail())
                .setDeleted(false)
                .setFood("Donut Test")
                .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setMeetLocation("U of M Test Location")
                .setMeetTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setNotes("Donuts!")
                .setCategory("American"));
    }

    @Test
    public void testNewAPISuccessObject() {
        testAPISuccessObject = new APISuccessObject();
        assertEquals(false, testAPISuccessObject.isSuccess());
        assertNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testSuccess() {
        testAPISuccessObject = new APISuccessObject(true, new Chows()
                .setId(-1)
                .setPosterUser(UserHelper.getUsername())
                .setPosterName(UserHelper.getUsersName())
                .setPosterEmail(UserHelper.getUserEmail())
                .setDeleted(false)
                .setFood("Donut Test")
                .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setMeetLocation("U of M Test Location")
                .setMeetTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setNotes("Donuts!")
                .setCategory("American"));
        assertEquals(true, testAPISuccessObject.isSuccess());
        assertNotNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testFailure() {
        testAPISuccessObject = new APISuccessObject(false, new Chows()
                .setId(-1)
                .setPosterUser(UserHelper.getUsername())
                .setPosterName(UserHelper.getUsersName())
                .setPosterEmail(UserHelper.getUserEmail())
                .setDeleted(false)
                .setFood("Donut Test")
                .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setMeetLocation("U of M Test Location")
                .setMeetTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setNotes("Donuts!")
                .setCategory("American"));
        assertEquals(false, testAPISuccessObject.isSuccess());
        assertNotNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testSuccessNull() {
        testAPISuccessObject = new APISuccessObject(true, null);
        assertEquals(true, testAPISuccessObject.isSuccess());
        assertNull(testAPISuccessObject.getChow());
    }

    @Test
    public void testFailureNull() {
        testAPISuccessObject = new APISuccessObject(false, null);
        assertEquals(false, testAPISuccessObject.isSuccess());
        assertNull(testAPISuccessObject.getChow());
    }

    @After
    public void tearDown() {

    }
}
