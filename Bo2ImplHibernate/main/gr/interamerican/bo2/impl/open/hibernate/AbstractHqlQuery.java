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

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.hibernate.types.EntryUserType;
import gr.interamerican.bo2.impl.open.hibernate.types.UserTypeUtil;
import gr.interamerican.bo2.utils.sql.types.Type;
import gr.interamerican.bo2.utils.sql.types.TypeUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * Abstract implementation of {@link AbstractHibernateQuery} based on HQL.
 * 
 * Fields annotated with {@link Parameter} are used as query named parameters.
 * If a field is a java bean, then all its properties are assumed to be
 * query named parameters. 
 * 
 * @param <P> result row type 
 *
 */
public abstract class AbstractHqlQuery<P> 
extends AbstractHibernateQuery<P>  {
	
	/**
	 * Creates a new AbstractHqlQuery object. 
	 * 
	 * This protected constructor is mainly intended for use by
	 * public no argument constructors of sub-classes.
	 *
	 */
	protected AbstractHqlQuery() {
		super();
	}

	/**
	 * HQL statement of the query.
	 * 
	 * @return Returns the HQL string for the query.
	 */	
	protected abstract String hql();
	
	/**
	 * Query parameters.
	 * 
	 * @return Returns the query parameters.
	 */
	protected Map<String, Object> parameters() {		
		return this.getNamedParameters();
	}
	
	/**
	 * Sets a parameter to a query.
	 * 
	 * If the parameter is of a type that is handled with a custom Bo2
	 * hibernate user type, then it is handled accordingly. The association
	 * between Bo2 hibernate user types and types they handle can be found
	 * in {@link UserTypeUtil}.
	 * 
	 * If a parameter of such as type is passed to the query that is not handled
	 * as a custom user type in the corresponding mappings, an error will ensue.
	 * 
	 * For example:
	 * Branch is {@link TypedSelectable} (i.e. a type represented with the 
	 * custom hibernate user type {@link EntryUserType}, but is also a 
	 * {@link PersistentObject} that has its own hibernate mapping file.
	 * Therefore, a {@link Parameter} of type Branch should not be passed
	 * in an {@link AbstractHqlQuery}!!!
	 * 
	 * @param query
	 *        Query on which the parameter is set.
	 * @param name
	 *        Name of the parameter.
	 * @param parm
	 *        Value of the parameter.
	 */
	private void setParameter(org.hibernate.Query query, String name, Object parm) {
	    if (UserTypeUtil.isUserType(parm)) {
	    	query.setParameter(name, parm);
		} else if (parm instanceof List<?>) {
			query.setParameterList(name, (List<?>) parm);
		} else if (parm instanceof Object[]) {
			query.setParameterList(name, (Object[])parm);
		} else {
			@SuppressWarnings("rawtypes") 
			Type type = TypeUtils.getType(parm);
			@SuppressWarnings("unchecked") 
			Object parameter = type.statementParameter(parm);
			query.setParameter(name, parameter);
			
			/*
			 * ���� ������ �������??
			 * query.setEntity(name, parm);
			 */
		}
	}
	
	
	
	@Override
	protected Iterator<?> createIterator() {
		org.hibernate.Query query = session.createQuery(hql());
		if(parameters()!=null){
			for (Map.Entry<String, Object> entry : parameters().entrySet()) {
				String name = entry.getKey();
				Object parm = entry.getValue();
				setParameter(query, name, parm);
			}
		}
		return query.iterate();
	}
	
	
}
