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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.namedstreams.StreamType;
import gr.interamerican.bo2.impl.open.operations.EntitiesQuery2CsvOperation;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.IteratorQuery;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.bo2.callbacks.clients.List2CsvActionClient;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.util.resource.InputStreamAsResourceStream;

import java.util.List;
import java.util.Map;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * This action will export the elements of a list to a 
 * CSV file.
 */
public class List2CsvAction 
extends Bo2WicketBlock {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logical name of output stream.
	 */
	private static final String CSV_NAME = "List2CsvAction.out."; //$NON-NLS-1$
	
	/**
	 * Client.
	 */
	List2CsvActionClient client;	
	
	/**
	 * Creates a new List2CsvAction object. 
	 *
	 * @param client
	 */
	public List2CsvAction(List2CsvActionClient client) {
		super();
		this.client = client;
	}

	@Override
	public void work() throws InitializationException, DataException, LogicException {
		List<Object> list = Utils.cast(client.getList());
		String[] properties = client.getPropertiesToExport();
		String[] labels = client.getColumnLabels();
		Map<Integer, Formatter<?>> specialFormatters = client.getSpecialFormatters();
		if (!CollectionUtils.isNullOrEmpty(list)) {
			EntitiesQuery<Object> query = new IteratorQuery<Object>(list.iterator());  
			String streamName = CSV_NAME + client.getFileName();
			EntitiesQuery2CsvOperation<Object, EntitiesQuery<Object>> toCsv = 
				new EntitiesQuery2CsvOperation<Object, EntitiesQuery<Object>>
				(query, properties, specialFormatters, streamName, labels);
			toCsv.setFirstRows(client.getFirstRows());
			toCsv.setLastRows(client.getLastRows());
			String manager = Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName();
			toCsv.setManagerName(manager);			
			Bo2WicketRequestCycle.execute(toCsv);			
			Provider provider = Bo2WicketRequestCycle.provider(); 
			NamedStreamsProvider nsp = provider.getResource(manager, NamedStreamsProvider.class);			
			NamedInputStream nis = (NamedInputStream) 
				nsp.convert(streamName, StreamType.INPUTSTREAM, client.getFileName()); 
			IResourceStream csvStream = new InputStreamAsResourceStream(nis.getStream());
			RequestCycle.get().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(csvStream, client.getFileName()));
		}
	}
	
}
