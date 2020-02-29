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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.impl.open.operations.functional.EntitiesQuery2CsvPrintStreamOperation;
import gr.interamerican.bo2.impl.open.workers.IteratorQuery;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * A {@link SerializableRunnable} that will convert the data provided by
 * {@link #getData()} to a csv with the
 * {@link EntitiesQuery2CsvPrintStreamOperation} and send it to be download by
 * the used with the use of
 * {@link WicketUtils#sendFileForDownload(byte[], String)}.
 * 
 * @param <T>
 *            Type of Entity that provides data to the csv
 */
public abstract class AbstractCsvAction<T>
implements SerializableRunnable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 233L;

	/**
	 * The Input of this
	 */
	private final ExportDataSetup<T> input;

	/**
	 * Name of the downloaded file
	 */
	private final String downloadedFileName;

	/**
	 * Public Constructor.
	 * 
	 * @param input
	 *            The Input of this
	 * @param downloadedFileName
	 *            Name of the downloaded file
	 */
	public AbstractCsvAction(ExportDataSetup<T> input, String downloadedFileName) {
		this.input = input;
		this.downloadedFileName = downloadedFileName;
	}

	@Override
	public void run() {
		List<T> data = getData();
		if (CollectionUtils.isNullOrEmpty(data)) {
			return;
		}
		try {
			EntitiesQuery<T> query = new IteratorQuery<T>(data.iterator());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream output = new PrintStream(out, false, Bo2UtilsEnvironment.get().getDefaultTextCharset().name());
			EntitiesQuery2CsvPrintStreamOperation<T, EntitiesQuery<T>> toCsv = new EntitiesQuery2CsvPrintStreamOperation<>(
					query, input, output);
			Bo2WicketRequestCycle.execute(toCsv);
			WicketUtils.sendFileForDownload(out.toByteArray(), downloadedFileName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieves the data of the file.
	 * 
	 * @return List of items to display as csv
	 */
	abstract List<T> getData();
}