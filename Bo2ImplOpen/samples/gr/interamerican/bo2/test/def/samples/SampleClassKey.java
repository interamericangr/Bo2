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
package gr.interamerican.bo2.test.def.samples;

import gr.interamerican.bo2.impl.open.annotations.KeyProperties;
import gr.interamerican.bo2.samples.implopen.entities.SampleClassKP1;
import gr.interamerican.bo2.samples.implopen.entities.SampleClassKP2;

/**
 * sample interface
 */
@KeyProperties("field1, field2")
public interface SampleClassKey
extends SampleClassKP1, SampleClassKP2
{
	//empty
}
