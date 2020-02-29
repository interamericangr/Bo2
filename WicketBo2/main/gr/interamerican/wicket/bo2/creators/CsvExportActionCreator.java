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
package gr.interamerican.wicket.bo2.creators;

import java.util.List;

import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.wicket.bo2.callbacks.SupplierBasedCsvAction;
import gr.interamerican.wicket.creators.ExportActionCreator;

/**
 * An {@link ExportActionCreator} that creates CSV files.
 * 
 * @param <T>
 *            Type of Entity to be reported
 */
public class CsvExportActionCreator<T> implements ExportActionCreator<T> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 252L;

	/**
	 * File Name
	 */
	String downloadedFileName;

	/**
	 * Public Constructor.
	 * 
	 * @param fileName
	 *            File Name without the extension
	 */
	public CsvExportActionCreator(String fileName) {
		this.downloadedFileName = fileName + ".csv"; //$NON-NLS-1$
	}

	@Override
	public SerializableRunnable getCreator(ExportDataSetup<T> input, SerializableSupplier<List<T>> data) {
		return new SupplierBasedCsvAction<>(input, downloadedFileName, data);
	}
}