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
package gr.interamerican.bo2.utils.adapters;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * Unit tests for {@link NumberConverter}.
 */
public class TestNumberCoverter {
	
	/**
	 * Number converter.
	 */
	private NumberConverter nc;
	
	/**
	 * sample number.
	 */
	private Number sample = Byte.MIN_VALUE;
	
	/**
	 * sample number.
	 */
	private Number sample2 = Float.MAX_VALUE;
	
	/**
	 * Supported types for conversion.
	 */
	private Class<?>[] types = {
			Byte.class,
			Short.class,
			Integer.class,
			Long.class,
			Float.class,
			Double.class,
			BigDecimal.class
		};
	
	/**
	 * Unit test for execute.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() {
		/*
		 * test with min byte.
		 */
		for (int i = 0; i < types.length; i++) {
			nc = new NumberConverter((Class<? extends Number>) types[i]);
			sample = nc.execute(sample);
			assertTrue(nc.execute(sample).getClass().equals(types[i]));
			for (int j = 0; j < types.length; j++) {
				nc = new NumberConverter((Class<? extends Number>) types[j]);
				assertTrue(nc.execute(sample).getClass().equals(types[j]));
			}
		}
		/*
		 * test with max float.
		 */
		for (int i = 0; i < types.length; i++) {
			nc = new NumberConverter((Class<? extends Number>) types[i]);
			sample2 = nc.execute(sample);
			assertTrue(nc.execute(sample2).getClass().equals(types[i]));
			for (int j = 0; j < types.length; j++) {
				nc = new NumberConverter((Class<? extends Number>) types[j]);
				assertTrue(nc.execute(sample).getClass().equals(types[j]));
			}
		}
	}
	
	/**
	 * Unit test for execute.
	 */
	@Test (expected = RuntimeException.class)
	public void testExecute_RuntimeException() {
		nc = new NumberConverter(BigInteger.class);
		nc.execute(Integer.MAX_VALUE);
	}
	
}
