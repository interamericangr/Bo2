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
import gr.interamerican.bo2.impl.open.jdbc.JdbcQuery;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.TransformationSpec;
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
 * <br>
 * 
 * The type of entity returned by the <code>getEntity()</code> method is created
 * dynamically on runtime and contains the elements of the query columns.
 * Therefore StoredDynamicEntitiesQuery is declared as EntitiesQuery&lt;Object&gt;,
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
 * @deprecated Use of this api is not recommended. This might get moved outside
 *             bo2. Switch to more simple Query implementations like
 *             {@link JdbcQuery} and {@link DynamicJdbcQuery}
 */
@Deprecated
public class GenericStoredDynamicEntitiesQuery<C> 
extends AbstractJdbcWorker 
implements  EntitiesQuery<Object>, CriteriaDependent<C>, OrderedFieldsContainer, 
NamedFieldsContainer, OrderedNamedFieldsContainer, TransformationSpec {
	/**
	 * Static map with all reports.
	 */
	static HashMap<String, PredefinedReport> reports = new HashMap<String, PredefinedReport>();	
	
	/**
	 * Wrapped query.
	 */
	PredefinedReportQuery query;
	
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
	 *        Criteria bean. If Class&lt;C&gt; is not Object.class, then this must not be null. 
	 */
	public GenericStoredDynamicEntitiesQuery(String path, C criteria) {
		super();				
		this.path = path;
		PredefinedReport report = reports.get(path);
		if (report!=null) {
			query = new PredefinedReportQuery(report);
		}
		initializeCriteria(criteria);
	}
	
	/**
	 * Creates a new GenericStoredDynamicEntitiesQuery object. 
	 *
	 * @param sql
	 *        SQL query statement.
	 * @param id
	 *        ID for the query.
	 * @param criteria 
	 *        Criteria bean. If Class&lt;C&gt; is not Object.class, then this must not be null. 
	 */
	public GenericStoredDynamicEntitiesQuery(String sql, String id, C criteria) {
		super();				
		this.sql = SqlProcessor.normalizeSql(sql);
		this.id = id;
		if(StringUtils.isNullOrBlank(id)) {
			String msg = "Cannot store a report with null/empty id"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		PredefinedReport report = reports.get(id);
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
		} else if (query.getManagerName() == null) {
			query.setManagerName(managerName);
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
	 * @throws InitializationException the initialization exception
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
			cmd.setSql(sql);
			try {
				cmd.execute();
			} finally {
				cmd.close();
			}
			PredefinedReport report = cmd.getReport();
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
			
			/*
			 * Cache with path if it exists, else cache with id
			 */
			if(path != null) {
				reports.put(path, report);
			} else {
				reports.put(id, report);
			}
		} catch (DataException e) {
			throw new InitializationException(e);
		}
	}	
	
	@Override
	public void close() throws DataException {		
		query.close();		
		super.close();
	}
	
	
	@Override
	public void execute() throws DataException {
		query.execute();
	}
	
	@Override
	public Object getEntity() throws DataAccessException {		
		return query.getEntity();
	}
	
	@Override
	public boolean next() throws DataAccessException {		
		return query.next();
	}
	
	@Override
	public int getRow() throws DataAccessException {		
		return query.getRow();
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {
		this.avoidLock = avoidLock;
		if (query!=null) {
			query.setAvoidLock(avoidLock);
		}
	}

	@Override
	public boolean isAvoidLock() {		
		return avoidLock;
	}

	@Override
	public int getFieldOrder(String field) {
		return query.getFieldOrder(field);
	}

	@Override
	public String getFieldName(int field) {
		return query.getFieldName(field);

	}

	@Override
	public int getFieldsCount() {
		return query.getFieldsCount();
	}

	@Override
	public String getString(int field) throws DataAccessException {
		return query.getString(field);
	}

	@Override
	public BigDecimal getBigDecimal(int field) throws DataAccessException {
		return query.getBigDecimal(field);
	}

	@Override
	public double getDouble(int field) throws DataAccessException {
		return query.getDouble(field);
	}

	@Override
	public float getFloat(int field) throws DataAccessException {
		return query.getFloat(field);
	}

	@Override
	public int getInt(int field) throws DataAccessException {
		return query.getInt(field);
	}

	@Override
	public long getLong(int field) throws DataAccessException {
		return query.getLong(field);
	}

	@Override
	public short getShort(int field) throws DataAccessException {
		return query.getShort(field);
	}

	@Override
	public boolean getBoolean(int field) throws DataAccessException {
		return query.getBoolean(field);
	}

	@Override
	public byte getByte(int field) throws DataAccessException {
		return query.getByte(field);
	}

	@Override
	public byte[] getBytes(int field) throws DataAccessException {
		return query.getBytes(field);
	}

	@Override
	public Date getDate(int field) throws DataAccessException {
		return query.getDate(field);
	}

	@Override
	public Calendar getCalendar(int field) throws DataAccessException {
		return query.getCalendar(field);
	}

	@Override
	public Object getObject(int field) throws DataAccessException {
		return query.getObject(field);
	}
	
	@Override
	public void setCriteria(C criteria) {
		this.criteria = criteria;
		if (query!=null) {
			query.setCriteria(criteria);			
		}		
	}

	@Override
	public C getCriteria() {
		return criteria;
	}	
	
	@Override
	public String getString(String field) throws DataAccessException {
		return query.getString(field);
	}

	@Override
	public BigDecimal getBigDecimal(String field) throws DataAccessException {
		return query.getBigDecimal(field);
	}

	@Override
	public double getDouble(String field) throws DataAccessException {
		return query.getDouble(field);
	}

	@Override
	public float getFloat(String field) throws DataAccessException {
		return query.getFloat(field);
	}

	@Override
	public int getInt(String field) throws DataAccessException {
		return query.getInt(field);
	}

	@Override
	public long getLong(String field) throws DataAccessException {
		return query.getLong(field);
	}

	@Override
	public short getShort(String field) throws DataAccessException {
		return query.getShort(field);
	}

	@Override
	public boolean getBoolean(String field) throws DataAccessException {
		return query.getBoolean(field);
	}

	@Override
	public byte getByte(String field) throws DataAccessException {
		return query.getByte(field);
	}

	@Override
	public byte[] getBytes(String field) throws DataAccessException {
		return query.getBytes(field);
	}

	@Override
	public Date getDate(String field) throws DataAccessException {
		return query.getDate(field);
	}

	@Override
	public Calendar getCalendar(String field) throws DataAccessException {
		return query.getCalendar(field);
	}

	@Override
	public Object getObject(String field) throws DataAccessException {
		return query.getObject(field);
	}
	
	@Override
	public Class<?> getArgumentType() {
		if (query==null) {
			return null;
		}		
		return query.getArgumentType();
	}

	@Override
	public Class<?> getResultType() {
		if (query==null) {
			return null;
		}		
		return query.getResultType();
	}

}
