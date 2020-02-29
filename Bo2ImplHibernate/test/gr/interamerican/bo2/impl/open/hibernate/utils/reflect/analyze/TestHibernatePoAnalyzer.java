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
package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;

import static org.junit.Assert.*;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProvider;
import gr.interamerican.bo2.impl.open.po.analysis.CompositeProperty;
import gr.interamerican.bo2.impl.open.po.analysis.OneToManyProperty;
import gr.interamerican.bo2.impl.open.po.analysis.OneToOneProperty;
import gr.interamerican.bo2.impl.open.po.analysis.PoAnalysis;
import gr.interamerican.bo2.impl.open.po.analysis.PoProperty;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.hibernate.def.entities.BarTPCH;
import gr.interamerican.bo2.samples.hibernate.def.entities.FooBarTPCH;
import gr.interamerican.bo2.samples.hibernate.def.entities.FooTPSCH;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.TimestampPo;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;
import gr.interamerican.bo2.test.impl.posamples.InvoiceImpl;

import java.util.Set;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.junit.Test;

/**
 * Tests {@link HibernatePoAnalyzer}.
 */
public class TestHibernatePoAnalyzer {

	/**
	 * Creates a Sample {@link HibernatePoAnalyzer} for test.
	 * 
	 * @return New Sample
	 */
	HibernatePoAnalyzer getTestSample() {
		try {
			return new HibernatePoAnalyzer(Bo2.getProvider()
					.getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession());//$NON-NLS-1$
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Test method for {@link HibernatePoAnalyzer#getAnalysis(Class)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetProperties() {
		HibernatePoAnalyzer tested = getTestSample();
		assertTrue(tested.cache.isEmpty());
		PoAnalysis analysis = tested.getAnalysis(InvoiceImpl.class);
		assertEquals(6, analysis.getProperties().size());
		assertEquals("lastModified", analysis.getVersionProperty().getName());
		assertFalse(tested.cache.isEmpty());
		assertSame(analysis, tested.cache.get(InvoiceImpl.class));
		tested.cache.put(InvoiceCustomer.class, analysis);
		assertSame(analysis, tested.getAnalysis(InvoiceCustomer.class));
	}

	/**
	 * Test method for {@link HibernatePoAnalyzer#findClassMetadata(Class)}.
	 */
	@Test
	public void testFindClassMetadata() {
		HibernatePoAnalyzer tested = getTestSample();
		assertEquals(Invoice.class.getName(), tested.findClassMetadata(InvoiceImpl.class).getEntityName());
		assertEquals(Invoice.class.getName(), tested.findClassMetadata(Invoice.class).getEntityName());
		assertEquals(FooBarTPCH.class.getName(), tested.findClassMetadata(FooBarTPCH.class).getEntityName());
		assertEquals(BarTPCH.class.getName(), tested.findClassMetadata(BarTPCH.class).getEntityName());
		assertEquals(FooTPSCH.class.getName(), tested.findClassMetadata(FooTPSCH.class).getEntityName());
		
	}
	/**
	 * Test method for {@link HibernatePoAnalyzer#findClassMetadata(Class)}.
	 */
	@Test(expected=RuntimeException.class)
	public void testFindClassMetadata_fail() {
		HibernatePoAnalyzer tested = getTestSample();
		tested.findClassMetadata(TimestampPo.class).getEntityName();
	}

	/**
	 * Test method for
	 * {@link HibernatePoAnalyzer#createPoProperty(String, Type)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreatePoProperty_Composite() {
		HibernatePoAnalyzer tested = getTestSample();
		ClassMetadata meta = tested.findClassMetadata(InvoiceImpl.class);
		PoProperty property = tested.createPoProperty("whatever", meta.getPropertyType("info"));
		assertTrue(property instanceof CompositeProperty);
		assertEquals(1, ((CompositeProperty)property).getProperties().size());
		assertEquals(InvoiceInfo.class, property.getType());
		assertEquals("whatever", property.getName());
	}

	/**
	 * Test method for
	 * {@link HibernatePoAnalyzer#createPoProperty(String, Type)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreatePoProperty_OneToMany() {
		HibernatePoAnalyzer tested = getTestSample();
		ClassMetadata meta = tested.findClassMetadata(InvoiceImpl.class);
		PoProperty property = tested.createPoProperty("whatever", meta.getPropertyType("lines"));
		assertEquals(Set.class, property.getType());
		assertTrue(property instanceof OneToManyProperty);
	}

	/**
	 * Test method for
	 * {@link HibernatePoAnalyzer#createPoProperty(String, Type)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreatePoProperty_OneToOne() {
		HibernatePoAnalyzer tested = getTestSample();
		ClassMetadata meta = tested.findClassMetadata(InvoiceImpl.class);
		PoProperty property = tested.createPoProperty("whatever", meta.getPropertyType("customer"));
		assertEquals(InvoiceCustomer.class, property.getType());
		assertTrue(property instanceof OneToOneProperty);
	}
}