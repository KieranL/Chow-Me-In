import com.chowpals.chowmein.CreateChowFromMainActivityWithLoginTest;
import com.chowpals.chowmein.LoginAndOutTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CreateChowFromMainActivityWithLoginTest.class, LoginAndOutTest.class})
public class InstrumentationTestSuite {
}