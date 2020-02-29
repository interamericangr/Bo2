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

import gr.interamerican.bo2.samples.bean.BeanWith3Fields;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link PropertiesBasedComparator}.
 */
@Deprecated
@SuppressWarnings("nls")
public class TestPropertiesBasedComparator {

	/**
	 * Tests compare(o1,o2).
	 */
	@Test
	public void testCompare_oneField() {
		String[] properties = { "field2", "field3" };
		PropertiesBasedComparator<BeanWith3Fields> comparator = new PropertiesBasedComparator<BeanWith3Fields>(
				BeanWith3Fields.class, properties);

		BeanWith3Fields o1 = new BeanWith3Fields(null, 1, 1.0);
		BeanWith3Fields o2 = new BeanWith3Fields(null, 2, 1.0);

		Comparator<BeanWith3Fields> other = Comparator.comparing(BeanWith3Fields::getField2)
				.thenComparing(BeanWith3Fields::getField3);
		Assert.assertTrue(comparator.compare(o1, o2) < 0);
		Assert.assertTrue(other.compare(o1, o2) < 0);
	}

	/**
	 * Tests compare(o1,o2).
	 */
	@Test
	public void testCompare_twoFields() {
		String[] properties = { "field2", "field3" };
		PropertiesBasedComparator<BeanWith3Fields> comparator = new PropertiesBasedComparator<BeanWith3Fields>(
				BeanWith3Fields.class, properties);

		BeanWith3Fields o1 = new BeanWith3Fields(null, 1, 1.0);
		BeanWith3Fields o2 = new BeanWith3Fields(null, 1, 2.0);

		Comparator<BeanWith3Fields> other = Comparator.comparing(BeanWith3Fields::getField2)
				.thenComparing(BeanWith3Fields::getField3);
		Assert.assertTrue(comparator.compare(o1, o2) < 0);
		Assert.assertTrue(other.compare(o1, o2) < 0);
	}

	/**
	 * Tests compare(o1,o2).
	 */
	@Test
	public void testCompare_equal() {
		String[] properties = { "field2", "field3" };
		PropertiesBasedComparator<BeanWith3Fields> comparator = new PropertiesBasedComparator<BeanWith3Fields>(
				BeanWith3Fields.class, properties);

		BeanWith3Fields o1 = new BeanWith3Fields(null, 1, 2.0);
		BeanWith3Fields o2 = new BeanWith3Fields(null, 1, 2.0);

		Comparator<BeanWith3Fields> other = Comparator.comparing(BeanWith3Fields::getField2)
				.thenComparing(BeanWith3Fields::getField3);
		Assert.assertEquals(0, comparator.compare(o1, o2));
		Assert.assertEquals(0, other.compare(o1, o2));
	}
}