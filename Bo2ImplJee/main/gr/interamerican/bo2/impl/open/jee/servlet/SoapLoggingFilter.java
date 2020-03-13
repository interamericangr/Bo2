package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.RegexUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.xml.DomUtils;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Concrete extension of {@link AbstractBaseLoggingFilter} for
 * logging of SOAP request-response messages. 
 */
public class SoapLoggingFilter extends AbstractBaseLoggingFilter {
	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SoapLoggingFilter.class.getName());
	
	/**
	 * Error message.
	 */
	static final String ERROR_MESSAGE = "Failed to manipulate SOAP for logging. Message was: \n"; //$NON-NLS-1$
	
	/** caused by. */
	static final String CAUSED_BY = "caused by: "; //$NON-NLS-1$
	
	/**
	 * Filter init param for elements omitted from logging (e.g. passwords)
	 * Values are comma separated
	 */
	static final String OMITTED_ELEMENTS_INIT_PARAM = "omittedElements"; //$NON-NLS-1$
	
	/**
	 * UPPERCASE elements to omit from logging (e.g. passwords)
	 */
	@SuppressWarnings("nls")
	static final String[] OMITTED_ELEMENTS = new String[]{"password", "passwd"};
	
	/**
	 * All omitted elements.
	 */
	Set<String> omittedElements = new HashSet<String>();
	
	/**
	 * Creates a new SoapLoggingFilter object. 
	 *
	 */
	public SoapLoggingFilter() {
		for (String s : OMITTED_ELEMENTS) { //standard omissions
			omittedElements.add(s);
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		
		String parameterizedOmissions = filterConfig.getInitParameter(OMITTED_ELEMENTS_INIT_PARAM);
		if(StringUtils.isNullOrBlank(parameterizedOmissions)) {
			return;
		}
		
		for (String s : TokenUtils.splitTrim(parameterizedOmissions, StringConstants.COMMA)) { //parameterized omissions
			omittedElements.add(s);
		}
	}

	@Override
	protected void doLog(String url, Charset requestEncoding, Charset responseEncoding, byte[] request, byte[] response) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"REQUEST " + url + StringConstants.NEWLINE,
			logSoap(request),
			StringConstants.NEWLINE + "RESPONSE" + StringConstants.NEWLINE,
			logSoap(response) + StringConstants.NEWLINE,
			"-------------------------------------------------------------------------------",
			StringConstants.NEWLINE);
		
		logger().info(msg);
	}
	
	/**
	 * Allows to create sub-types that log elsewhere.
	 * 
	 * @return Logger to log with.
	 */
	protected Logger logger() {
		return LOGGER;
	}
	
	/**
	 * Returns the SOAP message.
	 *
	 * @param soap the soap
	 * @return Returns the SOAP message.
	 */
	@SuppressWarnings("nls")
	String logSoap(byte[] soap) {
		if(soap==null || soap.length==0) {
			return "Empty document";
		}
		
		boolean mayLog = false;
		
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(soap);
			Document document = DomUtils.getDocument(bis);
			
			for(String omit : omittedElements) {
				String omitRegex = RegexUtils.ZERO_OR_MORE_CHARS + omit; //may be something like ns2:password
				List<Node> matchedNodes = DomUtils.matchedNodes(document, omitRegex);
				for(Node node : matchedNodes) {
					node.getParentNode().removeChild(node);
				}
			}
			
			/*
			 * Now the soap may be logged regardless of what happens next.
			 */
			mayLog = true;
			
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			/*
			 * Presumably the XML parser auto-detects the correct encoding.
			 * The XML document should be encoded in UTF-8, UTF-16 or an older
			 * encoding these are backwards compatible with.
			 * Note that ISO8859-7 (or windows-1253) with Greek characters is 
			 * not parsed by the JAXP parsers.
			 */
//			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(document), new StreamResult(sw));
			return sw.toString();
		} catch (Exception e) {
			return handle(e, soap, mayLog);
		}
	}
	
	/**
	 * Handle SOAP parsing exception.
	 *
	 * @param e the e
	 * @param soap the soap
	 * @param mayLog the may log
	 * @return SOAP string assuming bytes encoded as UTF-8
	 */
	@SuppressWarnings("nls")
	String handle(Throwable e, byte[] soap, boolean mayLog) {
		if(mayLog) {
			return new String(soap, Charset.forName("UTF-8"));
		}
		
		String message = new String(soap, Charset.forName("UTF-8"));
		try {
			int start = message.indexOf("<?xml");
			if(start > 0) {
				int end = message.lastIndexOf('>');
				String actualSoapMsg = message.substring(start, end+1);
				byte[] actualSoap = actualSoapMsg.getBytes(Charset.forName("UTF-8"));
				return logSoap(actualSoap);
			}
		}
		catch (RuntimeException rtex) {
			//ok, just fall through
		}
		
		return ERROR_MESSAGE + message + StringConstants.NEWLINE + CAUSED_BY + e.getMessage();
	}
	
}
