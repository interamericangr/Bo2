package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link SoapLoggingFilter}
 */
public class TestSoapLoggingFilter {
	
	/**
	 * test logSoap
	 * @throws IOException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLogSoap() throws IOException {
		SoapLoggingFilter subject = new SoapLoggingFilter();
		InputStream stream = StreamUtils.class.getResourceAsStream("/gr/interamerican/bo2/impl/open/jee/servlet/sampleSoapRequest.txt");
		InputStreamReader isr = new InputStreamReader(stream, Bo2UtilsEnvironment.getDefaultResourceFileCharset());
		BufferedReader br = new BufferedReader(isr);
		String document = "";
		String str;
		while((str = br.readLine()) != null) {
			document += str;
		}
		byte[] bytes = document.getBytes(Bo2UtilsEnvironment.getDefaultResourceFileCharset());

		String s = subject.logSoap(bytes);
		
		for(String omitted : SoapLoggingFilter.OMITTED_ELEMENTS) {
			Assert.assertFalse(s.contains(omitted));
		}
		
	}

}
