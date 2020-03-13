package gr.interamerican.bo2.utils;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ZipUtils} test.
 */
public class TestZipUtils {
	
	/**
	 * Test zipFlatDir<br>
	 * TODO : not a good work-around
	 */
	@SuppressWarnings("nls")
	@Test
	public void testZipFlatDir() {
		ByteArrayOutputStream baos = ZipUtils.zipFlatDir(System.getProperty("user.dir"));
		byte[] zip = baos.toByteArray();
		Assert.assertTrue(zip.length > 0);

//		try (FileOutputStream fos = new FileOutputStream("C:\\zipme.zip")) {
//			fos.write(zip);
//			fos.flush();
//		}
	}
}