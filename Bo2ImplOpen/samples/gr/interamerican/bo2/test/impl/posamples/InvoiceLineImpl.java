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
package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceLineKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLine;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Set;

/**
 * 
 */
@SuppressWarnings("unused")
@DelegateKeyProperties("")
public abstract class InvoiceLineImpl 
extends AbstractModificationRecordPo<InvoiceLineKey> 
implements InvoiceLine {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * invoiceNo, lineNo ����������� ��� delegateProperties ��� ��������. 
	 * ������ �������� @DelegateKeyProperties("") �� ��������� delegates
	 * ��� ��� �� properties ��� ��������.
	 */
	
	/**
	 * �����
	 */
	@Property Integer type;
	
	/**
	 * ����.
	 */
	@Property Double amount;
	
	/**
	 * �������.
	 */
	@Property Customer customer;
	
	/**
	 * Sublines
	 */	
	@Child	
	@Property Set<InvoiceSubLine> subLines;

}
