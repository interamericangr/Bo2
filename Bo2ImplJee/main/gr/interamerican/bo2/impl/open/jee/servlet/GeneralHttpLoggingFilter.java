package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete extension of {@link AbstractBaseLoggingFilter} for
 * logging of general HTTP request-response messages. The request
 * response character encodings are used respectively to encode
 * the bytes to a String for logging.
 */
public class GeneralHttpLoggingFilter extends AbstractBaseLoggingFilter {
	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralHttpLoggingFilter.class.getName());

	@Override
	protected void doLog(String url, Charset requestEncoding, Charset responseEncoding, byte[] request, byte[] response) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"REQUEST" + StringConstants.NEWLINE,
			logHttp(request, requestEncoding),
			"RESPONSE" + StringConstants.NEWLINE,
			logHttp(response, responseEncoding));
		
		LOGGER.info(msg);
	}
	
	/**
	 * Returns the HTTP message.
	 * 
	 * @param bytes
	 * @param charset
	 * 
	 * @return Returns the HTTP message.
	 */
	private String logHttp(byte[] bytes, Charset charset) {
		if(bytes==null) {
			return StringConstants.EMPTY;
		}
		return new String(bytes, charset);
	}

}
