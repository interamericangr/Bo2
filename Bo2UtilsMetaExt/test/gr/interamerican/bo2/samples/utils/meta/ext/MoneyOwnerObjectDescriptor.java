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
package gr.interamerican.bo2.samples.utils.meta.ext;

import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;

import java.util.Arrays;

/**
 * 
 */
public class MoneyOwnerObjectDescriptor 
extends BasicBusinessObjectDescriptor<MoneyOwnerObject> {
	
	/**
	 * Creates a new Bean1descriptor object. 
	 *
	 */
	public MoneyOwnerObjectDescriptor() {
		super();
		BoPropertyDescriptor<?>[] descriptors = {
				moneyDescriptor()
		};
		this.setPropertyDescriptors(Arrays.asList(descriptors));
	}
	
	/**
	 * Creates a MoneyBoPropertyDescriptor.
	 * @return MoneyBoPropertyDescriptor
	 */
	public MoneyBoPropertyDescriptor moneyDescriptor(){
		MoneyBoPropertyDescriptor moneyDescriptor = new MoneyBoPropertyDescriptor();
		moneyDescriptor.setName("moneyField"); //$NON-NLS-1$
		moneyDescriptor.setLengthOfIntegerPart(3);		
		moneyDescriptor.setLengthOfDecimalPart(6);
		moneyDescriptor.setNullAllowed(false);
		moneyDescriptor.setNegativeAllowed(false);
		moneyDescriptor.setReadOnly(false);
		moneyDescriptor.setZeroAllowed(false);
		moneyDescriptor.setPackageName(MoneyOwnerObject.class.getPackage().getName());
		moneyDescriptor.setClassName(MoneyOwnerObject.class.getSimpleName());
		
		return moneyDescriptor;
	}
}
