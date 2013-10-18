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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.BATCH_PROCESS_NAME;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.INPUT_PROPERTY;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.MONITOR_MESSAGE_INTERVAL;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.MONITOR_MESSAGE_RECIPIENTS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.OPERATION_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.POST_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.PRE_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.PROCESSORS_COUNT;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.QUERY_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.UI_CAN_ADD_THREADS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.UI_REFRESH_INTERVAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.samples.archutil.beans.SampleCriteriaDependentBean;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.implopen.operations.FailingOperation;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringWithDelayOperation;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.StringsQuery1000;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link BatchProcessParmFactoryImpl}.
 */
public class TestBatchProcessParmFactoryImpl {
	
	/**
	 * name.
	 */
	final String name = "sample"; //$NON-NLS-1$
	/**
	 * inputProperty.
	 */
	final String inputProperty = "string"; //$NON-NLS-1$
	/**
	 * threads.
	 */
	final int threads = 4;
	/**
	 * query.
	 */
	final Class<? extends Query> qc = StringsQuery1000.class;
	/**
	 * operation.
	 */
	final Class<? extends Operation> oc = PrintStringOperation.class;	
	/**
	 * pre operation.
	 */
	final Class<? extends Operation> pre = PrintStringWithDelayOperation.class; 
	/**
	 * pre operation.
	 */
	final Class<? extends Operation> post = FailingOperation.class; 
	/**
	 * monitoring message interval.
	 */
	final int interval = 5;
	/**
	 * monitoring message recipients.
	 */
	final String recipients = "foo@bar.com"; //$NON-NLS-1$
	
	/**
	 * monitoring message recipients.
	 */
	final boolean canAddThreads = true;
	
	/**
	 * UI refresh interval.
	 */
	final int refreshInterval = 1;

	
	/**
	 * Creates a sample properties.
	 * 
	 * @return Returns the properties.
	 */
	Properties sample() {
		Properties p = new Properties();
		p.setProperty(BATCH_PROCESS_NAME, name);
		p.setProperty(QUERY_CLASS, qc.getName());
		p.setProperty(OPERATION_CLASS, oc.getName());
		p.setProperty(PRE_PROCESSING_CLASS, pre.getName());
		p.setProperty(POST_PROCESSING_CLASS, post.getName());
		p.setProperty(INPUT_PROPERTY, inputProperty);
		p.setProperty(PROCESSORS_COUNT, Integer.toString(threads));
		p.setProperty(MONITOR_MESSAGE_INTERVAL, Integer.toString(interval));
		p.setProperty(MONITOR_MESSAGE_RECIPIENTS, recipients);
		p.setProperty(UI_CAN_ADD_THREADS, Boolean.toString(canAddThreads));
		p.setProperty(UI_REFRESH_INTERVAL, Integer.toString(refreshInterval));
		return p;
	}
	
	
	
	/**
	 * Uint test for createParameter(properties).
	 */
	@Test
	public void testCreateParameter() {
		Properties p = sample();
		String field1 = "field1"; //$NON-NLS-1$
		String field2 = "field2"; //$NON-NLS-1$
		String v1 = "value1"; //$NON-NLS-1$
		Integer v2 = 2;
		p.setProperty(field1, v1);
		p.setProperty(field2, v2.toString());
		
		
		
		BatchProcessParmFactoryImpl impl = new BatchProcessParmFactoryImpl();		
		BatchProcessParm<Object> in = Utils.cast(impl.createParameter(p));
		assertNotNull(in);
		assertEquals(name, in.getName());
		assertEquals(threads, in.getInitialThreads());
		assertTrue(qc.isAssignableFrom(in.getQuery().getClass()));
		assertEquals(oc, in.getOperationClass());
		assertTrue(pre.isAssignableFrom(in.getPreProcessing().getClass()));
		assertTrue(post.isAssignableFrom(in.getPostProcessing().getClass()));
		assertEquals(ObjectFormatter.INSTANCE, in.getFormatter());
		
		Modification<Object> mod1 = in.getOperationParametersSetter();
		assertNotNull(mod1);		
		Modification<Object> mod2 = in.getQueryParametersSetter();
		assertNotNull(mod2);		
		Modification<Object> mod3 = in.getPreOperationParametersSetter();
		assertNotNull(mod3);
		Modification<Object> mod4 = in.getPostOperationParametersSetter();
		assertNotNull(mod4);	
		
		assertNotEquals(mod1, mod2);
		assertNotEquals(mod1, mod3);
		assertNotEquals(mod1, mod4);
		assertNotEquals(mod2, mod3);
		assertNotEquals(mod2, mod4);
		assertNotEquals(mod3, mod4);
		
		assertEquals(interval, in.getMonitoringMailInterval());
		assertEquals(recipients, in.getMonitoringMailRecipients());
		assertEquals(canAddThreads, in.getUiCanAddThreads());
		
		
	}
	
	/**
	 * Test for createModification() when no additional parameters
	 * exist.
	 */
	@Test
	public void testCreateModification_withNoParams() {
		Properties p = sample();
		BatchProcessParmFactoryImpl impl = new BatchProcessParmFactoryImpl();
		Modification<Object> mod = impl.createModification(p,Object.class);
		assertNull(mod);
	}
	
	/**
	 * Test for createModification() when no additional parameters
	 * exist.
	 */
	@Test
	public void testCreateModification_withParams() {
		Properties p = sample();
		String field1 = "field1"; //$NON-NLS-1$
		String field2 = "field2"; //$NON-NLS-1$
		String v1 = "value1"; //$NON-NLS-1$
		Integer v2 = 2;
		p.setProperty(field1, v1);
		p.setProperty(field2, v2.toString());
		
		BatchProcessParmFactoryImpl impl = new BatchProcessParmFactoryImpl();		
		Modification<Object> mod = impl.createModification(p,Object.class);
		assertNotNull(mod);
		BeanWith2Fields bean = new BeanWith2Fields();
		mod.execute(bean);
		Assert.assertEquals(v1, bean.getField1());
		Assert.assertEquals(v2, bean.getField2());
	}
	
	/**
	 * Test for createModification() when no additional parameters
	 * exist.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreateModification_forCriteriaDependentClass() {
		Properties p = sample();
		String field1 = "field1"; 
		String field2 = "field2"; 
		String property1 = "property1"; 
		String f1 = "f1";
		Integer f2 = 2;		
		String p1 = "p1";
		
		p.setProperty(field1, f1);
		p.setProperty(field2, f2.toString());
		p.setProperty(property1, p1);
		
		BatchProcessParmFactoryImpl impl = new BatchProcessParmFactoryImpl();		
		Modification<Object> mod = impl.createModification(p,CriteriaDependent.class);
		
		SampleCriteriaDependentBean bean = new SampleCriteriaDependentBean();		
		assertNotNull(mod);				
		mod.execute(bean);
		Assert.assertEquals(p1, bean.getProperty1());
		Assert.assertEquals(f1, bean.getCriteria().getField1());
		Assert.assertEquals(f2, bean.getCriteria().getField2());
	}
	
	
	
	
	
	
	
	

}
