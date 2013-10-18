/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.odftoolkit.pdf;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.doc.PdfEngine;
import gr.interamerican.bo2.utils.doc.PdfEngineException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
import java.util.Properties;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * PDF converter based on JOD.
 */
public class JodPdfEngine extends AbstractPdfEngine
implements PdfEngine {
	
	/**
	 * Creates a new JodPdfEngine object. 
	 *
	 * @param properties
	 */
	public JodPdfEngine(Properties properties) {
		super(properties);
		if(StringUtils.isNullOrBlank(host) || StringUtils.isNullOrBlank(port)) {
			host="localhost"; //$NON-NLS-1$
			port="8100"; //$NON-NLS-1$
		}
	}

	@Override
	public byte[] toPdf(byte[] odf) throws PdfEngineException {
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
		} catch (ConnectException e) {
			throw new PdfEngineException(e);
		} finally {
			if(connection!=null && connection.isConnected()) {
				connection.disconnect();
			}
		}
	}

}
