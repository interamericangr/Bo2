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
package gr.interamerican.bo2.impl.open.creation;


import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;

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
	 * Tests that the association map is updated.
	 */
	@Test
	public void testMapIsUpdated() {
		@SuppressWarnings("unused")
		PersistenceWorker<User> pw = factory.createPw(User.class);
		Class<? extends PersistenceWorker<?>> mapValue = 
			factory.pwClasses.get(User.class);
		assertEquals(UserPwImpl.class,mapValue);
	}	
	
	/**
	 * Tests creation of object with full file.
	 * 
	 */	
	@Test
	public void testFactoryCreationWithFullFile(){
		Properties properties = new Properties();
		properties.setProperty("pwMappingsFile", PATH);   //$NON-NLS-1$
		new PersistenceWorkerFactoryImpl(properties); 
	}
	
	/**
	 * Tests creation of object with empty file.
	 * 
	 */	
	@Test
	public void testFactoryCreationWithEmptyFile(){
		String path = "/gr/interamerican/rsrc/factories/pwf1/pwTypes.empty.txt"; //$NON-NLS-1$
		Properties properties = new Properties();
		properties.setProperty("pwMappingsFile", path);   //$NON-NLS-1$
		new PersistenceWorkerFactoryImpl(properties);		
	}
	
	/**
	 * Tests creation of object with null path.
	 */	
	@Test
	public void testFactoryCreationWithNull(){		
		new PersistenceWorkerFactoryImpl(new Properties());	
	}
	
	/**
	 * Tests GetDetachStrategy
	 */
	@Test
	public void testGetDetachStrategy() {
		DetachStrategy actual = factory.getDetachStrategy(User.class);
		PersistenceWorker<User> pw = factory.createPw(User.class);
		assertEquals(pw.getDetachStrategy(), actual);
	}
	
	/**
	 * Tests GetDetachStrategy
	 */
	@Test(expected=RuntimeException.class)
	public void testNoImplementationy() {		
		factory.noImplementation(User.class);
	}

}
