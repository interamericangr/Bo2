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
package gr.interamerican.bo2.samples.creators;

import gr.interamerican.bo2.creation.ClassCreator;

/**
 * Mock implementation of {@link ClassCreator}.
 */
public class MockClassCreator implements ClassCreator {
	
	public Class<?> create(Class<?> interfaceType) {
		return null;
	}

	
	public String compileTimeClassName(String runTimeName) {
		return null;
	}

	public String runTimeClassName(String compileTimeName) {
		return null;
	}
	
	

}
