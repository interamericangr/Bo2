package gr.interamerican.bo2.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ZipUtils} test
 */
public class TestZipUtils {
	
	/**
	 * Test zipFlatDir
	 * 
	 * TODO: make this test PC independent
	 * @throws IOException 
	 */
	@SuppressWarnings("nls")
//	@Test
	public void testZipFlatDir() throws IOException {
		ByteArrayOutputStream baos = ZipUtils.zipFlatDir("/tmp/zipme");
		byte[] zip = baos.toByteArray();
		Assert.assertTrue(zip.length > 0);
		
		FileOutputStream fos = new FileOutputStream("/tmp/zipme.zip");
		fos.write(zip);
		fos.flush();
		fos.close();
	}
	
}
