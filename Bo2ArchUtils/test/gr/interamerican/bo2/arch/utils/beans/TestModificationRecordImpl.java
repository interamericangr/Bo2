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
package gr.interamerican.bo2.arch.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

/**
 * TestModificationRecordImpl
 */
public class TestModificationRecordImpl {
	
	
	/**
	 * LAST_MODIFIED
	 */
	private static final Date LAST_MODIFIED = new Date();
	
	/**
	 * LAST_MODIFIED_BY
	 */
	private static final String LAST_MODIFIED_BY = "name"; //$NON-NLS-1$
	
	/**
	 * record
	 */
	ModificationRecordImpl record = new ModificationRecordImpl();
	
		
	/**
	 * Test getLastModified
	 */
	@Test
	public void testGetLastModified(){
		record.setLastModified(LAST_MODIFIED);
		assertEquals(LAST_MODIFIED,record.getLastModified());
	}
	
	/**
	 * Test setLastModified
	 */
	@Test
	public void testSetLastModified(){
		record.setLastModified(LAST_MODIFIED);
	}
	
	/**
	 * Test getLastModifiedBy
	 */
	@Test
	public void testGetLastModifiedBy(){
		record.setLastModifiedBy(LAST_MODIFIED_BY);
		assertEquals(LAST_MODIFIED_BY,record.getLastModifiedBy());
	}
	
	/**
	 * Test setLastModifiedBy
	 */
	@Test
	public void testSetLastModifiedBy(){
		record.setLastModifiedBy(LAST_MODIFIED_BY);
	}
	
	/**
	 * Test Equals
	 */
	@Test
	public void testEqualsWithNullValue(){
		assertEquals(false,record.equals(null));
	}
	
	/**
	 * initializeRecord
	 */
	private void initializeRecord(){
		record.setLastModified(LAST_MODIFIED);
		record.setLastModifiedBy(LAST_MODIFIED_BY);
	}
	
	/**
	 * Test Equals
	 */
	@Test
	public void testEquals(){
		initializeRecord();
		ModificationRecordImpl record2 = new ModificationRecordImpl();		
		record2.setLastModified(new Date(LAST_MODIFIED.getTime()));
		record2.setLastModifiedBy(LAST_MODIFIED_BY); 
		assertEquals(true,record.equals(record2));
	}
	
	/**
	 * Test Equals
	 */
	@Test
	public void testEqualsWithFalseValue(){
		assertEquals(false,record.equals(new String()));
	}
	
	/**
	 * Test hashCode
	 */
	@Test
	public void testHashCode(){
		assertTrue(record.hashCode()!=0);
	}
}
