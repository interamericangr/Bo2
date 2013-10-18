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
package gr.interamerican.bo2.utils.comparators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestInvertedComparator {


	/**
	 * Return the opposite result that the regular comparator
	 */
	@Test
	public void testCompare_Number(){
		
		NumberComparator numberComparator =  new NumberComparator();
		InvertedComparator<Number> numberComp = new InvertedComparator(numberComparator);
		
		assertEquals(1,numberComp.compare(1, 2));
		assertEquals(-1,numberComp.compare(2, 1));
		assertEquals(0,numberComp.compare(1, 1));	
	}
	
	/**
	 * Return the opposite result that the regular comparator
	 */
	@Test
	public void testCompare_String(){
		
		StringComparator stringComparator =  new StringComparator();
		InvertedComparator<String> stringCorp = new InvertedComparator(stringComparator);
		
		assertEquals(1,stringCorp.compare("a", "b")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-1,stringCorp.compare("b", "a")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0,stringCorp.compare("a", "a"));	 //$NON-NLS-1$ //$NON-NLS-2$	
	}
	
	/**
	 * Return the opposite result that the regular comparator
	 */
	@Test
	public void testCompare_Array(){
		
		ArrayComparator arrayComparator =  new ArrayComparator();
		InvertedComparator<Object[]> arrayCorp = new InvertedComparator(arrayComparator);

		Object[] array1 = {new Integer(1),"string", new String("string")}; //$NON-NLS-1$ //$NON-NLS-2$
		Object[] array2 = {new Long(1),new String("string"),"string"}; //$NON-NLS-1$ //$NON-NLS-2$
		Object[] array3 = {new Long(1), new String("string")}; //$NON-NLS-1$
				
		assertEquals(0, arrayCorp.compare(array1, array2), 0);
		assertEquals(-1,arrayCorp.compare(array2, array3));
		assertEquals(1,arrayCorp.compare(array3, array2));	
	}
	
}

