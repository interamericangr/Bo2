package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.RegexUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.xml.DomUtils;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

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
	 * UPPERCASE regex for elements to omit from logging (e.g. passwords)
	 */
	@SuppressWarnings("nls")
	static final String[] OMITTED_ELEMENTS = new String[]{"PASSWORD", "PASSWD"};

	@Override
	protected void doLog(String url, Charset requestEncoding, Charset responseEncoding, byte[] request, byte[] response) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"REQUEST" + StringConstants.NEWLINE,
			url + StringConstants.NEWLINE,
			logSoap(request),
			"RESPONSE" + StringConstants.NEWLINE,
			logSoap(response));
		
		LOGGER.info(msg);
	}
	
	/**
	 * Returns the SOAP message.
	 * 
	 * @param soap
	 * 
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
			
			for(String omit : OMITTED_ELEMENTS) {
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
	 * @param e
	 * @param soap
	 * @param mayLog 
	 * @return SOAP string assuming bytes encoded as UTF-8
	 */
	@SuppressWarnings("nls")
	String handle(Throwable e, byte[] soap, boolean mayLog) {
		LOGGER.error(e.getMessage() + " while parsing SOAP. Returned as UTF-8");
		if(mayLog) {
			return new String(soap, Charset.forName("UTF-8"));
		}
		return "Failed to manipulate SOAP for logging";
	}
	
}
