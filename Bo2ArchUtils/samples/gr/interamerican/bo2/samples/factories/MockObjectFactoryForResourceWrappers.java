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
package gr.interamerican.bo2.samples.factories;

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.samples.providers.MockResourceWrapper;

/**
 * Mock factory. 
 */
public class MockObjectFactoryForResourceWrappers 
implements ObjectFactory  {
	
	@SuppressWarnings("unchecked")
	public <M> M create(Class<M> type) {
		if (ResourceWrapper.class.equals(type)) {
			return (M) new gr.interamerican.bo2.samples.providers.MockResourceWrapper();
		}
		throw new RuntimeException();
	}	
	
	public Class<?> getImplementationType(Class<?> declarationType) {
		if (ResourceWrapper.class.equals(declarationType)) {
			return MockResourceWrapper.class;
		}
		return null;
	}	

	public String getDeclarationTypeName(Class<?> implementationType) {
		return null;
	}
	
	public Class<?> getDeclarationType(Class<?> implementationType) {
		return null;
	}

	public void registerImplementationAsDeclaration(Class<?> declaration) {
		/* empty */
	}
	
	public Object create(String declarationTypeName) {
		return null;
	}

	
	public Class<?> getImplementationType(String declarationTypeName) {
		return null;
	}

	@Override
	public <M> void registerFixture(Class<M> declarationType, M fixture) {
		/* empty */
	}
	
	public <M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory) {
		/* empty */
	}

	@Override
	public void resetFixtures() {
		/* empty */
	}

}
