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
package gr.interamerican.bo2.samples.collections;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory utility class that creates lists of sample beans.
 */
public class BeanCollections {
	
	/**
	 * Creates a list of 7 BeanWithOrderedFields.
	 * 
	 * @return Returns a a list of 7 BeanWithOrderedFields.
	 */
	@SuppressWarnings("nls")
	public static List<BeanWithOrderedFields> listOfBeanWithOrderedFields() {
		List<BeanWithOrderedFields> list = new ArrayList<BeanWithOrderedFields>();
		list.add(new BeanWithOrderedFields("one", "1", 1, 1L, 1.1));
		list.add(new BeanWithOrderedFields("two", "2", 2, 2L, 1.2));
		list.add(new BeanWithOrderedFields("three", "3", 3, 3L, 1.3));
		list.add(new BeanWithOrderedFields("four","4", 4, 4L, 1.4));
		list.add(new BeanWithOrderedFields("five","5", 5, 5L, 1.5));
		list.add(new BeanWithOrderedFields("six","6", 6, 6L, 1.6));
		list.add(new BeanWithOrderedFields("seven","7", 7, 7L, 1.7));	
		return list;
	}

}
