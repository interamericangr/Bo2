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
package gr.interamerican.bo2.test.impl.posamplesConcrete;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.TimestampKey;
import gr.interamerican.bo2.test.def.posamples.TimestampPo;

import java.sql.Timestamp;

/**
 * 
 */
public class TimestampPoImpl 
extends AbstractModificationRecordPo<TimestampKey> 
implements TimestampPo {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * timestamp
	 */
	private Timestamp tms;
	
	
	/**
	 * ���������� tms.
	 *
	 * @return tms
	 */
	
	public Timestamp getTms() {
		return tms;
	}
	/**
	 * ��������� tms.
	 *
	 * @param tms 
	 */
	public void setTms(Timestamp tms) {
		this.tms = tms;
	}
  
	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public TimestampPoImpl() {
		super();
		this.key = Factory.create(TimestampKey.class);
	}

	public String getTimestampNo() {		
		return key.getTimestampNo();
	}
	

	public void setTimestampNo(String timestampNo) {
		key.setTimestampNo(timestampNo);
	}
	

}
