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
package gr.interamerican.bo2.test.impl.samples;

import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;

/**
 * Bean used as a Component in mapping of {@link Invoice}
 */
public class InvoiceInfoImpl implements InvoiceInfo{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * barCode
	 */
	private String barCode;

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getBarCode() {
		return barCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null) { return false; }
		InvoiceInfo that = (InvoiceInfo) obj;
		if(this.barCode.equals(that.getBarCode())) {
			return true;
		}
		return super.equals(obj);
	}
	

}
