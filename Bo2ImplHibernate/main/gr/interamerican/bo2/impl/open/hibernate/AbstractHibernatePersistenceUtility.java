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

import gr.interamerican.bo2.arch.PersistenceUtility;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.enums.TargetEnvironment;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze.HibernateAwarePoAnalyzer;
import gr.interamerican.bo2.impl.open.state.CrudStates;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Tree;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.validators.Validator;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.UnresolvableObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link PersistenceUtility} based on Hibernate.
 * 
 * @param <P> Type of object that is persisted by this utility. Hibernate 
 *            mapping for this type is necessary.
 *            
 */
public abstract class AbstractHibernatePersistenceUtility<P> 
extends AbstractHibernateWorker
implements PersistenceUtility<P> {
	
	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(AbstractHibernatePersistenceUtility.class);
	
	/**
	 * Class of P.
	 */	
	private Class<P> poClass;	

	/**
	 * Validator.
	 */
	private Validator<P> validator;
	
	/**
	 * Refresh mode.
	 */
	private RefreshMode mode;
	
	/**
	 * Creates a new GenericHibernatePersistenceWorker object. 
	 * 
	 * @param poClass 
	 *        Persistent class.
	 * @param validator 
	 *        Validator used to validate objects before save.
	 * @param mode 
	 *        Refresh mode.
	 */
	public AbstractHibernatePersistenceUtility(Class<P> poClass, Validator<P> validator, RefreshMode mode) {
		super();
		this.poClass = poClass;
		this.validator = validator;
		this.mode = mode;
	}
	
	
	/**
	 * Prepares the object for the persistence operation.
	 * 
	 * @param po
	 *        Object to prepare for persistence.
	 */
	protected abstract void prepareObject(P po);
	
	/**
	 * Gets the unique id of the persistent object.
	 * 
	 * @param po
	 *        Persistent object.
	 *        
	 * @return Returns the unique id of the specified entity.
	 */
	protected abstract Serializable getUniqueId(P po);
	
	/**
	 * Reads an object.
	 * 
	 * @param o
	 * 
	 * @return Returns the object.
	 * 
	 * @throws HibernateException
	 * @throws PoNotFoundException
	 */
	P readFromDB(P o) throws HibernateException, PoNotFoundException {
		Serializable uid = getUniqueId(o);
		@SuppressWarnings("unchecked") 
		P po = (P) mode.getOnRead().get(session, uid, poClass);		
		if (po==null) {
			String message = StringUtils.toString(uid);
			throw new PoNotFoundException(message);
		}	
		register(po);
		return po;
	}

	public P read(P o) 
	throws DataException, PoNotFoundException {		
		try {
			Debug.setActiveModule(this);
			validateOpen();
			log("About to read object", o); //$NON-NLS-1$			
			return readFromDB(o);
		} catch (UnresolvableObjectException e) {			
			throw new PoNotFoundException(e);			
		} catch (HibernateException e) {
			throw newDataException(e,o);
		} finally {
			Debug.resetActiveModule();			
			Bo2Session.setState(null);
		}
	}

	public P delete(P o) 
	throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);
			validateOpen();
			Bo2Session.setState(CrudStates.PRE_DELETE);
			prepareObject(o);
			log("About to delete object", o); //$NON-NLS-1$			 
			P managed = o;			
			if (!session.contains(o)) {
				managed = readFromDB(o);
			}			
			super.deleteEntity(managed);			
			return managed; 
		} catch (HibernateException e) {			
			throw newDataException(e,o);
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}
	}

	public P store(P o) 
	throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);
			validateOpen();
			Bo2Session.setState(CrudStates.PRE_STORE);
			prepareObject(o);			
			validate(o);
			log("About to store object", o); //$NON-NLS-1$
			saveEntity (o);
			Serializable uid = getUniqueId(o);
			@SuppressWarnings("unchecked") 
			P po = (P) mode.getOnStore().get(session, uid, poClass);	
			return po; 
		} catch (HibernateException e) {			
			throw newDataException(e,o);
		} catch (ValidationException ve) {
			throw new DataException(ve);
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}
	}
	
	public P update(P o) 
	throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);
			validateOpen();
			Bo2Session.setState(CrudStates.PRE_UPDATE);
			prepareObject(o);
			validate(o);
			log("About to update object", o); //$NON-NLS-1$
			@SuppressWarnings("unchecked")
			P merged = (P) mergeEntity(o);
			Serializable uid = getUniqueId(merged);
			@SuppressWarnings("unchecked")	
			P po = (P) mode.getOnUpdate().get(session, uid, poClass);
			return po; 
		} catch (HibernateException e) {			
			throw newDataException(e,o);
		} catch (ValidationException ve) {
			throw new DataException(ve);			
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}
	}
		
	public boolean ignoresSomething() {		
		return false;
	}
	
	/**
	 * Logs a persistent object.
	 * 
	 * @param message Message.
	 * @param po Object to log.
	 */
	@SuppressWarnings("nls")
	private void log(String message, P po) {
		if (logger.isTraceEnabled()) {
			logger.trace(message);
			Serializable uid = getUniqueId(po);						
			logger.trace("Key: " + uid.toString());
		}
	}
	
	/**
	 * Validates the specified object.
	 * @param po
	 * @throws ValidationException
	 */
	private void validate(P po) throws ValidationException {
		if (validator!=null) {
			validator.validate(po);
		}
	}
	
	@Override
	protected String managerNameNotSet() {	
		return managerNameNotSetToClass(poClass);
	}

	/**
	 * Gets the poClass.
	 *
	 * @return Returns the poClass
	 */
	protected Class<P> getPoClass() {
		return poClass;
	}
	
	/**
	 * Creates a {@link DataException} wrapper for a {@link HibernateException}.
	 * 
	 * This method also logs the caught HibernateException.
	 * 
	 * @param he
	 *        HibernateException that is being wrapped inside a new 
	 *        DataeXception.
	 * @param o 
	 *        Persistent object.
	 *        
	 * @return Returns the DataException.
	 */
	DataException newDataException(HibernateException he, Object o) {
		if (he instanceof StaleObjectStateException) {
			StaleObjectStateException stoe = (StaleObjectStateException) he;
			logStaleObjectException(stoe, o);
		} else {
			if(Bo2.getDefaultDeployment().getDeploymentBean().getTargetEnvironment()==TargetEnvironment.DEVELOPMENT) {
				String entityName = Factory.declarationTypeName(o.getClass());
				Serializable id = ((PersistentObject<?>)o).getKey();
				specialLogHibernateException(entityName, id, o);
			} else {
				logHibernateException(he);
			}
		}
		return new DataException(he);
	}
	
	/**
	 * Logs a StaleObjectStateException.
	 * 
	 * @param he
	 *        StaleObjectStateException. This object contains the class name
	 *        and the id of the object on which the check failed.
	 * @param o
	 *        The object on which the worker operation is performed
	 */
	void logStaleObjectException(StaleObjectStateException he, Object o) {
		logHibernateException(he);
		specialLogHibernateException(he.getEntityName(), he.getIdentifier(), o);
	}
	
	/**
	 * 
	 * 
	 * @param entityName
	 * @param id
	 * @param o
	 */
	@SuppressWarnings("nls")
	void specialLogHibernateException(String entityName, Serializable id, Object o) {
		if (logger.isDebugEnabled()) {
			HibernateAwarePoAnalyzer analyzer = new HibernateAwarePoAnalyzer();
			String message = StringConstants.EMPTY;
			try {
				Tree<VariableDefinition<?>> root = analyzer.execute(o);
				message = StringUtils.concat(
						"\n=======================================================================",
						"\nWorker operation performed on:\n", 
						root.toString());
				
				Class<?> persistenctClass = Class.forName(entityName);
				Object po = session.get(persistenctClass, id);
				Tree<VariableDefinition<?>> dirty = analyzer.execute(po);
				message = StringUtils.concat(
						message,
						"\nObject in session was:\n", 
						dirty.toString());
				
				session.refresh(po);
				Tree<VariableDefinition<?>> clean = analyzer.execute(po);
				message = StringUtils.concat(
						message,
						"\nObject in database is:\n", 
						clean.toString(),
						"\n=======================================================================");

				logger.debug(message);
			} catch (HibernateException e) {
				String msg = StringUtils.concat(
					"HibernateException while trying to log a StaleObjectException: ",
					e.toString(),
					" Salvaged following log fragment:");
				logger.debug(msg);
				logger.debug(message+"\n=======================================================================");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				String msg = StringUtils.concat(
					"ClassNotFoundException while trying to log a StaleObjectException: ",
					e.toString(),
					" Salvaged following log fragment:");
				logger.debug(msg);
				logger.debug(message+"\n=======================================================================");
				e.printStackTrace();
			} catch (RuntimeException e) {
				String msg = StringUtils.concat(
						"RuntimeException while trying to log a StaleObjectException: ",
						e.toString(),
						" Salvaged following log fragment:");
				logger.debug(msg);
				logger.debug(message+"\n=======================================================================");
				e.printStackTrace();
			} 
		}
	}
	
	
	
	
}
