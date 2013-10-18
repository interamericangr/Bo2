package gr.interamerican.bo2.legacy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



/**
 * Test suite for package <code>gr.interamerican.bo2</code>.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{	
		TestLegacyBoProviderImpl.class,
		TestLegacyBoUtils.class,
		TestLegacyOperationAdapter.class,
		TestLegacyPwAdapter.class,
		TestLegacyWorkerAdapter.class,
		TestLegacyQueryAdapter.class,
		TestLegacyQuestionAdapter.class,
	}
)
public class TestSuiteLegacy {
	/* empty */
}
