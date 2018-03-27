import com.chowpals.chowmein.CreateChowFromMainActivityWithLoginTest;
import com.chowpals.chowmein.LoginAndOutTest;
import com.chowpals.chowmein.SearchFromMainActivityLoginTest;
import com.chowpals.chowmein.SearchFromMainActivityNoLoginTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CreateChowFromMainActivityWithLoginTest.class, LoginAndOutTest.class, SearchFromMainActivityNoLoginTest.class,SearchFromMainActivityLoginTest.class})
public class InstrumentationTestSuite {
}