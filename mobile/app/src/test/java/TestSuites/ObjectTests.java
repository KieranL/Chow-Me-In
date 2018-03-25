package TestSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ObjectTests.APISuccessObjectTest;
import ObjectTests.ChowTest;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({APISuccessObjectTest.class, ChowTest.class})
public class ObjectTests {
}