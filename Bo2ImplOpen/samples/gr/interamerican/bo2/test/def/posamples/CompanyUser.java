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
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.samples.enums.Sex;

import java.util.List;

/**
 * 
 */
public interface CompanyUser 
extends CompanyUserKP, PersistentObject<CompanyUserKey>, ModificationRecord {
	
	/**
	 * Gets the companyRole.
	 *
	 * @return Returns the companyRole
	 */
	public CompanyRole getCompanyRole();

	/**
	 * Assigns a new value to the companyRole.
	 *
	 * @param companyRole the companyRole to set
	 */
	public void setCompanyRole(CompanyRole companyRole);
	
	/**
	 * Gets the additionalRoles
	 * 
	 * @return Returns the additionalRoles.
	 */
	public List<CompanyRole> getAdditionalRoles();
	
	/**
	 * Assigns a new value to the additionalRoles.
	 * 
	 * @param roles the additionalRoles to set
	 */
	public void setAdditionalRoles(List<CompanyRole> roles);
	
	/**
	 * Gets the user's sex.
	 * 
	 * @return returns the sex
	 */
	public Sex getSex();
	
	/**
	 * Assigns a new value to sex.
	 * 
	 * @param sex the sex to set.
	 */
	public void setSex(Sex sex);
	
}
