package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Concrete extension of {@link AbstractBaseLoggingFilter} for
 * logging of SOAP request-response messages. 
 */
public class SoapLoggingFilter extends AbstractBaseLoggingFilter {
	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SoapLoggingFilter.class.getName());

	@Override
	protected void doLog(Charset requestEncoding, Charset responseEncoding, byte[] request, byte[] response) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"REQUEST" + StringConstants.NEWLINE,
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
	private String logSoap(byte[] soap) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(soap);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(bis);
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			/*
			 * Presumably the XML parser should auto-detect the correct
			 * encoding.
			 */
//			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(document), new StreamResult(sw));
			return sw.toString();
		} catch (TransformerConfigurationException e) {
			/* ok */
		} catch (IllegalArgumentException e) {
			/* ok */
		} catch (ParserConfigurationException e) {
			/* ok */
		} catch (SAXException e) {
			/* ok */
		} catch (IOException e) {
			/* ok */
		} catch (TransformerFactoryConfigurationError e) {
			/* ok */
		} catch (TransformerException e) {
			/* ok */
		}
		return "SOAP parsing failed";
	}

}
