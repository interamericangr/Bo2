package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceLineKey;
import gr.interamerican.bo2.test.impl.posamplesConcrete.InvoiceImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.InvoiceLineImpl;
import gr.interamerican.bo2.utils.enums.IndexPropertyType;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ChildAdder}.
 */
public class TestChildAdder {
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor() {				
		ChildAdder add = new ChildAdder(Invoice.class, "lines");
		Assert.assertEquals("lines", add.childCollectionName);				
		Assert.assertEquals(Invoice.class, add.poClass);
		Assert.assertEquals(InvoiceKey.class, add.keyClass);
		Assert.assertEquals(InvoiceLine.class, add.childPoClass);
		Assert.assertEquals(InvoiceLineKey.class, add.childKeyClass);
		Assert.assertEquals("lineNo", add.indexPropertyName);
		Assert.assertEquals(IndexPropertyType.INTEGER, add.indexPropertyType);
		
	}
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testConstructor_failWithNotExistingProperty() {
		new ChildAdder(Invoice.class, "sex");		
	}
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testConstructor_failWithNotBadPropertyType() {				
		new ChildAdder(Invoice.class, "invoiceNo");		
	}
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAdd() {		
		Invoice invoice = new InvoiceImpl();		
		ChildAdder add = new ChildAdder(Invoice.class, "lines");
		
		String invoiceNo = "x1";
		invoice.setInvoiceNo(invoiceNo);		
		InvoiceLine l1 = new InvoiceLineImpl();
		InvoiceLine l2 = new InvoiceLineImpl();
		InvoiceLine l3 = new InvoiceLineImpl();
		InvoiceLine l4 = new InvoiceLineImpl();
		InvoiceLine[] lines = {l1,l2,l3,l4};		
		add.add(invoice,lines);
		for (int i = 0; i < lines.length; i++) {
			Assert.assertTrue(invoice.getLines().contains(lines[i]));
			Assert.assertEquals(invoiceNo, lines[i].getInvoiceNo());
			Assert.assertEquals(Integer.valueOf(i+1), lines[i].getLineNo());
		}
		
		
		
	}

	

}
