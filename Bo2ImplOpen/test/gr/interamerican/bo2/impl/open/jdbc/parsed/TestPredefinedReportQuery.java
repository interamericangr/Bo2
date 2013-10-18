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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.sql.elements.SamplePredefinedReport;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;
import gr.interamerican.bo2.utils.sql.VariableDefinitionFactory;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.elements.PredefinedReport;

import java.beans.PropertyDescriptor;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestPredefinedReportQuery {
	
	/**
	 * provider.
	 */
	protected Provider provider;
	
	/**
	 * Setup tests.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
	}
	
	
	/**
	 * Unit test for the whole lifecycle.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@Test
	public void testLifecycle() 
	throws InitializationException, DataException, SecurityException, NoSuchFieldException {
		PredefinedReport report = new SamplePredefinedReport();
		PredefinedReportQuery q = new PredefinedReportQuery(report);
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		Object criteria = q.getCriteria();
		Parameter[] params = report.getParameters();
		Column[] cols = report.getColumns();
		
		for (int i = 0; i < params.length; i++) {
			VariableDefinition<?> vd = VariableDefinitionFactory.create(params[i]);
			assertNotNull(criteria.getClass().getDeclaredField(vd.getName()));
		}
		
		Date from = DateUtils.getDate(2000, Calendar.JANUARY, 1);
		ReflectionUtils.setProperty("minInvoiceDt", from, criteria); //$NON-NLS-1$
		Date to = DateUtils.getDate(2050, Calendar.DECEMBER, 31);
		ReflectionUtils.setProperty("maxInvoiceDt", to, criteria); //$NON-NLS-1$
		
		assertEquals(criteria.getClass(), q.getArgumentType());
		
		q.init(provider);
		q.open();
		q.sql(); //necessary for the parameters to take value.
		
		Object[] actualParams = q.parameters();
		Object[] expectedParams = {new java.sql.Date(to.getTime()), new java.sql.Date(from.getTime())};
		
		assertArrayEquals(expectedParams, actualParams);
		
		q.execute();
		while (q.next()) {
			Object row = q.getEntity();			
			assertEquals(row.getClass(), q.getResultType());
			for (int i = 0; i < cols.length; i++) {
				VariableDefinition<?> vd = VariableDefinitionFactory.create(cols[i]);
				PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(row.getClass(), vd.getName());
				assertNotNull(pd);
				Object entityValue = JavaBeanUtils.getProperty(pd, row);
				Object rsValue = q.get(vd.getName(),cols[i].getColumnType());
				assertEquals(rsValue, entityValue);
			}		
		}		
		q.close();
	}	
	

}
