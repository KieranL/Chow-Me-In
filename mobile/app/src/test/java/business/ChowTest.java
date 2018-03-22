package business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.UserHelper;
import objects.Chows;

import static org.junit.Assert.assertEquals;

public class ChowTest {
    private Chows testChow;

    @Before
    public void setup() {
        testChow = new Chows()
                .setId(1)
                .setPosterUser(UserHelper.getUsername())
                .setPosterName(UserHelper.getUsersName())
                .setPosterEmail(UserHelper.getUserEmail())
                .setDeleted(false)
                .setFood("Donut Test")
                .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setMeetLocation("U of M Test Location")
                .setMeetTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setNotes("Donuts!")
                .setCategory("American");
    }

    @Test
    public void testBoundsId() {
        testChow = new Chows()
                .setId(Integer.MAX_VALUE)
                .setPosterUser(UserHelper.getUsername())
                .setPosterName(UserHelper.getUsersName())
                .setPosterEmail(UserHelper.getUserEmail())
                .setDeleted(false)
                .setFood("Donut Test")
                .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setMeetLocation("U of M Test Location")
                .setMeetTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setNotes("Donuts!")
                .setCategory("American");

        assertEquals(Integer.MAX_VALUE, testChow.getId());

        testChow = new Chows()
                .setId(Integer.MIN_VALUE)
                .setPosterUser(UserHelper.getUsername())
                .setPosterName(UserHelper.getUsersName())
                .setPosterEmail(UserHelper.getUserEmail())
                .setDeleted(false)
                .setFood("Donut Test")
                .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setMeetLocation("U of M Test Location")
                .setMeetTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                .setNotes("Donuts!")
                .setCategory("American");

        assertEquals(Integer.MIN_VALUE, testChow.getId());
    }

    @Test
    public void testFoodNameChanged() {
        // set the start time after grabbing it
        testChow = new Chows()
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
                .setCategory("American");

        assertEquals("Donut Test", testChow.getFood());

        testChow.setFood("Timbits");

        assertEquals("Timbits", testChow.getFood());
    }

    @Test
    public void testDeletedChanged() {
        testChow = new Chows()
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
                .setCategory("American");

        assertEquals(false, testChow.isDeleted());

        testChow.setDeleted(true);

        assertEquals(true, testChow.isDeleted());
    }


    @After
    public void tearDown() {

    }
}
