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
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.def.posamples.CompanyUserKey;
import gr.interamerican.bo2.test.def.samples.enums.Sex;

import java.util.List;

/**
 * 
 */
@DelegateKeyProperties("")
public abstract class CompanyUserImpl
extends AbstractModificationRecordPo<CompanyUserKey>
implements CompanyUser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * companyRole
	 */
	@SuppressWarnings("unused")
	@Property private CompanyRole companyRole;
	
	/**
	 * additionalRoles
	 */
	@SuppressWarnings("unused")
	@Property private List<CompanyRole> additionalRoles;
	
	/**
	 * sex
	 */
	@SuppressWarnings("unused")
	@Property private Sex sex;
	
}
