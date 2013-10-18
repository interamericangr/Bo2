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
package gr.interamerican.bo2.impl.open.jasper;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.jasper.SampleCriteriaDependentEntitiesQueryJasperReportOperation;

import org.junit.Test;

/**
 * 
 */
public class TestCriteriaDependentEntitiesQueryJasperReportOperation {
	
	
	
	/**
	 * test execute
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute() 
	throws LogicException, DataException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				SampleCriteriaDependentEntitiesQueryJasperReportOperation op = 
					new SampleCriteriaDependentEntitiesQueryJasperReportOperation();
				op.setManagerName("LOCALFS"); //$NON-NLS-1$
				op.init(getProvider());
				op.open();
				op.getCriteria().setGender("UNKNOWN"); //$NON-NLS-1$
				op.execute();
				op.close();
			}
		}.execute();		
	}

}
