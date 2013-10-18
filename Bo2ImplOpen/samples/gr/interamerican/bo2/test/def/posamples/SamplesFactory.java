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
package gr.interamerican.bo2.test.def.posamples;

import gr.interamerican.bo2.arch.utils.collections.PoSet;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserProfile;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Calendar;
import java.util.Set;


/**
 * Factory of sample objects.
 */
public class SamplesFactory {
	
	
	/**
	 * Gets a factory that will use the default Bo2 factory.
	 * 
	 * @return factory.
	 */
	public static SamplesFactory getBo2Factory() {		
		ObjectFactory objectFactory =
			Factory.getCurrentFactory();
		return new SamplesFactory(objectFactory);
	}
	
	/**
	 * Gets a factory that will create concrete objects defined in a
	 * mapping file that is found in an initialization path.
	 * 
	 * @return factory.
	 */
	public static SamplesFactory getConcrete() {		
		ObjectFactory objectFactory = UtilityForBo2Test.getMappedFactory();
		return new SamplesFactory(objectFactory);
	}
	
	/**
	 * Gets a factory that will create factored objects.
	 * 
	 * @return factory.
	 */
	public static SamplesFactory getFactored() {
		ObjectFactory objectFactory = UtilityForBo2Test.getNotMappedFactory();
		return new SamplesFactory(objectFactory);
	}
	
