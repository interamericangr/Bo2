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
package gr.interamerican.wicket.samples.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.creation.annotations.MockMethods;
import gr.interamerican.bo2.impl.open.workers.AbstractBaseWorker;
import gr.interamerican.bo2.test.def.samples.SamplePoForTest;

/**
 * Sample {@link EntitiesQuery} that returns {@link Codified} entities.
 */
@MockMethods({})
public abstract class SampleCodifiedEntitiesQuery extends AbstractBaseWorker implements EntitiesQuery<SamplePoForTest> {
	// empty
}
