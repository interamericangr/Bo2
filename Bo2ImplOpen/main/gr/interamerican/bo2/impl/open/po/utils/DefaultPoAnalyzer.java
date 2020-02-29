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

import gr.interamerican.bo2.impl.open.po.analysis.PoAnalysis;


/**
 * Default implementation for {@link PoAnalyzer}.<br>
 * Basically this is not supported as is.
 */
public class DefaultPoAnalyzer implements PoAnalyzer {

	@Override
	public PoAnalysis getAnalysis(Class<?> class1) {
		throw new RuntimeException("Not Supported"); //$NON-NLS-1$
	}
}