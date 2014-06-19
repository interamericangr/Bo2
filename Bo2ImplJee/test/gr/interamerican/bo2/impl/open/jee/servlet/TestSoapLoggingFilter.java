package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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
	
	/**
	 * Test doFilter()
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testDoFilter() throws IOException, ServletException {
		SoapLoggingFilter subject = new SoapLoggingFilter();
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer());
		ServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain chain = Mockito.mock(FilterChain.class);
		subject.doFilter(request, response, chain);
	}

}
