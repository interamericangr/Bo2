package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests of {@link LoggingFilter}.
 */
public class TestLoggingFilter {
	
	/**
	 * Test doFilter()
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testDoFilter() throws IOException, ServletException {
		LoggingFilter subject = new LoggingFilter();
		
		ServletRequest request = Mockito.mock(HttpServletRequest.class);
		ServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain chain = Mockito.mock(FilterChain.class);
		subject.doFilter(request, response, chain);
	}

}
