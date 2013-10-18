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

import static gr.interamerican.bo2.impl.open.hibernate.HibernateBo2Utils.getHibernateSessionProvider;
import static gr.interamerican.bo2.impl.open.hibernate.HibernateBo2Utils.logSessionFactoryStatistics;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLine;
import gr.interamerican.bo2.test.impl.posamples.InvoiceLineImpl;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.junit.Test;

/**
 * Unit tests for {@link HibernateBo2Utils}.
 */
public class TestHibernateBo2Utils {
	
	/**
	 * tests isManaged()
	 * @throws InitializationException 
	 */
	@Test
	public void testGetHibernateSessionProvider() throws InitializationException {
		Provider prov = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider(); //Non transactional provider.
		Invoice invoice = Factory.create(Invoice.class);
		HibernateSessionProvider hib1 = getHibernateSessionProvider(invoice, prov);
		assertNotNull(hib1);
		HibernateSessionProvider hib2 = getHibernateSessionProvider(new Object(), prov);
		assertNull(hib2);
	}
	
	/**
	 * tests isManaged()
	 * @throws InitializationException 
	 */
	@Test
	public void testLogSessionFactoryStatistics() throws InitializationException {
		Provider prov = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider(); //Non transactional provider.		
		HibernateSessionProvider hib = prov.getResource("LOCALDB", HibernateSessionProvider.class); //$NON-NLS-1$
		Session session = hib.getHibernateSession();
		SessionFactory factory = session.getSessionFactory();
		logSessionFactoryStatistics(factory);
	}
	
	/**
	 * tests isTransient
	 */
	@Test
	public void testIsTransient() {
		assertFalse(HibernateBo2Utils.isTransient(new InvoiceLineProxy()));
		
		InvoiceLine invoiceLine = Factory.create(InvoiceLine.class);
		assertTrue(HibernateBo2Utils.isTransient(invoiceLine));
		invoiceLine.setInvoiceNo("A"); //$NON-NLS-1$
		assertTrue(HibernateBo2Utils.isTransient(invoiceLine));
		invoiceLine.setLineNo(2);
		assertTrue(HibernateBo2Utils.isTransient(invoiceLine));
		invoiceLine.setLastModified(DateUtils.today());
		
		assertFalse(HibernateBo2Utils.isTransient(invoiceLine));
	}
	
	/**
	 * Sample for testIsTransient
	 */
	private class InvoiceLineProxy extends InvoiceLineImpl implements HibernateProxy {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public Integer getType() {
			return null;
		}

		public void setType(Integer type) { /* empty */ }

		public Double getAmount() {
			return null;
		}

		public void setAmount(Double amount) { /* empty */ }

		public void setSubLines(Set<InvoiceSubLine> subLines) { /* empty */ }

		public Set<InvoiceSubLine> getSubLines() {
			return null;
		}

		public Integer getLineNo() {
			return null;
		}

		public void setLineNo(Integer lineNo) { /* empty */ }

		public String getInvoiceNo() {
			return null;
		}

		public void setInvoiceNo(String invoiceNo) { /* empty */ }

		public Object writeReplace() {
			return null;
		}

		public LazyInitializer getHibernateLazyInitializer() {
			return null;
		}

		public Customer getCustomer() {
			return null;
		}

		public void setCustomer(Customer customer) { /* empty */ }
		
	}
	
}


