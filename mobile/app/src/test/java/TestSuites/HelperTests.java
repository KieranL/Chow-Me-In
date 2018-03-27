package TestSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import HelperTests.ChowHelperTest;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ChowHelperTest.class})
public class HelperTests {
}