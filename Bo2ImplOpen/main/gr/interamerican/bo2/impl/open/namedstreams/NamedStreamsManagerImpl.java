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
package gr.interamerican.bo2.impl.open.namedstreams;

import static gr.interamerican.bo2.impl.open.namedstreams.InvalidNsdUtility.invalid;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.DATE;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.ENCODING_PREFIX;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.RECORD_LENGTH_PREFIX;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.TIMESTAMP;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.CouldNotConvertNamedStreamException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.CouldNotCreateNamedStreamException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.NamedStreamFactory;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

/**
 * TODO: This is the replacement of NamedStreamsManagerImpl.
 *
 */
public class NamedStreamsManagerImpl
implements NamedStreamsProvider {



	/**
	 * Streams opened by the program
	 */
	HashMap<String, NamedStream<?>> streams;

	/**
	 * Program for which the NamedStreamsCreator operates.
	 */
	protected Properties properties;

	/**
	 * Creates a new NamedStreamsCreator that is reads input by a Properties
	 * object.
	 *
	 * @param properties
	 *            Properties object with input for this NamedStreamCreator.
	 */
	public NamedStreamsManagerImpl(Properties properties) {
		this.properties = properties;
		streams = new HashMap<String, NamedStream<?>>();
	}



	@Override
	public NamedStream<?> getStream(String name) throws InitializationException {
		NamedStream<?> ns = streams.get(name);
		if (ns==null) {
			NamedStreamDefinition def = getDefinition(name);
			ns = open(def);
			registerStream(ns);
		}
		return ns;
	}

	@Override
	public NamedStream<?> getSharedStream(String name) throws InitializationException {
		/*
		 * This needs to be synchronized across all instances of this class, because
		 * it is possible that two competing threads may open two different streams
		 * to the same resource described by the logical name, even though, in the
		 * end only one will be registered with the registry and used.
		 */
		synchronized (NamedStreamsManagerImpl.class) {
			NamedStream<?> ns = SharedNamedStreamsRegistry.getStream(name, this);
			if (ns==null) {
				NamedStreamDefinition def = getDefinition(name);
				ns = open(def);
				SharedNamedStreamsRegistry.register(name, ns, this);
			}
			return SharedNamedStreamsRegistry.getStream(name, this);
		}
	}

	@Override
	public void close() throws DataException {
		for (NamedStream<?> stream : streams.values()) {
			stream.close();
		}
		streams.clear();
		SharedNamedStreamsRegistry.releaseSharedStreams(this);
	}

	@Override
	public void registerStream(NamedStream<?> stream) {
		streams.put(stream.getName(), stream);
	}

	@Override
	public void registerSharedStream(NamedStream<?> stream) {
		SharedNamedStreamsRegistry.register(stream.getName(), stream, this);
	}

	@Override
	public void registerStreamDefinition(NamedStreamDefinition definition) {
		properties.setProperty(definition.getName(), definition.getSpecsString());
	}


	/**
	 * Gets the definition of the stream with the specified logical name.
	 *
	 * @param name
	 *        Stream name.
	 *
	 * @return Returns the definition.
	 *
	 * @throws InitializationException
	 */
	NamedStreamDefinition getDefinition(String name)
			throws InitializationException {
		String definition = properties.getProperty(name);
		if (definition==null) {
			String problem = "No description"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		String[] attributes = TokenUtils.splitTrim(definition, StringConstants.COMMA);
		if (attributes.length<3) {
			String problem = "Invalid description"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		StreamType type = StringUtils.ignoreCaseValueOf
				(StreamType.values(), attributes[1]);
		if (type==null) {
			String problem = "Unknown type " + attributes[1]; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		StreamResource resourceType = StringUtils.ignoreCaseValueOf
				(StreamResource.values(), attributes[2]);
		if (resourceType==null) {
			String problem = "Unknown resource type " + attributes[2]; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		NamedStreamDefinition nsd = createNameStreamDefinitionWithMandatoryOptions(name,
				attributes[0], type, resourceType);
		nsd = handleOptionalDefinitionElement(nsd, ArrayUtils.safeGet(attributes, 3),
				ArrayUtils.safeGet(attributes, 4));
		return nsd;
	}

	/**
	 * creates a {@link NamedStreamDefinition} with the mandatory options set.
	 *
	 * @param name
	 * @param uri
	 * @param type
	 * @param resourceType
	 * @return the {@link NamedStreamDefinition}
	 */
	NamedStreamDefinition createNameStreamDefinitionWithMandatoryOptions(String name,
			String uri, StreamType type, StreamResource resourceType) {
		NamedStreamDefinition nsd = new NamedStreamDefinition();
		nsd.setName(name);
		String definitionUri = uri;
		nsd.setType(type);
		nsd.setResourceType(resourceType);
		nsd.setRecordLength(0); //initialize with zero

		if(resourceType == StreamResource.FILE) { //dynamic URI + windows hack
			definitionUri = fileUri(definitionUri, type);
		}
		nsd.setUri(definitionUri);
		return nsd;
	}

	/**
	 * Gets the actual definitionUri if the stream is FILE based. Output streams URI may be dynamic based
	 * on the current timestamp.
	 *
	 * @param definitionUri
	 * @param streamType
	 *
	 * @return Returns the actual definitionUri
	 */
	String fileUri(String definitionUri, StreamType streamType) {
		String result = fileUriModificationForWindows(definitionUri);

		if(!streamType.isOutputStream()) {
			return result;
		}

		if(result.contains(TIMESTAMP)) {
			result = result.replace(TIMESTAMP, currentTimestamp());
		}

		if(result.contains(DATE)) {
			result = result.replace(DATE, currentDate());
		}

		return result;
	}

	/**
	 * Prepends C: if the running OS is not unix...
	 *
	 * @param fileUri
	 * @return modified URI.
	 */
	String fileUriModificationForWindows(String fileUri) {
		boolean runsOnUnix = File.separator.equals("/"); //$NON-NLS-1$
		if(!runsOnUnix && fileUri.startsWith("/")) { //$NON-NLS-1$
			return "C:" + fileUri.trim(); //$NON-NLS-1$
		}
		return fileUri;
	}

	/**
	 * Gets a string based on the current timestamp.
	 *
	 * timestamp format is yyyyMMddhhmmss, e.g. 20141103124700
	 *
	 * @return Returns a string based on the current timestamp.
	 */
	String currentTimestamp() {
		String tmstmp = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
		return tmstmp;
	}

	/**
	 * Gets a string based on the current date.
	 *
	 * date format is yyyyMMdd, e.g. 20141103
	 *
	 * @return Returns a string based on the current timestamp.
	 */
	String currentDate() {
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
		return date;
	}

	/**
	 * The first three definition attributes are mandatory. There are two more
	 * optional attributes, recordLength and encoding. RecordLength is an integer
	 * encoding is a Charset name starting with the prefix {@value #ENCODING_PREFIX} <br/>
	 * For example enc:UTF-8 signifies the UTF-8 encoding.
	 *
	 * @see Charset
	 *
	 * @param nsd
	 * @param attributes
	 * @return NamedStreamDefinition instance for testability.
	 */
	NamedStreamDefinition handleOptionalDefinitionElement(NamedStreamDefinition nsd,
			String... attributes) {
		for (String attribute : attributes) {
			if(attribute==null) {
				continue;
			}

			if(attribute.trim().startsWith(ENCODING_PREFIX)) {
				String charset = attribute.trim().replace(ENCODING_PREFIX, StringConstants.EMPTY);
				nsd.setEncoding(Charset.forName(charset));
			} else if(attribute.trim().startsWith(RECORD_LENGTH_PREFIX)) {
				String recordLength = attribute.trim().replace(RECORD_LENGTH_PREFIX, StringConstants.EMPTY);
				int iLen = NumberUtils.string2Int(recordLength);
				nsd.setRecordLength(iLen);
			}

		}
		return nsd;
	}

	/**
	 * @param def
	 * @return
	 * @throws InitializationException
	 */
	NamedStream<?> open(NamedStreamDefinition def) throws InitializationException {
		try {
			StreamResource resourceType = def.getResourceType();
			NamedStreamFactory factory = resourceType.getFactory();
			return factory.create(def);
		} catch (CouldNotCreateNamedStreamException e) {
			throw new InitializationException(e);
		}
	}

	@Override
	public NamedStream<?> convert(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream)
			throws DataException {
		try {
			NamedStream<?> ns = getStream(nameOfStreamToConvert);
			StreamResource resourceType = ns.getResourceType();
			NamedStreamFactory factory = resourceType.getFactory();
			NamedStream<?> converted = factory.convert(ns, typeOfNewStream, nameOfNewStream);
			registerStream(converted);
			return converted;
		} catch (CouldNotConvertNamedStreamException cncnsex) {
			throw new DataException(cncnsex);
		} catch (InitializationException iex) {
			throw new DataException(iex);
		}
	}










}
