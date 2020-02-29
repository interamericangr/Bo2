package gr.interamerican.bo2.impl.open.po.analysis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;

/**
 * Unit test of {@link CompositeProperty}.
 */
public class TestCompositeProperty {

	/**
	 * Test method for {@link CompositeProperty#merge(Object, Object, PoAnalyzer, MergeMode)}.
	 */
	@Test
	public void testMerge() {
		// test setup
		CompositeProperty tested = Factory.create(CompositeProperty.class);
		SimpleProperty child = Factory.create(SimpleProperty.class);
		child.setName("barCode"); //$NON-NLS-1$
		child.setType(String.class);
		List<PoProperty> childs = new ArrayList<PoProperty>();
		childs.add(child);
		tested.setProperties(childs);
		tested.setName("info"); //$NON-NLS-1$
		// samples
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		Invoice invoice = factory.newInvoice();
		InvoiceInfo info = Factory.create(InvoiceInfo.class);
		// 1st case - null target - mode is favor target
		assertNull(invoice.getInfo());
		tested.merge(info, invoice, null, MergeMode.FAVOR_TARGET);
		assertNull(invoice.getInfo());
		// 2st case - null target - mode is not favor target
		tested.merge(info, invoice, null, MergeMode.OVERWRITE);
		assertSame(info, invoice.getInfo());
		// 3rd case - null source - not null target
		info.setBarCode("barcode"); //$NON-NLS-1$
		tested.merge(null, invoice, null, MergeMode.OVERWRITE);
		assertNull(invoice.getInfo().getBarCode());
		// 4th case - not null source of value
		InvoiceInfo anotherSource = Factory.create(InvoiceInfo.class);
		anotherSource.setBarCode("another"); //$NON-NLS-1$
		tested.merge(anotherSource, invoice, null, MergeMode.FAVOR_SOURCE);
		assertEquals("another", invoice.getInfo().getBarCode()); //$NON-NLS-1$
	}
}