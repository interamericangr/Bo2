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
package gr.interamerican.bo2.test.def.samples;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.test.def.samples.enums.Sex;

/**
 * The Interface SamplePo.
 */
public interface SamplePo 
extends SamplePoKP, PersistentObject<SamplePoKey>, 
ModificationRecord, SampleCalculator, SampleJavaBean3, 
TypedSelectable<Long>, IBeanWithIdAndName, 
PoDependent<SamplePoForTest>
{	
	
	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the new serial no
	 */
	public void setSerialNo(Long serialNo);
	
	/**
	 * Gets the serial no.
	 *
	 * @return the serial no
	 */
	public Long getSerialNo();
	
	/**
	 * hack used to initialize class fields
	 * with @DelegateProperties or @DelegateMethods.
	 */
	public void init();
	
	/**
	 * Sets the longs.
	 *
	 * @param longs the new longs
	 */
	void setLongs(Long[] longs);
	
	/**
	 * Gets the longs.
	 *
	 * @return the longs
	 */
	Long[] getLongs();
	
	/**
	 * Sets the bytes.
	 *
	 * @param bytes the new bytes
	 */
	void setBytes(byte[] bytes);
	
	/**
	 * Gets the bytes.
	 *
	 * @return the bytes
	 */
	byte[] getBytes();
	
	/**
	 * Gets the i.
	 *
	 * @return the i
	 */
	int getI();
	
	/**
	 * Sets the i.
	 *
	 * @param i the new i
	 */
	void setI(int i);
	
	/**
	 * Enum.
	 *
	 * @param sex the new sex
	 */
	void setSex(Sex sex);
	
	/**
	 * Enum
	 * @return the sex
	 */
	Sex getSex();
	
}
