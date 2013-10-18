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
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistenceWorkerFactory;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.utils.meta.ValidatorRegistry;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.Properties;

/**
 * Implementation of {@link PersistenceWorkerFactory}.
 * 
 * This implementation searches to find the appropriate implementation
 * type of {@link PersistenceWorker} for each {@link PersistentObject}
 * type in a mappings file. If no mapping is found in this file for
 * a specified subclass of PersistentObject, then the factory will
 * automatically create a {@link GenericHibernatePersistenceWorker} for this
 * type, provided that the property <i>Hibernate</i> is set to true
 * in the deployment.properties file. <br/>
 * In the case that a GenericHibernatePersistenceWorker is created, the
 * factory uses the utility class {@link ValidatorRegistry} searching for a
 * registered validator of the persistent class. If there is a validator
 * registered for the persistent class, then it is passed as a parameter
 * to the constructor of the GenericHibernatePersistenceWorker.
 */
public class PersistenceWorkerFactoryImpl 
extends gr.interamerican.bo2.impl.open.creation.PersistenceWorkerFactoryImpl {
	
	
	
	/**
	 * Creates a new PersistenceWorkerFactoryImpl object. 
	 *
	 * @param properties
	 */
	public PersistenceWorkerFactoryImpl(Properties properties) {
		super(properties);		
	}
	
	@Override
	public <M extends PersistentObject<?>> 
	PersistenceWorker<M> createPw(Class<M> poClass) {
		PersistenceWorker<M> pw = createMappedPw(poClass);
		if (pw==null) {
			Validator<M> validator = ValidatorRegistry.getValidator(poClass);			
			pw = GenericHibernatePersistenceWorker.newInstance(poClass, validator);
			@SuppressWarnings("unchecked") Class<? extends PersistenceWorker<?>> pwType = 
				(Class<? extends PersistenceWorker<?>>) GenericHibernatePersistenceWorker.class;
			associate(poClass, pwType);
		}
		return pw;
	}


}
