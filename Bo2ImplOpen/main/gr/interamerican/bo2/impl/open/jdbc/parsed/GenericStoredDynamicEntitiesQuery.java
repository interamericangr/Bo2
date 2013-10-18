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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.def.NamedFieldsContainer;
import gr.interamerican.bo2.arch.def.OrderedFieldsContainer;
import gr.interamerican.bo2.arch.def.OrderedNamedFieldsContainer;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.jdbc.AbstractJdbcWorker;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.AnyOperationSpec;
import gr.interamerican.bo2.utils.sql.SqlProcessor;
import gr.interamerican.bo2.utils.sql.elements.PredefinedReport;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/** 
 * {@link GenericStoredDynamicEntitiesQuery} is a query that has its SQL query
 * statement stored in a resource file and also implements the interface 
 * {@link EntitiesQuery}. Alternatively, the SQL query can be supplied on
 * as a String along with a (unique) id. 
 * <br/>
 * 
 * The type of entity returned by the <code>getEntity()</code> method is created
 * dynamically on runtime and contains the elements of the query columns.
 * Therefore StoredDynamicEntitiesQuery is declared as EntitiesQuery<Object>,
 * however the type of Object is a synthetic class that is created on runtime
 * and contains one field for each column of the row.
 * 
 * The convention for the property names of the synthetic results
 * bean of the {@link GenericStoredDynamicEntitiesQuery} is as follows: The result 
 * set column names are converted to property names by converting all letters
 * to small case and capitalizing each letter that is preceded by an underscore.
 * For example, FIRST_NAME column corresponds to firstName property.
 * 
 * @param <C> 
 *        Type of criteria.
 * 
 */
