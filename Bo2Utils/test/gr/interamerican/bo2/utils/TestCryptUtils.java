package gr.interamerican.bo2.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CryptUtils}
 */
public class TestCryptUtils {
	
	/**
	 * Test getMd5
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetMd5() {
		String md5 = CryptUtils.getMd5("lala".getBytes());
		Assert.assertEquals(32, md5.length());
		String actual = md5.replaceAll("[0-9A-F]", StringConstants.EMPTY);
		Assert.assertEquals(StringConstants.EMPTY, actual);
	}

}
