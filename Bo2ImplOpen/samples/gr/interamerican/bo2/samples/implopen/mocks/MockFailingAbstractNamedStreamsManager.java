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
package gr.interamerican.bo2.samples.implopen.mocks;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.AbstractNamedStreamsManager;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.StreamType;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Mock {@link AbstractNamedStreamsManager} that always returns
 * null when it tries to open a stream.
 * 
 * Not only instances of this class are used by the tests, but also
 * the class itself. This is why it can't be removed and replaced
 * by mock objects created by a mocking framework.
 */
public class MockFailingAbstractNamedStreamsManager 
extends AbstractNamedStreamsManager {
	
	/**
	 * Creates a new MockAbstractNamedStreamsProvider object. 
	 *
	 * @param properties
	 */
	public MockFailingAbstractNamedStreamsManager(Properties properties) {
		super(properties);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected NamedStream<?> open(NamedStreamDefinition def) 
	throws InitializationException {		 
		return new MockNamedStream(def.getType(), def.getResourceType(), null, def.getName(), def.getRecordLength(), Bo2UtilsEnvironment.getDefaultTextCharset());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NamedStream<?> convert
	(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream)
	throws DataException {	
		return new MockNamedStream(typeOfNewStream, StreamResource.OBJECT, null, nameOfNewStream, 0, Bo2UtilsEnvironment.getDefaultTextCharset());
	}

}
