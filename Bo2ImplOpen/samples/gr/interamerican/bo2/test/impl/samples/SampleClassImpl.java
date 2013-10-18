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
package gr.interamerican.bo2.test.impl.samples;

import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.test.def.samples.SampleClass;
import gr.interamerican.bo2.test.def.samples.SampleClassKey;

/**
 * sample abstract class
 */
public abstract class SampleClassImpl implements SampleClass {
	
	/**
	 * a sample key field declared as an interface.
	 */
	@SuppressWarnings({ "unused", "cast" })
	@DelegateProperties("field1, field2")
	private SampleClassKey sampleInterface = (SampleClassKey) new SampleClassKeyImpl();
	
	
}
