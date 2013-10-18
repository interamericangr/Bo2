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
package gr.interamerican.bo2.impl.open.records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specification structure for a Buffer class.
 * 
 * There is one BufferSpec instance for each sub-type of Buffer.
 */
public class BufferSpec {
	
	/**
	 * Calculates the position of each field.
	 * 
	 * The position of each field is determined based on the length
	 * of the previous field. The first field starts at position 0.
	 * 
	 * @param lengths Array with lengths.
	 * 
	 * @return Returns an array with the positions of the fields.
	 */
	static int[] calculatePositions(int[] lengths) {
		int[] positions = new int[lengths.length];
		int currentPosition = 0;
		for (int i = 0; i < lengths.length; i++) {
			positions[i] = currentPosition;
			currentPosition += lengths[i];			
		}
		return positions;		
	}
	
	
	/**
	 * Lengths of fields.
	 */
	Map<String, Integer> fieldLengths=new HashMap<String, Integer>();
	
	/**
	 * Positions of fields.
	 */
	Map<String, Integer> fieldPositions=new HashMap<String, Integer>();
	
	/**
	 * Length of the buffer byte array.
	 */
	int bufferLength = 0;
	
	/**
	 * Gets the bufferLength.
	 *
	 * @return Returns the bufferLength
	 */
	int getBufferLength() {
		return bufferLength;
	}
	
	/**
	 * Gets the length of the field with the specified name.
	 * 
	 * @param field Field name.
	 * 
	 * @return Returns the field's length
	 */
	int getLength(String field) {
		Integer l = fieldLengths.get(field);
		if (l==null) {
			throw new FieldNotFoundException(field);
		}
		return l;
	}
	
	/**
	 * Gets the position of the field with the specified name.
	 * 
	 * @param field Field position.
	 * 
	 * @return Returns the field's position
	 */
	int getPosition(String field) {
		Integer p = fieldPositions.get(field);
		if (p==null) {
			throw new FieldNotFoundException(field);
		}
		return p;
	}
	
	
	/**
	 * Names of fields.
	 * 
	 * @return Returns the names of the fields.
	 */
	public List<String> getFieldNames() {
		List<String> fields = new ArrayList<String>(fieldLengths.keySet());
		return fields;
	}

	/**
	 * 
	 * Creates a new BufferSpec object. 
	 *
	 * @param fields names of fields
	 * @param lengths lengths of fields
	 * @param positions positions of fields.
	 */
	public BufferSpec(String[] fields, int[] lengths, int[] positions ) {
		bufferLength = 0;
		for (int i = 0; i < fields.length; i++) {
			fieldLengths.put(fields[i], lengths[i]);
			fieldPositions.put(fields[i], positions[i]);
			int max = positions[i] + lengths[i];
			if (max > bufferLength) {
				bufferLength = max;
			}
		}
	}	
	
	/**
	 * 
	 * Creates a new BufferSpec object. 
	 *
	 * @param fields names of fields
	 * @param lengths lengths of fields
	 * 
	 */
	public BufferSpec(String[] fields, int[] lengths) {
		this(fields,lengths,calculatePositions(lengths));
	}	
	
	
	
	
	
}
