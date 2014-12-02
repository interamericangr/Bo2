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
package gr.interamerican.bo2.arch;

import java.io.Serializable;

/**
 * Interface of a utility that reads persistent objects.  
 * 
 * @param <K> 
 *        Type of key.
 * @param <P> 
 *        Type of {@link PersistentObject}
 *        
 * @deprecated Use {@link PoReader} instead. 
 * 
 */
public interface FastPoReader
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> 
extends PoReader<K, PersistentObject<K>> {
	/* empty */
}
