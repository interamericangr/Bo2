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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Base class for worker implementations.
 * 
 * Deals with handling of child-workers.
 * Also allows fields to be annotated with the {@link Parameter} annotation.
 * <br/>
 * The {@link Child} annotation can be used to mark child {@link Worker} 
 * fields of Worker objects. When an attribute of a Worker is annotated 
 * as child worker, then its <code>init(p)</code> method will be called 
 * by the <code>init()</code> method of the parent Worker. The same will 
 * happen also for the methods <code>open()</code> and <code>close()</code>.
 * <br/>
 */
public abstract class AbstractBaseWorker 
implements Worker {
	
	
	/**
	 * message key for worker not open
	 */
	private static final String NOT_INITIALIZED = 
		"AbstractBaseWorker.NOT_INITIALIZED"; //$NON-NLS-1$

	/**
	 * message key for worker not initialized
	 */
	private static final String NOT_OPEN = 
		"AbstractBaseWorker.NOT_OPEN"; //$NON-NLS-1$
	
	/**
	 * message key for worker not open
	 */
	@SuppressWarnings("unused")
	private static final String NO_PARAMETER_FIELD = 
		"AbstractBaseWorker.NO_PARAMETER_FIELD"; //$NON-NLS-1$
	
	/**
	 * indicates if the worker's <code>init(parent)</code> method has been called.
	 */
	
	protected boolean initialized=false;
	
	/**
	 * Provider of this worker.
	 */
	Provider provider=null;
	
	/**
	 * holds all child workers.
	 */
	List<Field> childFields;
	
	/**
	 * holds all parameters with the field name as key.
	 */
	Set<Field> parameterFields;
	
	/**
	 * holds all parameters with the field name as key.
	 */
	Set<Field> parameterBeanFields;
	

	/**
	 * indicates if the worker is in state open.
	 * 
	 * An open state means that the worker is ready to be used.
	 */
	boolean open=false;
	
	/**
	 * indicates if the worker has been initialized.
	 * 
	 * @return returns true if the worker has been initialized.
	 */
	public boolean isInitialized() {
		return initialized;
	}


	/**
	 * indicates if the worker is in state open.
	 * 
	 * @return returns true if the worker is in state open.
	 */
	public boolean isOpen() {
		return open;
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		for (Worker child : getChildren()) {			
			child.init(parent);
		}
		provider = parent;
		initialized = true;
	}
	
	
	
	/**
	 * Gets the children workers.
	 * 
	 * @return Returns a list containing the child workers of this
	 *         worker. 
	 */
	List<Worker> getChildren() {
		return ReflectionUtils.getValuesOfFields
			(this, childFields, Worker.class, false);
	}
	
	/**
	 * Creates a new AbstractBaseWorker object.
	 * 
	 */
	public AbstractBaseWorker() {
		super();
		TypeAnalysis analysis = TypeAnalysis.analyze(this.getClass());
		childFields = new ArrayList<Field>();
		Set<Field> children = analysis.getAnnotated(Child.class);
		if (children!=null) {
			for (Field field : children) {
				field.setAccessible(true);
				childFields.add(field);
			}
		}
		Set<Field> parameters = analysis.getAnnotated(Parameter.class); 
		parameterFields = new HashSet<Field>();
		parameterBeanFields = new HashSet<Field>();
		
		if (parameters!=null) {
			for (Field field : parameters) {
				ReflectionUtils.setAccessible(field);
				Parameter anno = field.getAnnotation(Parameter.class);
				boolean isBean = anno.isBean();
				if (isBean) {
					parameterBeanFields.add(field);
				} else {
					parameterFields.add(field);
				}
			}
		}
	}

	@Override
	public void open() throws DataException {
		if (!initialized) {
			throw new DataException(NOT_INITIALIZED);
		}
		for (Worker child : getChildren()) {
			child.open();
		}
		open = true;
	}

	@Override
	public void close() throws DataException {
		validateOpen();
		for (Worker child : getChildren()) {
			child.close();
		}
		open = false;
	}
	
	/**
	 * Checks that the worker is open.
	 * 
	 * If the worker is not open, then a runtime exception is thrown.
	 * The method should be called before trying any operation that
	 * requires the object to have been opened previously.
	 * 
	 * @throws DataException 
	 * 
	 */
	protected void validateOpen() throws DataException {
		if (!open) {
			throw new DataException(NOT_OPEN); 
		}
	}

	@Override
	public Provider getProvider() {		
		return provider;
	}
	
	/**
	 * Gets the values of the fields marked as parameters on this object.
	 * 
	 * @return Returns a map that maps the names of this object's named
	 *         parameters to their values.
	 */
	public Map<String, Object> getNamedParameters() {
		Map<String, Object> values = new HashMap<String, Object>();
		for (Field field : parameterFields) {
			String name = field.getName();
			Object value = ReflectionUtils.get(field, this); 
			values.put(name, value);
		}
		for (Field field : parameterBeanFields) {
			Object bean = ReflectionUtils.get(field, this);
			if (bean!=null) {				
				TypeAnalysis fieldAnalysis = TypeAnalysis.analyze(field.getType());
				Map<String, Object> beanProperties = 
					fieldAnalysis.getPropertyValues(bean);
				values.putAll(beanProperties);
			}
		}
		return values;
	}
	
	/**
	 * Gets the parameters as an array of objects according to the order
	 * defined in this class with the annotation {@link ParametersOrder}.
	 * 
	 * @return Returns an array of objects.
	 */
	protected Object[] getParamsFromNamedParams() {		
		String[] fields = getParameterNamesArray();
		if (fields==null) {
			return null;
		}
		Object[] parms = new Object[fields.length];		
		Map<String, Object> namedParameters = this.getNamedParameters();
		for (int i = 0; i < fields.length; i++) {
			parms[i] = namedParameters.get(fields[i]);
		}
		return parms;
	}
	
	/**
	 * Gets an array that contains the names of this workers' parameters
	 * ordered as they must be entered to the underlying data layer object.
	 * The base implementation leverages the existence of a {@link ParametersOrder}
	 * annotation on the class.
	 * 
	 * @return Returns an array with the parameter names.
	 */
	protected String[] getParameterNamesArray() {
		return Bo2AnnoUtils.getParameterNames(this.getClass());
	}


}
