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
package gr.interamerican.bo2.arch.utils.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.utils.beans.CodifiedNamedImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * tests for {@link Utils}
 */
public class TestUtils {
	
	/**
	 * tests arrayAsCodifiedSet()
	 */
	@Test
	public void testArrayAsCodifiedSet() {
		SomePo[] pos = samplePos(2);
		
		Set<CodifiedNamedImpl<Integer>> set = Utils.arrayAsCodifiedSet(pos);
		assertEquals(2, set.size());
		
		for (CodifiedNamedImpl<Integer> c : set) {
			assertTrue(c.getCode()==1 || c.getCode()==2);
			assertTrue(c.getName().equals(pos[0].toString()) 
					|| c.getName().equals(pos[1].toString()));
		}
	}
	
	/**
	 * tests getMax()
	 */
	@Test
	public void testGetMax() {
		int population = 100;
		SomePo[] pos = samplePos(population);
		Set<SomePo> set = new HashSet<SomePo>();
		set.addAll(Arrays.asList(pos));		
		SomePo maxPo = Utils.getMax(set);
		assertEquals(maxPo.getKey(), new Integer(--population));
	}
	
	/**
	 * tests getMin()
	 */
	@Test
	public void testGetMin() {
		int POPULATION = 100;
		SomePo[] pos = samplePos(POPULATION);
		Set<SomePo> set = new HashSet<SomePo>();
		set.addAll(Arrays.asList(pos));
		SomePo minPo = Utils.getMin(set);
		assertEquals(minPo.getKey(), new Integer(0));
	}
	
	/**
	 * tests maxByKey()
	 */
	@Test
	public void testMaxByKey() {
		int POPULATION = 2;
		SomePo[] pos = samplePos(POPULATION);
		SomePo maxPo = Utils.maxByKey(pos[0], pos[1]);
		assertEquals(maxPo.getKey(), new Integer(1));
		maxPo = Utils.maxByKey(pos[0], pos[0]);
		assertEquals(maxPo.getKey(), new Integer(0));
	}
	
	/**
	 * tests minByKey()
	 */
	@Test
	public void testGetMaxByKey() {
		int population = 2;
		SomePo[] pos = samplePos(population);
		SomePo minPo = Utils.minByKey(pos[0], pos[1]);
		assertEquals(minPo.getKey(), new Integer(0));
		minPo = Utils.maxByKey(pos[1], pos[1]);
		assertEquals(minPo.getKey(), new Integer(1));
	}
	
	/**
	 * creates an array of sample pos
	 * 
	 * @param number array size
	 * @return an array of sample pos 
	 */
	private SomePo[] samplePos(int number) {
		SomePo[] pos = new SomePo[number];
		for (int i=0; i<number; i++) {
			SomePo po = new SomePo();
			po.setKey(i);
			po.setString(Integer.toString(i));
			pos[i] = po;
		}
		return pos;
	}
	
	/**
	 * Test getMax given an empty collection
	 */
	@Test
	public void testGetMaxWithNullCollection(){
		Set<SomePo> set = null;
		SomePo maxPo = Utils.getMax(set);
		assertNull(maxPo);
	}
	
	/**
	 * Test getMax given an empty collection
	 */
	@Test
	public void testGetMaxWithEmptyCollection(){
		Set<SomePo> set = new HashSet<SomePo>();
		SomePo maxPo = Utils.getMax(set);
		assertNull(maxPo);
	}
	
	/**
	 * Test getMin given an empty collection
	 */
	@Test
	public void testGetMinWithNullCollection(){
		Set<SomePo> set = null;
		SomePo minPo = Utils.getMin(set);
		assertNull(minPo);
	}
	
	/**
	 * Test getMin given an empty collection
	 */
	@Test
	public void testGetMinWithEmptyCollection(){
		Set<SomePo> set = new HashSet<SomePo>();
		SomePo minPo = Utils.getMin(set);
		assertNull(minPo);
	}
	
	/**
	 * sample po
	 */
	@SuppressWarnings({ "unused", "serial" })
	private class SomePo implements PersistentObject<Integer> {
		
		/**
		 * string property
		 */
		String string;
		
		/**
		 * key
		 */
		Integer key;
		
		/**
		 * Gets the string.
		 *
		 * @return Returns the string
		 */		
		public String getString() {
			return string;
		}

		/**
		 * Assigns a new value to the string.
		 *
		 * @param string the string to set
		 */
		public void setString(String string) {
			this.string = string;
		}

		

		/* (non-Javadoc)
		 * @see gr.interamerican.bo2.arch.PersistentObject#getKey()
		 */
		public Integer getKey() {
			return key;
		}

		/* (non-Javadoc)
		 * @see gr.interamerican.bo2.arch.PersistentObject#setKey(java.io.Serializable)
		 */
		public void setKey(Integer key) {
			this.key = key;
		}

		/* (non-Javadoc)
		 * @see gr.interamerican.bo2.arch.PersistentObject#tidy()
		 */
		public void tidy() {
			/* empty */
		}
	}

}
