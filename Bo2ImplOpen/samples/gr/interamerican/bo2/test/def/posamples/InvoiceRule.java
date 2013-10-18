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

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;

import java.util.Set;

/**
 * An {@link InvoiceSubLine} has a reference to one {@link InvoiceRule}.
 * This is a many-to-one relationship. Because the key invoiceNo is shared
 * between the {@link InvoiceSubLine} and {@link InvoiceRule} the relationship
 * is mapped with insert="false" update="false".
 */
@SuppressWarnings("all")
public interface InvoiceRule 
extends InvoiceRuleKP, PersistentObject<InvoiceRuleKey>, ModificationRecord {
	
	public void setRuleName(String name);
	
	public String getRuleName();
	
	public Set<InvoiceSubRule> getSubRules();
	
	public void setSubRules(Set<InvoiceSubRule> subRules);
	
}
