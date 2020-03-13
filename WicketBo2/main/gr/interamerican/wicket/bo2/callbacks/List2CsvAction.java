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

import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.wicket.bo2.utils.Bo2WicketUtils;
import gr.interamerican.wicket.bo2.utils.DownloadFileFromListInput;

/**
 * This action will export the elements of a list to a 
 * CSV file.
 * @deprecated Use {@link ListBasedCsvAction}
 */
@Deprecated
public class List2CsvAction 
implements SerializableRunnable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Client.
	 */
	private DownloadFileFromListInput client;
	
	/**
	 * Creates a new List2CsvAction object. 
	 *
	 * @param client the client
	 */
	public List2CsvAction(DownloadFileFromListInput client) {
		this.client = client;
	}

	@Override
	public void run() {
		Bo2WicketUtils.createDownloadFileFromList(client);
	}
}