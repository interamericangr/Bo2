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

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link BufferSpec}.
 */
@SuppressWarnings("nls")
public class TestBufferSpec {
	
	/**
	 * Test for calcPositions()
	 */
	@Test
	public void testCalcPositions() {
		int[] l = {2,4,6};
		int[] actuals = BufferSpec.calculatePositions(l);
		int[] expecteds = {0,2,6};
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Tests getBufferLength.
	 */
	@Test
	public void testGetBufferLength() {
		int[] lengths = {2,4,6}; 
		String[] fields = {"one", "two", "three"};
		BufferSpec spec = new BufferSpec(fields, lengths);
		Assert.assertEquals(12, spec.getBufferLength());
	}
	
	/**
	 * Tests getLength.
	 */	
	@Test
	public void testGetLength() {
		int[] lengths = {2,4,6}; 
		String[] fields = {"one", "two", "three"};
		BufferSpec spec = new BufferSpec(fields, lengths);
		for (int i = 0; i < fields.length; i++) {
			Assert.assertEquals(lengths[i], spec.getLength(fields[i]));			
		}		
	}
	
	/**
	 * Tests getLength.
	 */	
	@Test
	public void testGetPosition() {
		int[] lengths = {2,4,6}; 
		int[] positions = {0,2,0}; 
		String[] fields = {"one", "two", "three"};
		BufferSpec spec = new BufferSpec(fields, lengths, positions);
		for (int i = 0; i < fields.length; i++) {
			Assert.assertEquals(positions[i], spec.getPosition(fields[i]));			
		}		
	}
	
	/**
	 * Tests getLength.
	 */	
	@Test
	public void testGetPositions() {
		int[] lengths = {2,4,6}; 
		int[] positions = {0,2,0}; 
		String[] fields = {"one", "two", "three"};
		BufferSpec spec = new BufferSpec(fields, lengths, positions);
		Set<Integer> pos = spec.getFieldPositions();
		Assert.assertEquals(pos.size(), 2);
		Assert.assertTrue(pos.contains(0));
		Assert.assertTrue(pos.contains(2));
	}
	
	

}
