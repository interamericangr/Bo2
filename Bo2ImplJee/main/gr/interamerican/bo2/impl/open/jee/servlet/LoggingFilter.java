package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Filter} that records HTTP request-response messages.
 */
public class LoggingFilter implements Filter {
	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class.getName()); 

	@SuppressWarnings("nls")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
	throws IOException, ServletException {

		StringBuilder sb = new StringBuilder();
		
		ServletRequest requestWrapper = request;
		ServletResponse responseWrapper = response;
		
		if (request instanceof HttpServletRequest) {
			requestWrapper = new RecordingServletRequestWrapper((HttpServletRequest) request);
		}

		if (response instanceof HttpServletResponse) {
			responseWrapper = new RecordingServletResponseWrapper((HttpServletResponse) response);
		}
		
		chain.doFilter(requestWrapper, responseWrapper);
		
		if(requestWrapper instanceof RecordingServletRequestWrapper) {
			sb.append("REQUEST\n");
			sb.append(((RecordingServletRequestWrapper) requestWrapper).getPayload() + "\n");
		}
		
		if(responseWrapper instanceof RecordingServletResponseWrapper) {
			sb.append("RESPONSE\n");
			sb.append(((RecordingServletResponseWrapper) responseWrapper).getPayload() + "\n");
		}
		
		LOGGER.info(sb.toString());

	}

	public void init(FilterConfig filterConfig) throws ServletException { /* empty */ }
	public void destroy() { /* empty */	}

}
