package gr.interamerican.bo2.utils;

import java.util.Properties;

import org.junit.Assert;

import org.junit.AfterClass;
import org.junit.Test;

import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

/**
 * The Class TestBo2UtilsDeployment.
 */
public class TestBo2UtilsDeployment {
	
	/**
	 * reset env.
	 */
	@AfterClass
	public static void teardown() {		
		Bo2UtilsEnvironment.set(null);		
	}

	
	/**
	 * Test for set(p).
	 */
	@Test
	public void testSet() {
		String sdf = "dd-MM-yyyy"; //$NON-NLS-1$
		String ldf = Bo2UtilsEnvironment.get().getLongDateFormatPattern();
		Properties p = new Properties();
		p.setProperty("shortDateFormat", sdf);		 //$NON-NLS-1$
		Bo2UtilsEnvironment.set(p);
		String actual = Bo2UtilsEnvironment.get().getShortDateFormatPattern();
		Assert.assertEquals(sdf, actual);
		Assert.assertEquals(ldf, Bo2UtilsEnvironment.get().getLongDateFormatPattern());
	}
	
	

}
