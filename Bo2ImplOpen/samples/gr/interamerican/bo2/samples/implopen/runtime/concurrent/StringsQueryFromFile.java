/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. All rights reserved. This program and
 * the accompanying materials are made available under the terms of the GNU Lesser Public License v3 which accompanies
 * this distribution, and is available at http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 ******************************************************************************/
package gr.interamerican.bo2.samples.implopen.runtime.concurrent;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;

import java.io.InputStream;

/**
 * {@link StringsQuery} that fetches 1000 strings.
 */
public class StringsQueryFromFile extends AbstractResourceConsumer implements EntitiesQuery<String> {

	/** The rownumber. */
	int rownumber = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
		readStream = (NamedStream<InputStream>) nsp.getStream("testRead"); //$NON-NLS-1$
	}

	/** The entity. */
	String entity = null;
	
	/** The read stream. */
	NamedStream<InputStream> readStream;
	
	/** The avoid lock. */
	private boolean avoidLock = false;
	
	@Override
	public void execute() throws DataException {
		rownumber = 0;
	}

	@Override
	public boolean next() throws DataAccessException {
		try {
			entity = readStream.readString();
		} catch (DataOperationNotSupportedException e) {
			throw new DataAccessException(e);
		} catch (DataException e) {
			throw new DataAccessException(e);
		}
		if (entity == null) {
			return false;
		}
		rownumber++;
		return true;
	}

	@Override
	public int getRow() throws DataAccessException {
		return rownumber;
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {
		this.avoidLock = avoidLock;
	}

	@Override
	public boolean isAvoidLock() {
		return avoidLock;
	}

	@Override
	public String getEntity() throws DataAccessException {
		return entity;
	}
}
