package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests of {@link AbstractBaseLoggingFilter}.
 */
public class TestAbstractBaseLoggingFilter {
	
	/**
	 * Test doFilter()
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testDoFilter() throws IOException, ServletException {
		AbstractBaseLoggingFilter subject = new AbstractBaseLoggingFilter() {
			@Override
			protected void doLog(String url, Charset requestEncoding, Charset responseEncoding, byte[] request, byte[] response) {
				//empty
			}
		};
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer());
		ServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain chain = Mockito.mock(FilterChain.class);
		subject.doFilter(request, response, chain);
	}

}
