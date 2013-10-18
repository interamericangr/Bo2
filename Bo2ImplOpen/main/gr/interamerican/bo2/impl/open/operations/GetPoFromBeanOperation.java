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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.KeyProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * This operation will get a {@link PersistentObject} from a java bean
 * that contains the elements of its key. <br/>
 * 
 * For this operation to work, the following prerequisites are required:
 * <li> The default Bo2 factory can create instances of the PersistentObject
 *      as wall as its PersistenceWorker. </li>
 * <li> The class or interface of the PersistentObject has the names of the
 *      key properties marked using the {@link KeyProperties} annotation.
 * <li> The input bean has all properties of the key. </li> 
 * 
 * @param <P>
 *        Type of PersistentObject. 
 */
public class GetPoFromBeanOperation<P extends PersistentObject<?>> 
extends AbstractOperation {
	
	/**
	 * Class of PersistentObject.
	 */
	Class<P> poClass;
	
	/**
	 * Persistence worker.
	 */
	@Child PersistenceWorker<P> pw;
	
	/**
	 * Bean containing the key information.
	 */
	Object bean;
	
	/**
	 * Persistent object.
	 */
	P po;
	
	/**
	 * Gets the po.
	 *
	 * @return Returns the po
	 */
	public P getPo() {
		return po;
	}
	
	/**
	 * Gets the bean.
	 *
	 * @return Returns the bean
	 */
	public Object getBean() {
		return bean;
	}

	/**
	 * Assigns a new value to the bean.
	 *
	 * @param bean the bean to set
	 */
	public void setBean(Object bean) {
		this.bean = bean;
	}

	/**
	 * Creates a new GetPoFromBeanOperation object. 
	 *
	 * @param poClass
	 */
	public GetPoFromBeanOperation(Class<P> poClass) {
		super();
		this.poClass = poClass;
		pw = Factory.createPw(poClass);
	}
	
	@Override
	public void execute() throws LogicException, DataException {
		String[] keyProperties = getKeyProperties();
		po = Factory.create(poClass);
		ReflectionUtils.copyProperties(bean, po, keyProperties);
		po = pw.read(po);		
	}
	
	/**
	 * Finds the Key class of a po implementation and then
	 * finds its properties.
	 * 
	 * @return the properties of the Po Key.
	 * @throws LogicException 
	 */	
	String[] getKeyProperties() throws LogicException {
		String[] keyProperties = PoUtils.getKeyProperties(poClass);
		if (keyProperties!=null) {
			return keyProperties;
		}
		/*
		 * this shouldn't be necessary.
		 * 
		PersistentObject<?> persistent = Factory.create(poClass);
		Object key = persistent.getKey();
		Class<?> keyClass = key.getClass();
		Class<?> annotated = ReflectionUtils.getAnnotatedType(keyClass, KeyProperties.class);
		if(annotated != null) {
			return Bo2AnnoUtils.getKeyProperties(annotated);
		}
		*/		

		throw keyPropertiesNotFound();
	}
	
	/**
	 * Creates a {@link LogicException}.
	 * 
	 * @return Returns a LogicException
	 */
	private LogicException keyPropertiesNotFound() {
		String msg = "Could not find Key class of " + poClass.getName(); //$NON-NLS-1$
		return new LogicException(msg);
	}

}
