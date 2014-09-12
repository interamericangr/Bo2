package gr.interamerican.bo2.impl.open;

import java.util.Date;

import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.impl.posamples.InvoiceImpl;

/**
 * 
 */
public class TestQt {
	
	@Test
	public void test1() {
		System.out.println("#@#@");
		
		
		Invoice i = Factory.create(Invoice.class);
		
		Class<?> enhanced = i.getClass();
		System.out.println(enhanced.getName());
		
		ClassLoader enhancedCl = enhanced.getClassLoader();
		
		ClassLoader standardCl = InvoiceImpl.class.getClassLoader();
		
		System.out.println(enhancedCl==standardCl);
		
		i.setInvoiceDate(new Date());
	}

}
