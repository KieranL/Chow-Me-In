import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import TestSuites.HelperTests;
import TestSuites.ObjectTests;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({HelperTests.class, ObjectTests.class})
public class RunAllTests {
}