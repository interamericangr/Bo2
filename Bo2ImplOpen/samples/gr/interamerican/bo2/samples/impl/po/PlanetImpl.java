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
package gr.interamerican.bo2.samples.impl.po;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.samples.def.po.Continent;
import gr.interamerican.bo2.samples.def.po.Planet;
import gr.interamerican.bo2.samples.def.po.PlanetKey;
import gr.interamerican.bo2.samples.def.po.Populated;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Set;

/**
 * The Class PlanetImpl.
 */
@DelegateKeyProperties({})
public abstract class PlanetImpl 
extends AbstractModificationRecordPo<PlanetKey>
implements Planet {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Name.
	 */
	@Property String name;
	
	/**
	 * Child collection.
	 */
	@Child @Property Set<Continent> continents;
	
	@Override
	public int getPopulation() {
		int population = 0;
		for (Populated child : continents) {
			population+=child.getPopulation();
		}
		return population;
	}

}
