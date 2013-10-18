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
package gr.interamerican.bo2.utils.reflect.analyze;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class TestAllFieldsAnalyzer {

	/**
	 * AllFieldsAnalyzer
	 */
	AllFieldsAnalyzer afa = new AllFieldsAnalyzer();
	
	
	/**
	 * test FieldsToInclude
	 */
	@Test
	public void testFieldsToInclude(){ 
		List<Field> fields = afa.fieldsToInclude(BeanWith2Fields.class);
		assertEquals(2,fields.size());
		
	}
}
