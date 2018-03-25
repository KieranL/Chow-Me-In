package ObjectTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import objects.Chows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChowTest {
    private Chows testChow;
    private static final SimpleDateFormat ISO_DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Before
    public void setup() {
        testChow = new Chows()
                .setId(-1)
                .setPosterUser("postTester")
                .setPosterName("postTester")
                .setPosterEmail("postTester@gmail.com")
                .setDeleted(false)
                .setFood("TestFood")
                .setLastUpdated(ISO_DATA_FORMAT.format(new Date()))
                .setMeetLocation("Here")
                .setMeetTime(ISO_DATA_FORMAT.format(new Date()))
                .setNotes("No notes")
                .setCategory("American");
    }

    @Test
    public void testBoundsId() {
        assertEquals(Integer.MAX_VALUE, new Chows()
                .setId(Integer.MAX_VALUE)
                .setPosterUser("postTester")
                .setPosterName("postTester")
                .setPosterEmail("postTester@gmail.com")
                .setDeleted(false)
                .setFood("TestFood")
                .setLastUpdated(ISO_DATA_FORMAT.format(new Date()))
                .setMeetLocation("Here")
                .setMeetTime(ISO_DATA_FORMAT.format(new Date()))
                .setNotes("No notes")
                .setCategory("American").getId());
        assertEquals(Integer.MIN_VALUE, new Chows()
                .setId(Integer.MIN_VALUE)
                .setPosterUser("postTester")
                .setPosterName("postTester")
                .setPosterEmail("postTester@gmail.com")
                .setDeleted(false)
                .setFood("TestFood")
                .setLastUpdated(ISO_DATA_FORMAT.format(new Date()))
                .setMeetLocation("Here")
                .setMeetTime(ISO_DATA_FORMAT.format(new Date()))
                .setNotes("No notes")
                .setCategory("American").getId());
    }

    @Test
    public void testNewChow() {
        testChow = new Chows();

        //fresh end  should be null
        assertEquals(0, testChow.getId());
        assertNull(testChow.getFood());
        assertEquals(false, testChow.isDeleted());
        assertNull(testChow.getLastUpdated());
        assertNull(testChow.getMeetLocation());
        assertNull(testChow.getMeetTime());
        assertNull(testChow.getNotes());
        assertNull(testChow.getCategory());
        assertNull(testChow.getPosterUser());
        assertNull(testChow.getPosterName());
        assertNull(testChow.getPosterEmail());
        assertNull(testChow.getJoinedUser());
        assertNull(testChow.getJoinedName());
        assertNull(testChow.getJoinedEmail());

        testChow.setPosterUser("postTester")
                .setPosterName("postTester")
                .setPosterEmail("postTester@gmail.com");

        assertEquals("postTester", testChow.getPosterName());
        assertEquals("postTester", testChow.getPosterUser());
        assertEquals("postTester@gmail.com", testChow.getPosterEmail());
    }

    @Test
    public void testFoodNameChanged() {
        // set the start time after grabbing it
        testChow = new Chows()
                .setId(-1)
                .setPosterUser("postTester")
                .setPosterName("postTester")
                .setPosterEmail("postTester@gmail.com")
                .setDeleted(false)
                .setFood("Donuts")
                .setLastUpdated(ISO_DATA_FORMAT.format(new Date()))
                .setMeetLocation("Here")
                .setMeetTime(ISO_DATA_FORMAT.format(new Date()))
                .setNotes("No notes")
                .setCategory("American");

        assertEquals("Donuts", testChow.getFood());

        testChow.setFood("Timbits");

        assertEquals("Timbits", testChow.getFood());
    }

    @Test
    public void testDeletedChanged() {
        // set the start time after grabbing it
        testChow = new Chows()
                .setId(-1)
                .setPosterUser("postTester")
                .setPosterName("postTester")
                .setPosterEmail("postTester@gmail.com")
                .setDeleted(false)
                .setFood("TestFood")
                .setLastUpdated(ISO_DATA_FORMAT.format(new Date()))
                .setMeetLocation("Here")
                .setMeetTime(ISO_DATA_FORMAT.format(new Date()))
                .setNotes("No notes")
                .setCategory("American");

        assertEquals(false, testChow.isDeleted());

        testChow.setDeleted(true);

        assertEquals(true, testChow.isDeleted());
    }


    @After
    public void tearDown() {

    }
}
