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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.namedstreams.CsvRecordQuery;
import gr.interamerican.bo2.impl.open.namedstreams.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.impl.open.transformations.CsvRecord2RowTags;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Converts a CsvRecord to an HTML file with a table.
 * 
 * The operation takes as input a CSV file stream and
 * a stream that contains the CSS file for the output
 * HTML file. The CSS will be included in the output
 * file.
 */
public class CsvQuery2HtmlTableOperation 
extends QueryPrinterOperation<CsvRecord, CsvRecordQuery>{
	
	/**
	 * Named stream for css.
	 */
	private NamedStream<?> css;
	
	/**
	 * Named stream for tableAttributes.
	 */
	private NamedInputStream tableAttributesStream;
	
	/**
	 * Logical name of css stream. 
	 */
	private String cssName;
	
	/**
	 * Logical name of tableAttributes stream. 
	 */
	private String tableAttributesName;
	
	/**
	 * Properties. 
	 */
	private Properties tableAttributes;
	
	
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
		css = nsp.getStream(cssName);
		tableAttributesStream = (NamedInputStream) nsp.getStream(tableAttributesName);		
	}
	
	

	/**
	 * Creates a new CsvQuery2HtmlTableOperation object. 
	 * 
	 * @param columnCount 
	 *        Count of CSV file columns.
	 * @param separator 
	 *        Column separator in the CSV file.
	 * @param streamsBasicName
	 *        Base for the logical name of the streams. The logical name 
	 *        of the streams used by this operation are created with the
	 *        addition of an extension to this basic name. <br/>
	 *        The extension <code>csv</code> is added in order to create 
	 *        the input CSV file logical name. <br/>
	 *        The extension <code>css</code> is added in order to create 
	 *        the logical name of the input CSS file that contains the 
	 *        style sheet information. <br/>
	 *        The extension <code>properties</code> is added in order to create 
	 *        the logical name of the properties file that keets the attributes
	 *        of the HTML table. <br/>

	 */
	@SuppressWarnings("nls")
	public CsvQuery2HtmlTableOperation (
			int columnCount, char separator, String streamsBasicName) {
		
		super(new CsvRecordQuery(columnCount, separator), 
			  new CsvRecord2RowTags(columnCount), 
			  new CsvRecord2RowTags(columnCount, "tr", "th"),
			  streamsBasicName+".csv");
		this.query.setManagerName(this.getManagerName());
		this.query.setStreamName(streamsBasicName+".csv");
		this.cssName = streamsBasicName+".css";
		this.tableAttributesName = streamsBasicName+".properties";
		this.outputName = streamsBasicName+".html";	
	}
	
	/**
	 * Creates the table tag.
	 * 
	 * @return Returns the table tag.
	 */
	String tableTag() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table "); //$NON-NLS-1$
		boolean notFirst = false;
		for (Map.Entry<Object, Object> attribute : this.tableAttributes.entrySet()) {
			if (notFirst) {
				sb.append(StringConstants.COMMA);
				sb.append(StringConstants.SPACE);
			}
			String name = (String) attribute.getKey();
			String value = (String) attribute.getValue();
			value = StringUtils.surround(value, StringConstants.DOUBLE_QUOTE);
			sb.append(name);
			sb.append(StringConstants.EQUALS);
			sb.append(value);
		}
		sb.append(">"); //$NON-NLS-1$
		return sb.toString();
	}
	
	@Override
	public void setManagerName(String managerName) {		
		super.setManagerName(managerName);
		query.setManagerName(managerName);
	}
	
	/**
	 * Loads the table attributes.
	 * 
	 * @throws DataException 
	 */
	private void readTableAttributes() throws DataException {		
		try {
			InputStream in = tableAttributesStream.getStream();	
			tableAttributes = new Properties();
			tableAttributes.load(in);
		} catch (IOException e) {
			throw new DataException(e);
		}
	}
	
	@Override
	protected void beforeQuery() throws LogicException, DataException {		
		readTableAttributes();
		out.writeString("<html>");		 //$NON-NLS-1$		
		out.writeString("<head>");		 //$NON-NLS-1$
		String s = css.readString();
		while (s!=null) {
			out.writeString(s);
			s = css.readString();			
		} 
		out.writeString("</head>");		 //$NON-NLS-1$
		out.writeString("<body>"); //$NON-NLS-1$		
		out.writeString(tableTag()); 
	}
	
	
	@Override
	protected void afterQuery() throws LogicException, DataException {
		out.writeString("</table></body></html>");	 //$NON-NLS-1$
	}
	
	@Override
	protected CsvRecord getCurrentRow() {
		return (CsvRecord) query.getRecord();
	}

}
