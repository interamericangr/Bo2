package gr.interamerican.bo2.utils;

import org.junit.Test;

/**
 * {@link ZipUtils} test
 */
public class TestZipUtils {
	
	/**
	 * Test zipFlatDir
	 * 
	 * TODO: make this test PC independent
	 */
	@SuppressWarnings("nls")
//	@Test
	public void testZipFlatDir() {
		ZipUtils.zipFlatDir("/tmp/zipme", "/tmp/zipme.zip");
	}
	
}
