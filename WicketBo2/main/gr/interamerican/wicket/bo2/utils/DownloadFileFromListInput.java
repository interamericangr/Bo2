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
package gr.interamerican.wicket.bo2.utils;

import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.util.List;
import java.util.Map;

/**
 * Input for {@link Bo2WicketUtils#createDownloadFileFromList(DownloadFileFromListInput)}.
 * 
 * @deprecated Due to deprecated usages 
 */
@Deprecated
public interface DownloadFileFromListInput {
	/**
	 * Names of the properties to export.
	 * 
	 * @return Returns an array with the properties to
	 *         export to the CSV file.
	 */
	public String[] getPropertiesToExport();

	/**
	 * Column labels.
	 *
	 * @return Returns an array with the labels of the columns.
	 */
	public String[] getColumnLabels();
	
	/**
	 * List with elements to export.
	 * 
	 * @return Returns a list with the elements to export.
	 */
	public List<?> getList();
	
	/**
	 * Gets the special formatters.
	 *
	 * @return Returns a Map with special formatters for {@link #getPropertiesToExport()}.
	 *         The map Key is the property index.
	 */
	public Map<Integer, Formatter<?>> getSpecialFormatters();
	
	/**
	 * Gets the first rows.
	 *
	 * @return Returns rows to write before anything else.
	 */
	public List<String> getFirstRows();
	
	/**
	 * Gets the last rows.
	 *
	 * @return Returns rows to write after everything else.
	 */
	public List<String> getLastRows();
	
	/**
	 * Name of the file the user downloads.
	 * 
	 * @return Returns the name of the CSV file the user downloads.
	 */
	public String getDownloadedFileName();

}
