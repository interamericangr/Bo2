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
package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.SystemUtils;
import gr.interamerican.bo2.utils.beans.Range;

/**
 * Condition that checks if the used memory in the JVM has reached or exceeded
 * a specific percentage of the available memory.
 */
public class VmMemoryPercentageReachedCondition implements Condition<Long>{

	@Override
	public boolean check(Long t) {
		boolean withinRange = new IsWithinRange<Long>(new Range<Long>(0L,100L)).check(t);
		if(!withinRange) {
			throw new RuntimeException(t + " not within [0,100] range"); //$NON-NLS-1$
		}
		
		double used = SystemUtils.usedMemory();
		double total = SystemUtils.totalMemory();
		
		Long percentage = Math.round(100*(used/total));
		
		/*
		 * is the actual percentage greater or equal than the supplied value?
		 */
		return percentage.compareTo(t)>=0;
	}

}
