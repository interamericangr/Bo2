package gr.interamerican.bo2.impl.corp;

import gr.interamerican.bo2.impl.corp.hconnect.TestSuiteImplCorpHconnect;
import gr.interamerican.bo2.impl.corp.utils.TestSuiteImplCorpUtils;

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
		TestZLinuxConnectionStrategy.class,
		TestJotmZLinuxConnectionStrategy.class,
		TestSuiteImplCorpHconnect.class,		
		TestSuiteImplCorpUtils.class
	}
)
public class TestSuiteImplCorp {
	/* empty */
	
}
