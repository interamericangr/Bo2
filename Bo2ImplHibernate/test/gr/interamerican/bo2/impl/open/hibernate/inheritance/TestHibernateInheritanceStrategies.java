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
package gr.interamerican.bo2.impl.open.hibernate.inheritance;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProvider;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.hibernate.def.entities.BarTPCH;
import gr.interamerican.bo2.samples.hibernate.def.entities.BarTPSCH;
import gr.interamerican.bo2.samples.hibernate.def.entities.FooTPCH;
import gr.interamerican.bo2.samples.hibernate.def.entities.FooTPSCH;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestHibernateInheritanceStrategies {
	
	/**
	 * sample
	 */
	private String barStr = "BAR"; //$NON-NLS-1$
	
	/**
	 * sample
	 */
	private String fooStr = "FOO"; //$NON-NLS-1$
	
	/**
	 * Table per class hierarchy test.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testTablePerClassHierarchy() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				BarTPCH bar = Factory.create(BarTPCH.class);
				FooTPCH foo = Factory.create(FooTPCH.class);
				bar.setBar(barStr);
				bar.setId(1);
				foo.setFoo(fooStr);
				foo.setId(2);
				Session session = getProvider().getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				session.save(bar);
				session.save(foo);
				session.flush();
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				Session session = getProvider().getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				Assert.assertNull(session.get(BarTPCH.class, 2));
				Assert.assertNull(session.get(FooTPCH.class, 1));
				BarTPCH bar = (BarTPCH) session.get(BarTPCH.class, 1);
				FooTPCH foo = (FooTPCH) session.get(FooTPCH.class, 2);
				Assert.assertNotNull(foo);
				Assert.assertNotNull(bar);
				Assert.assertEquals(bar.getBar(), barStr);
				Assert.assertEquals(foo.getFoo(), fooStr);
				session.delete(foo);
				session.delete(bar);
				session.flush();
			}
		}.execute();
		
	}
	
	
	/**
	 * Table per sub-class hierarchy test.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testTablePerSubClassHierarchy() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				BarTPSCH bar = Factory.create(BarTPSCH.class);
				FooTPSCH foo = Factory.create(FooTPSCH.class);
				bar.setBar(barStr);
				bar.setId(1);
				foo.setFoo(fooStr);
				foo.setId(2);
				Session session = getProvider().getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				session.save(bar);
				session.save(foo);
				session.flush();
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				Session session = getProvider().getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				Assert.assertNull(session.get(BarTPSCH.class, 2));
				Assert.assertNull(session.get(FooTPSCH.class, 1));
				BarTPSCH bar = (BarTPSCH) session.get(BarTPSCH.class, 1);
				FooTPSCH foo = (FooTPSCH) session.get(FooTPSCH.class, 2);
				Assert.assertNotNull(foo);
				Assert.assertNotNull(bar);
				Assert.assertEquals(bar.getBar(), barStr);
				Assert.assertEquals(foo.getFoo(), fooStr);
				session.delete(foo);
				session.delete(bar);
				session.flush();
			}
		}.execute();
	}

}
