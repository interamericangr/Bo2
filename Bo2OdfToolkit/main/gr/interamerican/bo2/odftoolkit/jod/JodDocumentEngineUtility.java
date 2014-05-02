package gr.interamerican.bo2.odftoolkit.jod;

import gr.interamerican.bo2.odftoolkit.AbstractDocumentEngineUtility;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentEngineUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * Implementation of {@link DocumentEngineUtility} based on the existence
 * LibreOffice daemon listening on a TCP/IP port/address.
 */
public class JodDocumentEngineUtility extends AbstractDocumentEngineUtility {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(JodDocumentEngineUtility.class.getName());
	
	/**
	 * Creates a new JodPdfEngine object. 
	 *
	 * @param properties
	 */
	public JodDocumentEngineUtility(Properties properties) {
		super(properties);
		if(StringUtils.isNullOrBlank(host) || StringUtils.isNullOrBlank(port)) {
			host="localhost"; //$NON-NLS-1$
			port="8100"; //$NON-NLS-1$
		}
	}

	public String toHtml(byte[] odf) throws DocumentEngineException {
		OpenOfficeConnection connection = null;
		try {			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = new ByteArrayInputStream(odf);			
			connection = new SocketOpenOfficeConnection(host, NumberUtils.string2Int(port));
			connection.connect();
			DefaultDocumentFormatRegistry registry = new DefaultDocumentFormatRegistry();
			DocumentFormat inFormat = registry.getFormatByFileExtension("odt"); //$NON-NLS-1$
			DocumentFormat outFormat = registry.getFormatByFileExtension("XHTML"); //$NON-NLS-1$			
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);			
			converter.convert(in, inFormat, out, outFormat);
			String xhtml = new String(out.toByteArray(), Charset.forName("UTF-8")); //$NON-NLS-1$
			return xhtml;
		} catch (Exception e) { //log and rethrow all exceptions
			LOG.error(ExceptionUtils.getThrowableStackTrace(e));
			throw new DocumentEngineException(e);
		} finally {
			if(connection!=null && connection.isConnected()) {
				connection.disconnect();
			}
		}
	}

	public byte[] toPdf(byte[] odf) throws DocumentEngineException {
		OpenOfficeConnection connection = null;
		try {			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = new ByteArrayInputStream(odf);			
			connection = new SocketOpenOfficeConnection(host, NumberUtils.string2Int(port));
			connection.connect();
			DefaultDocumentFormatRegistry registry = new DefaultDocumentFormatRegistry();
			DocumentFormat inFormat = registry.getFormatByFileExtension("odt"); //$NON-NLS-1$
			DocumentFormat outFormat = registry.getFormatByFileExtension("pdf"); //$NON-NLS-1$			
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);			
			converter.convert(in, inFormat, out, outFormat);
			byte[] pdf = out.toByteArray();
			return pdf;
		} catch (Exception e) { //log and rethrow all exceptions
			LOG.error(ExceptionUtils.getThrowableStackTrace(e));
			throw new DocumentEngineException(e);
		} finally {
			if(connection!=null && connection.isConnected()) {
				connection.disconnect();
			}
		}
	}

}
