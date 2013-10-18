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

/**
 * An {@link InvoiceLine} owns many {@link InvoiceSubLine}s.
 * This is a one-to-many relationship.
 */
@SuppressWarnings("all")
public interface InvoiceSubLine 
extends InvoiceSubLineKP, PersistentObject<InvoiceSubLineKey>, ModificationRecord {
	
	public void setName(String name);
	
	public String getName();
	
	public InvoiceRule getRule();
	
	public void setRule(InvoiceRule rule);
	
	public Long getRuleCd();
	
	public void setRuleCd(Long ruleCd);
	
}
