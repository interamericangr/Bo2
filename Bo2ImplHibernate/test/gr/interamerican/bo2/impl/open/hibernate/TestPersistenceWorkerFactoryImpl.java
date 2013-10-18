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
package gr.interamerican.bo2.impl.open.hibernate;


import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.test.def.posamples.Invoice;

import java.util.Properties;

import org.junit.Test;



/**
 * Unit test for {@link PersistenceWorkerFactoryImpl}.
 *  
 */
public class TestPersistenceWorkerFactoryImpl {
	
	/**
	 * Path to mappings file.
	 */
	private static final String PATH = 
		"/gr/interamerican/rsrc/factories/pwf1/pwTypes.txt"; //$NON-NLS-1$
	
	/**
	 * Factory beeing tested.
	 */
	private PersistenceWorkerFactoryImpl factory; 
	
	
	/**
	 * Creates a new TestPersistenceWorkerFactoryImpl object. 
	 *
	 */
	public TestPersistenceWorkerFactoryImpl() {
		super();
		Properties properties = new Properties();
		properties.setProperty("pwMappingsFile", PATH); //$NON-NLS-1$
		factory = new PersistenceWorkerFactoryImpl(properties);
	}

	/**
	 * Tests creation when there is a mapping for the
	 * {@link PersistenceWorker}.
	 */
	@Test
	public void testCreateMappedPw() {
		PersistenceWorker<User> pw = factory.createPw(User.class);
		Class<?> pwclass = pw.getClass();
		assertEquals(UserPwImpl.class,pwclass);
	}
	
	/**
	 * Tests creation when there is a mapping for the
	 * {@link PersistenceWorker}.
	 */
	@Test
	public void testCreateDefaultPw() {
		PersistenceWorker<Invoice> pw = factory.createPw(Invoice.class);
		Class<?> pwclass = pw.getClass();
		assertEquals(GenericHibernatePersistenceWorker.class,pwclass);
	}
	
	/**
	 * Tests GetDetachStrategy
	 */
	@Test
	public void testGetDetachStrategy() {
		DetachStrategy actual = factory.getDetachStrategy(Invoice.class);
		PersistenceWorker<Invoice> pw = factory.createPw(Invoice.class);
		assertEquals(pw.getDetachStrategy(), actual);
	}

}
