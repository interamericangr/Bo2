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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.impl.open.records.Buffer;
import gr.interamerican.bo2.impl.open.records.BufferSpec;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.Mediator;
import gr.interamerican.bo2.utils.meta.Meta;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Transforms an object to a Fixed length buffer.
 * 
 * @param <T> 
 *        Type of object being transformed to buffer.
 */
public class ToBuffer<T> 
implements Transformation<T, Buffer> {
	
	/**
	 * Buffer specification.
	 */
	BufferSpec bufferSpec;
	
	/**
	 * Business object descriptor.
	 */
	BusinessObjectDescriptor<T> descriptor;
	
	/**
	 * Mediator.
	 */
	Mediator mediator = Mediator.getInstance();
	
	/**
	 * Property descriptors necessary for this buffer.
	 */
	Set<BoPropertyDescriptor<Object>> propertyDescriptors;
	
	/**
	 * Formatters.
	 */
	Map<String, Formatter<Object>> formatters;
	
	/**
	 * 
	 */
	private boolean formatNullsAsMaxLengthSpaces = false;
	
	/**
	 * Creates a new ToBuffer object. 
	 *
	 * @param bufferSpec
	 *        Buffer specification.
	 * @param descriptor
	 *        Business object descriptor for the object to be transformed
	 *        to a Buffer.
	 * @param alternateFormatters
	 *        Maps field names with formatters. This parameters provides a
	 *        way to override the format of the objects. This map doesn't need
	 *        to contain formatters for all fields. It can even be empty or 
	 *        even null. For the fields for which there is no formatter
	 *        specified in this map, the {@link BoPropertyDescriptor} will
	 *        be used to format the values.  
	 */
	public ToBuffer(BufferSpec bufferSpec, BusinessObjectDescriptor<T> descriptor, 
			        Map<String, Formatter<?>> alternateFormatters) {
		super();
		this.bufferSpec = bufferSpec;
		this.descriptor = descriptor;
		Set<String> fieldNames = new HashSet<String>(bufferSpec.getFieldNames());		
		propertyDescriptors = new HashSet<BoPropertyDescriptor<Object>>();		
		formatters = new HashMap<String, Formatter<Object>>();
		for (BoPropertyDescriptor<?> bopd : descriptor.getPropertyDescriptors()) {
			String field = bopd.getName();
			if (fieldNames.contains(bopd.getName())) {
				@SuppressWarnings("unchecked")
				BoPropertyDescriptor<Object> boPD = (BoPropertyDescriptor<Object>) bopd; 
				propertyDescriptors.add(boPD);				
				Formatter<?> formatter = null;
				if (alternateFormatters!=null) {
					formatter = alternateFormatters.get(field); 
				}
				if (formatter==null) {
					formatter = Meta.getFormatter(boPD);
				}
				@SuppressWarnings("unchecked") 
				Formatter<Object> f = (Formatter<Object>) formatter;
				formatters.put(field, f);
			}
		}
	}
	
	/**
	 * Creates a new ToBuffer object. 
	 *
	 * @param bufferSpec
	 *        Buffer specification.
	 * @param descriptor
	 *        Business object descriptor for the object to be transformed
	 *        to a Buffer.
	 * @param alternateFormatters
	 *        Maps field names with formatters. This parameters provides a
	 *        way to override the format of the objects. This map doesn't need
	 *        to contain formatters for all fields. It can even be empty or 
	 *        even null. For the fields for which there is no formatter
	 *        specified in this map, the {@link BoPropertyDescriptor} will
	 *        be used to format the values.  
	 * @param formatNullsAsMaxLengthSpaces if true all null values will 
	 * 		  be formatted as a sequence of spaces with the length of MaxValue
	 */
	public ToBuffer(BusinessObjectDescriptor<T> descriptor, 
			        Map<String, Pair<Integer,Formatter<?>> > alternateFormatters, boolean formatNullsAsMaxLengthSpaces) {
		super();
		this.descriptor = descriptor;
		this.formatNullsAsMaxLengthSpaces = formatNullsAsMaxLengthSpaces;
		List<String> fieldNames = new ArrayList<String>();
		List<BoPropertyDescriptor<?>> sortedList =
			CollectionUtils.sort(descriptor.getPropertyDescriptors(), BoPropertyDescriptor.class, "index"); //$NON-NLS-1$
		propertyDescriptors = new HashSet<BoPropertyDescriptor<Object>>();		
		formatters = new HashMap<String, Formatter<Object>>();
		int[] lengths=new int[sortedList.size()];
		int i=0;
		for (BoPropertyDescriptor<?> bopd :sortedList) {
			String field = bopd.getName();
			fieldNames.add(field); 
			@SuppressWarnings("unchecked")
			BoPropertyDescriptor<Object> boPD = (BoPropertyDescriptor<Object>) bopd; 
			propertyDescriptors.add(boPD);				
			Formatter<?> formatter = null;
			if ((alternateFormatters!=null)&&(alternateFormatters.get(field)!=null)) {
				formatter = alternateFormatters.get(field).getRight();
				bopd.setMaxLength(alternateFormatters.get(field).getLeft());
			}else {
				formatter = Meta.getFormatter(boPD);
			}
			lengths[i]=bopd.getMaxLength();
			@SuppressWarnings("unchecked") 
			Formatter<Object> f = (Formatter<Object>) formatter;
			formatters.put(field, f);
			i++;
		}
		this.bufferSpec=new BufferSpec( fieldNames.toArray(new String[]{}),lengths);
	}
	
	/**
	 * Creates a new ToBuffer object. 
	 *
	 * @param bufferSpec
	 *        Buffer specification.
	 * @param descriptor
	 *        Business object descriptor for the object to be transformed
	 *        to a Buffer. 
	 */
	public ToBuffer(BufferSpec bufferSpec, BusinessObjectDescriptor<T> descriptor) {
		this(bufferSpec,descriptor,null);
	}

	
	public Buffer execute(T a) {
		Buffer buffer = new Buffer(bufferSpec);
		for (BoPropertyDescriptor<Object> boPD : propertyDescriptors) {
			Object value = mediator.getValue(boPD, a);
			String field = boPD.getName();
			Formatter<Object> formatter = formatters.get(field);
			String string = StringConstants.EMPTY;
			if(value == null && formatNullsAsMaxLengthSpaces) {
				for(int i=0; i<boPD.getMaxLength(); i++) {
					string += StringConstants.SPACE;
				}
			} else {
				string = formatter.format(value);
			}
			if (value instanceof Number) {
				buffer.setStringRightJustified(field, string);
			} else {
				buffer.setString(field, string);
			}
		}
		return buffer;
	}



	
	
	

}
