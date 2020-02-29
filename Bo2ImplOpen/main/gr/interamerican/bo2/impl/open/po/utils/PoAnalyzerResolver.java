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
package gr.interamerican.bo2.impl.open.po.utils;


import gr.interamerican.bo2.arch.PersistentObject;

/**
 * Finds the correct {@link PoAnalyzer} for an Object that is supposed to be
 * persisted (ideally a {@link PersistentObject}) and the manager it uses.
 */
public interface PoAnalyzerResolver {

	/**
	 * Finds the correct {@link PoAnalyzer} for an Object that is supposed to be
	 * persisted (ideally a {@link PersistentObject}) and the manager it uses.
	 * 
	 * @param clz
	 *            Class of the entity
	 * @param manager
	 *            Used Manager
	 * @return Proper {@link PoAnalyzer}
	 */
	PoAnalyzer getAnalyzer(Class<?> clz, String manager);
}