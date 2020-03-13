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
 * A {@link DownloadFileFromListInput} with setters as well.
 * 
 * @deprecated Due to deprecated usages
 */
@Deprecated
public interface DownloadFileFromListBean extends DownloadFileFromListInput {

	/**
	 * Sets the Names of the properties to export.
	 * 
	 * @param propertiesToExport
	 *            an array with the properties to export to the CSV file.
	 */
	public void setPropertiesToExport(String[] propertiesToExport);

	/**
	 * Sets the Column labels.
	 * 
	 * @param columnLabels
	 *            an array with the labels of the columns.
	 */
	public void setColumnLabels(String[] columnLabels);

	/**
	 * Sets a List with elements to export.
	 * 
	 * @param list
	 *            a list with the elements to export
	 */
	public void setList(List<?> list);

	/**
	 * Sets a Map with special formatters for
	 * {@link #setPropertiesToExport(String[])}. The map Key is the property
	 * index.
	 * 
	 * @param specialFormatters
	 *            Map with special formatters
	 */
	public void setSpecialFormatters(Map<Integer, Formatter<?>> specialFormatters);

	/**
	 * Sets the rows to write before anything else.
	 * 
	 * @param firstRows
	 *            rows to write before anything else
	 */
	public void setFirstRows(List<String> firstRows);

	/**
	 * Sets the rows to write after everything else.
	 * 
	 * @param lastRows
	 *            rows to write after everything else
	 */
	public void setLastRows(List<String> lastRows);

	/**
	 * Sets the Name of the file the user downloads.
	 * 
	 * @param downloadedFileName
	 *            name of the CSV file the user downloads.
	 */
	public void setDownloadedFileName(String downloadedFileName);
}