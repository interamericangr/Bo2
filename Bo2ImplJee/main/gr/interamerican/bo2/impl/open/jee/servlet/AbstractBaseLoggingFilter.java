package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base {@link Filter} that records HTTP request-response messages.
 * <br/>
 * Implementors of concrete extensions of this base class, should
 * implement {@link #doLog(String, Charset, Charset, byte[], byte[])}
 */
public abstract class AbstractBaseLoggingFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
	throws IOException, ServletException {
		
		String url = StringConstants.EMPTY;

		ServletRequest requestWrapper = request;
		ServletResponse responseWrapper = response;
		
		Charset requestCharset = Charset.defaultCharset();
		Charset responseCharset = Charset.defaultCharset();
		
		byte[] requestPayload = null;
		byte[] responsePayload = null;
		
		if (request instanceof HttpServletRequest) {
			requestWrapper = new RecordingServletRequestWrapper((HttpServletRequest) request);
			url = ((HttpServletRequest) request).getRequestURL().toString();
		}

		if (response instanceof HttpServletResponse) {
			responseWrapper = new RecordingServletResponseWrapper((HttpServletResponse) response);
		}
		
		chain.doFilter(requestWrapper, responseWrapper);
		
		if(requestWrapper instanceof RecordingServletRequestWrapper) {
			requestPayload = ((RecordingServletRequestWrapper) requestWrapper).getPayload();
		}
		
		if(responseWrapper instanceof RecordingServletResponseWrapper) {
			responsePayload = ((RecordingServletResponseWrapper) responseWrapper).getPayload();
		}
		
		if(!StringUtils.isNullOrBlank(request.getCharacterEncoding())) {
			requestCharset = Charset.forName(request.getCharacterEncoding());
		}
		
		if(!StringUtils.isNullOrBlank(response.getCharacterEncoding())) {
			responseCharset = Charset.forName(response.getCharacterEncoding());
		}
		
		doLog(url, requestCharset, responseCharset, requestPayload, responsePayload);

	}

	public void init(FilterConfig filterConfig) throws ServletException { /* empty */ }
	public void destroy() { /* empty */	}
	
	/**
	 * Data necessary to perform the logging.
	 * 
	 * @param url 
	 *        request url
	 * @param requestEncoding
	 *        {@link ServletRequest#getCharacterEncoding()} {@link Charset}
	 *        or {@link Charset#defaultCharset()}, if the first is not available.
	 * @param responseEncoding
	 *        {@link ServletResponse#getCharacterEncoding()} {@link Charset}
	 *        or {@link Charset#defaultCharset()}, if the first is not available.
	 * @param request
	 *        Recorded raw bytes of the request. Null values are allowed.
	 * @param response
	 *        Recorded raw bytes of the response. Null values are allowed.
	 */
	protected abstract void doLog(String url, Charset requestEncoding, Charset responseEncoding, byte[] request, byte[] response);

}
