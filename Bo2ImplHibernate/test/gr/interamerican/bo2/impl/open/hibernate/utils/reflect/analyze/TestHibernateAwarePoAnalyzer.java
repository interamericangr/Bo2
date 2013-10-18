package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProvider;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test of {@link HibernateAwarePoAnalyzer}.
 */
@SuppressWarnings("all")
public class TestHibernateAwarePoAnalyzer {
	
	private static SamplesFactory factory=SamplesFactory.getBo2Factory();
	DeleteInvoiceData clear = new DeleteInvoiceData();
	private String invoiceNo="AAA";
	private String customerNo1 = "No1";
	private String customerNo2 = "No2";
	HibernateAwarePoAnalyzer HFA = new HibernateAwarePoAnalyzer();
	Invoice work;
	String managerName = Bo2AnnoUtils.getManagerName(Invoice.class);
	
	@Before
	public void before() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				open(DeleteInvoiceData.class).execute();
				
				PersistenceWorker<Customer> pw = openPw(Customer.class);
				Customer customer1 = factory.sampleCustomer("taxId1"); 
				customer1.setCustomerNo(customerNo1);
				customer1 = pw.store(customer1);
				Customer customer2 = factory.sampleCustomer("taxId2"); 
				customer2.setCustomerNo(customerNo2);
				customer2 = pw.store(customer2);
				
				Invoice invoice = SamplesFactory.getBo2Factory().sampleInvoiceFull(2);
				invoice.setInvoiceNo(invoiceNo);
				invoice.getCustomer().setCustomer(customer1);
				for(InvoiceLine line : invoice.getLines()) {
					line.setCustomer(customer2);
				}
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
			}
		}.execute();
	}
	
	@Test
	public void test1() throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				work = Factory.create(Invoice.class);
				work.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				work = ipw.read(work);
				
				comment("Working object status after read");
				System.out.println(HFA.execute(work));
				
				comment("Iterating over lines");
				for(InvoiceLine line : work.getLines()) {}

				comment("Working object status after lines read");
				System.out.println(HFA.execute(work));
				
				flush(getProvider(), managerName);
				
				comment("Working object status after flush");
				System.out.println(HFA.execute(work));
				
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				comment("Detached invoice");
				System.out.println(HFA.execute(work));
				
				comment("Reattaching invoice Bo2 style");
				PoUtils.reattach(work, getProvider());
				System.out.println(HFA.execute(work));
				
				flush(getProvider(), managerName);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				comment("Detached invoice");
				System.out.println(HFA.execute(work));
				
				comment("Reattaching invoice with session update");
				getSession(getProvider(), managerName).update(work);
				System.out.println(HFA.execute(work));
				flush(getProvider(), managerName);
				
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				comment("Detached invoice");
				System.out.println(HFA.execute(work));
				
				comment("Reattaching invoice with session update");
				getSession(getProvider(), managerName).update(work);
				System.out.println(HFA.execute(work));
				
				flush(getProvider(), managerName);
				
				comment("Initializing InvoiceCustomer customer");
				Customer c = work.getCustomer().getCustomer();
				c.getCustomerName();
				
				comment("Working object status");
				System.out.println(HFA.execute(work));
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				comment("Detached invoice");
				System.out.println(HFA.execute(work));
				
				comment("Reattaching invoice with session update");
				getSession(getProvider(), managerName).update(work);
				System.out.println(HFA.execute(work));
				flush(getProvider(), managerName);
				
				comment("Working object status");
				System.out.println(HFA.execute(work));
			}
		}.execute();
		
//		new AbstractBo2RuntimeCmd() {
//			@Override public void work() throws LogicException, 
//			DataException, InitializationException, UnexpectedException {
//				comment("Detached invoice");
//				System.out.println(HFA.execute(work));
//				
//				comment("Reattaching invoice Bo2 style");
//				PoUtils.reattach(work, getProvider());
//				System.out.println(HFA.execute(work));
//				flush(getProvider(), managerName);
//				
//				comment("Working object status");
//				System.out.println(HFA.execute(work));
//			}
//		}.execute();
		
	}
	
	void flush(Provider prov, String managerName) {
		comment("Manual flush: ");
		getSession(prov, managerName).flush();
	}
	
	Session getSession(Provider prov, String managerName) {
		try {
			return prov.getResource(managerName, HibernateSessionProvider.class).getHibernateSession();
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
	}
	
	void comment(String comment) {
		System.out.println("\n" + comment + "\n" + "----------------------------------------------------------------\n");
	}

}
