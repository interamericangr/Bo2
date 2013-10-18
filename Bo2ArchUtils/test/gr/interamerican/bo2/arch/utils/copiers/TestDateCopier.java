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
package gr.interamerican.bo2.arch.utils.copiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.samples.po.SampleDate;
import gr.interamerican.bo2.samples.po.SampleDateOtherConstr;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

/**
 * Test for MoneyCopier
 */
public class TestDateCopier {

	
	/**
	 * DateCopier
	 */
	DateCopier copier = new DateCopier();
	
	
	/**
	 * Test copy
	 */
	@Test
	public void testCopy(){
		
		assertNull(copier.copy(null));
		
		//default constructor
		Date date = new Date();
		date.setTime(2011L);
		Date newDate = copier.copy(date);
		assertEquals(date.getTime(),newDate.getTime());
		
		//constructor with long arg
		SampleDate sample = new SampleDate(2011l);
		sample.setTime(2011L);
		SampleDate newSampleDate = (SampleDate) copier.copy(sample);
		assertEquals(sample.getTime(),newSampleDate.getTime());
	}
	
	/**
	 * Test copy
	 */
	@Test(expected = RuntimeException.class)
	public void testCopy_fail(){

		SampleDateOtherConstr sample = new SampleDateOtherConstr(new BigDecimal(2));
		copier.copy(sample);

	}
	
	
}