	/**
	 * Creates a new SamplesFactored object. 
	 *
	 * @param factory
	 */
	private SamplesFactory(ObjectFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Object factory.
	 */
	private ObjectFactory factory;
	
	/**
	 * Creates a sample customer with two addresses.
	 * 
	 * @param taxId
	 * @return customer.
	 */
	public Customer sampleCustomer(String taxId) {
		Customer cust = factory.create(Customer.class);
		cust.setTaxId(taxId);
		Set<CustomerAddress> addresses = cust.getAddresses();
		addresses.add(sampleAddress(1));
		addresses.add(sampleAddress(2));
		cust.setAddresses(addresses);
		return cust;
	}
	
	/**
	 * Creates a sample address.
	 * 
	 * @param number
	 * @return address.
	 */
	public CustomerAddress sampleAddress(int number) {
		CustomerAddress addr = factory.create(CustomerAddress.class);
		addr.setStreet("���� �����"); //$NON-NLS-1$
		addr.setStreetNo(Integer.toString(number));
		addr.setAddressNo(number);
		return addr;
	}
	
	/**
	 * Creates a sample {@link InvoiceCustomer}.
	 * 
	 * @return InvoiceCustomer.
	 */
	public InvoiceCustomer sampleInvoiceCustomer() {
		InvoiceCustomer cust = factory.create(InvoiceCustomer.class);
		cust.setAddressNoForInvoice(1);
		cust.setRoleId(0);
		return cust;
	}
	
	/**
	 * Creates a sample {@link InvoiceLine}.
	 * 
	 * @param lineNo
	 * @return InvoiceLine.
	 */
	public InvoiceLine sampleInvoiceLine(int lineNo) {
		InvoiceLine line = factory.create(InvoiceLine.class);
		line.setAmount(100.00);
		line.setType(0);
		line.setLineNo(lineNo);
		return line;
	}
	
	/**
	 * Creates a sample {@link InvoiceSubLine}.
	 * 
	 * @param subLineNo
	 * @return InvoiceSubLine.
	 */
	public InvoiceSubLine sampleInvoiceSubLine(int subLineNo) {
		InvoiceSubLine sLine = factory.create(InvoiceSubLine.class);
		sLine.setSubLineNo(subLineNo);
		sLine.setName("name" + subLineNo); //$NON-NLS-1$
		return sLine;
	}
	
	/**
	 * Creates a sample {@link InvoiceSubRule}.
	 * 
	 * @param subRuleCd
	 * @return InvoiceSubRule
	 */
	public InvoiceSubRule sampleInvoiceSubRule(Long subRuleCd) {
		InvoiceSubRule sRule = factory.create(InvoiceSubRule.class);
		sRule.setSubRuleCd(subRuleCd);
		sRule.setSubRuleName("subRule"+subRuleCd); //$NON-NLS-1$
		return sRule;
	}
	
	/**
	 * Creates a sample {@link InvoiceRule}.
	 * 
	 * @param ruleCd
	 * @return InvoiceSubRule
	 */
	public InvoiceRule sampleInvoiceRule(Long ruleCd) {
		InvoiceRule rule = factory.create(InvoiceRule.class);
		rule.setRuleCd(ruleCd);
		rule.setRuleName("Rule"+ruleCd); //$NON-NLS-1$
		return rule;
	}
	
	/**
	 * Creates a sample {@link InvoiceRule}.
	 * 
	 * @param ruleCd
	 * @param countOfSubRules 
	 * @return InvoiceSubRule
	 */
	public InvoiceRule sampleInvoiceRuleWithSubRules(Long ruleCd, int countOfSubRules) {
		InvoiceRule rule = factory.create(InvoiceRule.class);
		rule.setRuleCd(ruleCd);
		rule.setRuleName("Rule"+ruleCd); //$NON-NLS-1$
		for(int i=1; i<=countOfSubRules; i++) {
			InvoiceSubRule sRule = sampleInvoiceSubRule(new Long(i));
			rule.getSubRules().add(sRule);
		}
		return rule;
	}
	
	/**
	 * Creates a sample Invoice.
	 * @param invoiceNo 
	 * @return Invoice.
	 */
	public Invoice sampleInvoice(String invoiceNo) {
		Invoice invoice = factory.create(Invoice.class);
		invoice.setInvoiceDate(DateUtils.getDate(2010,Calendar.DECEMBER,5));
		invoice.setInvoiceNo(invoiceNo);
		InvoiceInfo info = Factory.create(InvoiceInfo.class);
		info.setBarCode(StringConstants.MINUS);
		invoice.setInfo(info);
		return invoice;
	}	
	
	/**
	 * Creates a sample Invoice that contains rules and lines.
	 * @param countOfLines
	 * @return Invoice.
	 */
	public Invoice sampleInvoiceFull(int countOfLines) {
		Invoice invoice = factory.create(Invoice.class);
		invoice.setInvoiceDate(DateUtils.getDate(2010,Calendar.DECEMBER,5));
		invoice.setCustomer(sampleInvoiceCustomer());
		InvoiceInfo info = Factory.create(InvoiceInfo.class);
		info.setBarCode(StringConstants.MINUS);
		invoice.setInfo(info);
		Set<InvoiceLine> lines = new PoSet<InvoiceLine>();
		for (int i = 1; i <= countOfLines; i++) {
			InvoiceLine line = sampleInvoiceLine(i);
			line.getSubLines().add(sampleInvoiceSubLine(i));
			lines.add(line);
		}
		invoice.setLines(lines);

		/* also add 2 rules */
		InvoiceRule rule = sampleInvoiceRuleWithSubRules(new Long(1), 1);
		invoice.getRules().add(rule);
		rule = sampleInvoiceRuleWithSubRules(new Long(2), 2);
		invoice.getRules().add(rule);
		
		return invoice;
	}
	
	/**
	 * Returns a sample User with the specified number of profiles.
	 * @param userId 
	 * 
	 * @param countOfProfiles
	 * @return User.
	 */
	@SuppressWarnings("nls")
	public User sampleUser(int userId, int countOfProfiles) {
		User u = new User();
		u.setUsrid("USRID_00");
		u.setName("name");
		u.setId(userId);
		for(int i=0; i<countOfProfiles; i++) {
			UserProfile up = new UserProfile();
			up.setUserId(userId);
			up.setProfileId(i);
			up.setName("name" + String.valueOf(i));
			u.getProfiles().add(up);
		}
		
		return u;
	}
	
	/**
	 * Creates an {@link Invoice}
	 * 
	 * @return Invoice.
	 */
	public Invoice newInvoice() {
		return factory.create(Invoice.class);
	}
	
	/**
	 * Creates an {@link InvoiceCustomer}
	 * 
	 * @return InvoiceCustomer.
	 */
	public InvoiceCustomer newInvoiceCustomer() {
		return factory.create(InvoiceCustomer.class);
	}

	/**
	 * Gets the factory.
	 *
	 * @return Returns the factory
	 */
	public ObjectFactory getFactory() {
		return factory;
	}

}