public class GenericStoredDynamicEntitiesQuery<C> 
extends AbstractJdbcWorker 
implements  EntitiesQuery<Object>, CriteriaDependent<C>, OrderedFieldsContainer, 
NamedFieldsContainer, OrderedNamedFieldsContainer, AnyOperationSpec {
	/**
	 * Static map with all reports.
	 */
	private static HashMap<String, PredefinedReport> reports = 
		new HashMap<String, PredefinedReport>();	
	
	/**
	 * Wrapped query.
	 */
	PredefinedReportQuery query;
	
	/**
	 * Predefined report for the query. 
	 */
	PredefinedReport report;
	
	/**
	 * Path to the statement.
	 */
	String path;
	
	/**
	 * SQL.
	 */
	String sql;
	
	/**
	 * Id.
	 */
	String id;
	
	/**
	 * avoid lock.
	 */
	boolean avoidLock=false;
	
	/**
	 * Criteria.
	 */
	C criteria;
	
	/**
	 * Creates a new GenericStoredDynamicEntitiesQuery object. 
	 *
	 * @param path
	 *        Path to the file that contains the SQL query statement.
	 * @param criteria 
	 *        Criteria bean. If Class<C> is not Object.class, then this must not be null. 
	 */
	public GenericStoredDynamicEntitiesQuery(String path, C criteria) {
		super();				
		this.path = path;
		this.report = reports.get(path);
		if (report!=null) {
			query = new PredefinedReportQuery(report);
		}
		initializeCriteria(criteria);
	}
	
	/**
	 * Initializes this object's criteria with the specified parameter.
	 * 
	 * @param initializationCriteria
	 *        Criteria used to initialize this object.
	 */
	private void initializeCriteria(C initializationCriteria) {
		if (initializationCriteria!=null) {
			this.criteria = initializationCriteria;
			if (query!=null) {
				query.setCriteria(initializationCriteria);				
			}
		}
	}
	
	/**
	 * Creates a new GenericStoredDynamicEntitiesQuery object. 
	 *
	 * @param sql
	 *        SQL query statement.
	 * @param id
	 *        ID for the query.
	 * @param criteria 
	 *        Criteria bean. If Class<C> is not Object.class, then this must not be null. 
	 */
	public GenericStoredDynamicEntitiesQuery(String sql, String id, C criteria) {
		super();				
		this.sql = SqlProcessor.normalizeSql(sql);
		this.id = id;
		if(StringUtils.isNullOrBlank(id)) {
			String msg = "Cannot store a report with null/empty id"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		this.report = reports.get(id);
		if (report!=null) {
			query = new PredefinedReportQuery(report);
		}
		initializeCriteria(criteria);
	}
	
	@Override
	public void setManagerName(String managerName) {	
		super.setManagerName(managerName);
		if (query!=null) {
			query.setManagerName(managerName);
		}
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		if (query==null) {
			initPredefinedReportQuery();
		}
		query.init(parent);
	}
		
	@Override
	public void open() throws DataException {	
		super.open();
		query.open();
	}
	
	/**
	 * Initializes the wrapped query.
	 * 
	 * @throws InitializationException
	 */
	@SuppressWarnings("unchecked")
	void initPredefinedReportQuery() throws InitializationException {
		try {
			if(sql == null) {
				String sqlFromFile = StreamUtils.getStringFromResourceFile(path); 
				sql = SqlProcessor.normalizeSql(sqlFromFile);
			}
			CreatePredefinedReportCmd cmd = new CreatePredefinedReportCmd();
			cmd.setManagerName(this.getManagerName());
			cmd.init(this.getProvider());
			cmd.open();
			cmd.setManagerName(this.getManagerName());
			cmd.setSql(sql);
			cmd.execute();
			report = cmd.getReport();
			if(path != null) {
				id = StringUtils.removeCharacter(path, '/');
			}
			report.setUniqueId(id);
			query = new PredefinedReportQuery(report);
			query.setAvoidLock(avoidLock);
			query.setManagerName(this.getManagerName());
			
			if (criteria!=null) {
				query.setCriteria(criteria);
			} else {
				criteria = (C) query.getCriteria(); 
				/*
				 * For this to work, either Class<C> must be Object.class,
				 * or the criteria parameter in the constructors, must not be null.
				 */
			}
			
			if(path == null) {
				reports.put(id, report);
			} else if (id == null) {
				reports.put(path, report);
			}
			cmd.close();
		} catch (DataException e) {
			throw new InitializationException(e);
		}
	}	
	
	@Override
	public void close() throws DataException {		
		query.close();		
		super.close();
	}
	
	
	public void execute() throws DataException {
		query.execute();
	}
	
	public Object getEntity() throws DataAccessException {		
		return query.getEntity();
	}
	
	public boolean next() throws DataAccessException {		
		return query.next();
	}
	
	public int getRow() throws DataAccessException {		
		return query.getRow();
	}

	public void setAvoidLock(boolean avoidLock) {
		this.avoidLock = avoidLock;
		if (query!=null) {
			query.setAvoidLock(avoidLock);
		}
	}

	public boolean isAvoidLock() {		
		return avoidLock;
	}

	public int getFieldOrder(String field) {
		return query.getFieldOrder(field);
	}

	public String getFieldName(int field) {
		return query.getFieldName(field);

	}

	public int getFieldsCount() {
		return query.getFieldsCount();
	}

	public String getString(int field) throws DataAccessException {
		return query.getString(field);
	}

	public BigDecimal getBigDecimal(int field) throws DataAccessException {
		return query.getBigDecimal(field);
	}

	public double getDouble(int field) throws DataAccessException {
		return query.getDouble(field);
	}

	public float getFloat(int field) throws DataAccessException {
		return query.getFloat(field);
	}

	public int getInt(int field) throws DataAccessException {
		return query.getInt(field);
	}

	public long getLong(int field) throws DataAccessException {
		return query.getLong(field);
	}

	public short getShort(int field) throws DataAccessException {
		return query.getShort(field);
	}

	public boolean getBoolean(int field) throws DataAccessException {
		return query.getBoolean(field);
	}

	public byte getByte(int field) throws DataAccessException {
		return query.getByte(field);
	}

	public byte[] getBytes(int field) throws DataAccessException {
		return query.getBytes(field);
	}

	public Date getDate(int field) throws DataAccessException {
		return query.getDate(field);
	}

	public Calendar getCalendar(int field) throws DataAccessException {
		return query.getCalendar(field);
	}

	public Object getObject(int field) throws DataAccessException {
		return query.getObject(field);
	}
	
	public void setCriteria(C criteria) {
		this.criteria = criteria;
		if (query!=null) {
			query.setCriteria(criteria);			
		}		
	}

	public C getCriteria() {
		return criteria;
	}	
	
	public String getString(String field) throws DataAccessException {
		return query.getString(field);
	}

	public BigDecimal getBigDecimal(String field) throws DataAccessException {
		return query.getBigDecimal(field);
	}

	public double getDouble(String field) throws DataAccessException {
		return query.getDouble(field);
	}

	public float getFloat(String field) throws DataAccessException {
		return query.getFloat(field);
	}

	public int getInt(String field) throws DataAccessException {
		return query.getInt(field);
	}

	public long getLong(String field) throws DataAccessException {
		return query.getLong(field);
	}

	public short getShort(String field) throws DataAccessException {
		return query.getShort(field);
	}

	public boolean getBoolean(String field) throws DataAccessException {
		return query.getBoolean(field);
	}

	public byte getByte(String field) throws DataAccessException {
		return query.getByte(field);
	}

	public byte[] getBytes(String field) throws DataAccessException {
		return query.getBytes(field);
	}

	public Date getDate(String field) throws DataAccessException {
		return query.getDate(field);
	}

	public Calendar getCalendar(String field) throws DataAccessException {
		return query.getCalendar(field);
	}

	public Object getObject(String field) throws DataAccessException {
		return query.getObject(field);
	}
	
	public Class<?> getArgumentType() {
		if (query==null) {
			return null;
		}		
		return query.getArgumentType();
	}

	public Class<?> getResultType() {
		if (query==null) {
			return null;
		}		
		return query.getResultType();
	}

}
