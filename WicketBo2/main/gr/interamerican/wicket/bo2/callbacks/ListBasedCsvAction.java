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
package gr.interamerican.wicket.bo2.callbacks;

import java.util.List;

import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;

/**
 * An {@link AbstractCsvAction} that is based on a {@link List} provided in the
 * constructor.
 * 
 * @param <T>
 *            Type of Entity that provides data to the csv
 */
public class ListBasedCsvAction<T> extends AbstractCsvAction<T> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List with the items contained in the file
	 */
	private final List<T> data;

	/**
	 * Public Constructor.
	 *
	 * @param input
	 *            The Input of this
	 * @param downloadedFileName
	 *            Name of the downloaded file
	 * @param data
	 *            List with the items contained in the file
	 */
	public ListBasedCsvAction(ExportDataSetup<T> input, String downloadedFileName, List<T> data) {
		super(input, downloadedFileName);
		this.data = data;
	}

	@Override
	List<T> getData() {
		return data;
	}
}