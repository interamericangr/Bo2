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
package gr.interamerican.bo2.impl.open.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.StaleTransactionException;
import gr.interamerican.bo2.arch.utils.ext.WorkerExecutionDetails;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.impl.open.utils.Messages;
import gr.interamerican.bo2.impl.open.utils.Util;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.SqlUtils;

/**
 * Abstract base class for worker implementations based on JDBC.
 *
 * JDBC based implementations of {@link Worker} use prepared statement
 * for database operations. The SQL statements are defined as string
 * fields of the class. These fields have to be annotated with the
 * annotation SQL.
 *
 */
public abstract class AbstractJdbcWorker
extends AbstractResourceConsumer {

	/**
	 * Logger.
	 */
	private static Logger logger =
			LoggerFactory.getLogger(AbstractJdbcWorker.class);

	/**
	 * Message key for message stating that provided session is null.
	 */
	private static final String NULL_CONNECTION = Messages.NULL_RESOURCE;


	/**
	 * Message key for message stating that a HibernateSessionProvider
	 * could not be acquired.
	 */
	private static final String NO_PROVIDER	=Messages.NO_PROVIDER;


	/**
	 * String that is used as database schema. It is replaced in the runtime by the actual schema
	 */
	private static final String SCHEMA_KEY=Util.getParameter("AbstractJdbcWorker.SCHEMA_KEY"); //$NON-NLS-1$

	/**
	 * SQL connection used by this worker.
	 */
	private Connection connection;

	/**
	 * Database schema used defined by the runtime layer.
	 */
	private String schema;

	/**
	 * HashMap with all statements of the object.
	 */
	HashMap<String, PreparedStatement> statements = new HashMap<String, PreparedStatement>();

	/**
	 * List with all fields marked with the SQL annotation.
	 */
	List<String> sqlStatementFields;

	/**
	 * List with all methods of this object marked with the SQL annotation.
	 */
	List<Method> sqlStatementMethods;

	/**
	 * Sets the SQL connection.
	 *
	 * @param connection Connection to set.
	 */
	private void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Sets the database schema string.
	 *
	 * @param string the new schema
	 */
	private void setSchema(String string) {
		schema = string;
	}

	/**
	 * Replaces the symbolic schema used in the declarations of the sql
	 * statements with the actual database schema string.
	 *
	 * @param sql
	 *            SQL statement as it is defined in the object code.
	 *
	 * @return Returns the SQL statement string, ready to be prepared. The
	 *         symbolic string used for database schema has been replaced by the
	 *         database schema string that was defined by the runtime layer
	 *         provider.
	 */
	private String fix(String sql) {
		if ((SCHEMA_KEY != null) && (schema != null)) {
			return sql.replace(SCHEMA_KEY, schema);
		}
		return sql;
	}

	/**
	 * Sets the parameters for the execution of a statement.
	 *
	 * @param ps Prepared statement to be executed.
	 * @param parms Parameters for the statement.
	 * @throws SQLException the SQL exception
	 */
	protected void setParameters(PreparedStatement ps, Object[] parms)
			throws SQLException {
		ParameterMetaData meta=ps.getParameterMetaData();
		for (int i = 0; i < parms.length; i++) {
			int idx=i+1;
			Object parm=SqlUtils.statementParameter(parms[i]);
			if (parm==null) {
				int type = meta.getParameterType(idx);
				ps.setObject (idx, null, type);
			} else {
				ps.setObject (idx, parm);
			}
		}
	}


	/**
	 * Logs the statement.
	 *
	 * @param statement the statement
	 * @param params the params
	 */
	protected void logStatement(String statement, Object[] params) {
		if (logger.isTraceEnabled()) {
			String stmt = showPreparedStatement(statement, params);
			String parameters = StringUtils.squareBrackets(StringUtils.array2String(params, StringConstants.COMMA));
			logger.trace(stmt + StringConstants.SPACE + parameters);
		}
	}

	/**
	 * Logs parameters passed to a prepared statement before execution.
	 *
	 * @param params the params
	 */
	protected void logPsParameters(Object[] params) {
		if (logger.isTraceEnabled()) {
			String parameters = StringUtils.squareBrackets(StringUtils.array2String(params, StringConstants.COMMA));
			logger.trace("PS parameters: " + parameters); //$NON-NLS-1$

		}
	}

	/**
	 * Logs statement preparation.
	 *
	 * @param statement the statement
	 */
	protected void logStatementPreparation(String statement) {
		if (logger.isTraceEnabled()) {
			logger.trace("Preparing " + statement); //$NON-NLS-1$
		}
	}

	/**
	 * Executers an update.
	 *
	 * @param ps            Statement.
	 * @param statement            Sql statement (for error logging)
	 * @param params            parameters used for the sql statement (for error logging)
	 * @return Returns the count of lines.
	 * @throws SQLException the SQL exception
	 * @throws StaleTransactionException the stale transaction exception
	 */
	protected int executeUpdatePs(PreparedStatement ps, String statement, Object[] params)
	throws SQLException, StaleTransactionException {
		WorkerExecutionDetails details = new WorkerExecutionDetails();
		details.setTaskInfo("Executing statement " + showPreparedStatement(statement, params)); //$NON-NLS-1$
		try {
			Debug.setActiveModule(ps);
			int count = ps.executeUpdate();
			return count;
		} catch (SQLException e) {
			logger.error(showPreparedStatement(statement, params) + StringConstants.NEWLINE
					+ ExceptionUtils.getThrowableStackTrace(e));
			throw e;
		} finally {
			Debug.resetActiveModule();
			checkTransactionHealth(details);
		}
	}

	/**
	 * Executers a query..
	 *
	 * @param ps            PreparedStatement.
	 * @param statement            (plain sql for logging purposes)
	 * @param params            (for logging purposes)
	 * @return Returns the resultset.
	 * @throws SQLException the SQL exception
	 * @throws StaleTransactionException the stale transaction exception
	 */
	protected ResultSet executeQueryPs(PreparedStatement ps, String statement, Object[] params)
	throws SQLException, StaleTransactionException {
		WorkerExecutionDetails details = new WorkerExecutionDetails();
		details.setTaskInfo("Executing statement " + showPreparedStatement(statement, params)); //$NON-NLS-1$
		try {
			Debug.setActiveModule(ps);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.error(showPreparedStatement(statement, params) + StringConstants.NEWLINE
					+ ExceptionUtils.getThrowableStackTrace(e));
			throw e;
		} finally {
			Debug.resetActiveModule();
			checkTransactionHealth(details);
		}
	}


	/**
	 * Creates a PreparedStatement from the sql string.
	 *
	 * Parameter markers are defined in the sql staring with question marks.
	 * Alternatively parameter markers can be marked with named parameters
	 * using a parameter name preceded by a colon (:).
	 * The logical schema string will be replaced by the actual schema string.
	 * The prepared statement is created and put to the statements map with
	 * key the <code>sql</code> parameter.
	 * <p>
	 *
	 * Examples of valid statements are:
	 * <code>select foo, bar from MYTABLE where foo &gt; ? </code> <br>
	 * <code>select foo, bar from MYTABLE where foo &gt; :foo </code> <br>
	 * </p>
	 * @param sql String for the sql statement.
	 * @throws DataException the data exception
	 */
	protected void prepare(String sql)
			throws DataException {
		try {
			prepareSql(sql);
		} catch (SQLException e) {
			logger.error(e.toString());
			throw new DataException(e);
		}
	}

	/**
	 * Creates a PreparedStatement from the sql string.
	 *
	 * Parameter markers are defined in the sql staring with question marks.
	 * Alternatively parameter markers can be marked with named parameters
	 * using a parameter name preceded by a colon (:).
	 * The logical schema string will be replaced by the actual schema string.
	 * The prepared statement is created and put to the statements map with
	 * key the <code>sql</code> parameter.
	 * <p/>
	 * Examples of valid statements are:
	 * <code>select foo, bar from MYTABLE where foo > ? </code> <br>
	 * <code>select foo, bar from MYTABLE where foo > :foo </code> <br>
	 *
	 * @param sql String for the sql statement.
	 * @throws SQLException the SQL exception
	 */
	private void prepareSql(String sql)
			throws SQLException {
		String sqlToPrepare = SqlUtils.replaceParametersWithMarkers(sql);
		String fixedSql=fix(sqlToPrepare);
		logStatementPreparation(fixedSql);
		PreparedStatement ps=connection.prepareStatement(fixedSql);
		statements.put(sql, ps);
	}



	/**
	 * Gets an already prepared statement, or creates a new one.
	 *
	 * @param sql        SQL statement.
	 * @return Returns the prepared statement.
	 * @throws DataException the data exception
	 */
	protected PreparedStatement getPreparedStatement(String sql)
			throws DataException {
		try {
			return getPreparedSqlStatement(sql);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

	/**
	 * Gets an already prepared statement, or creates a new one.
	 *
	 * @param sql        SQL statement.
	 * @return Returns the prepared statement.
	 * @throws SQLException the SQL exception
	 */
	private PreparedStatement getPreparedSqlStatement(String sql)
			throws SQLException {
		PreparedStatement ps = statements.get(sql);
		if (ps==null) {
			prepareSql(sql);
			ps = statements.get(sql);
		}
		return ps;
	}

	/**
	 * Executes a query PreparedStatement.
	 *
	 * @param statement The statement.
	 * @param params        The parameters for the execution of the prepared
	 *        statement. If the statement has no parameters,
	 *        then <code>params</code> should have a value of null.
	 * @return Returns the ResultSet created by the prepared query.
	 * @throws SQLException the SQL exception
	 * @throws DataException the data exception
	 */
	protected ResultSet executePreparedQuery(String statement,Object[] params)
			throws SQLException, DataException {
		validateOpen();
		PreparedStatement ps = getPreparedSqlStatement(statement);
		logStatement(statement, params);
		if (params!=null) {
			setParameters(ps, params);
		}
		return executeQueryPs(ps, statement, params);
	}






	/**
	 * Executes an update PreparedStatement.
	 *
	 * @param statement The statement.
	 * @param params        The parameters for the execution of the prepared
	 *        statement. If the statement has no parameters,
	 *        then <code>params</code> should be null or an
	 *        empty array.
	 * @return Returns the count of rows affected by the statement.
	 * @throws SQLException the SQL exception
	 * @throws DataException the data exception
	 */
	protected int executePreparedUpdate(String statement,Object[] params)
			throws SQLException, DataException {
		validateOpen();
		PreparedStatement ps = getPreparedSqlStatement(statement);
		logStatement(statement, params);
		if (params!=null) {
			setParameters(ps, params);
		}
		int count = executeUpdatePs(ps, statement, params);

		if (logger.isTraceEnabled()) {
			@SuppressWarnings("nls")
			String msg = "Last statement affected " + count + " lines";
			logger.trace(msg);
		}
		return count;
	}

	/**
	 * Executes a query PreparedStatement that has no parameters.
	 *
	 * @param statement The statement.
	 * @return Returns the ResultSet created by the prepared query.
	 * @throws SQLException the SQL exception
	 * @throws DataException the data exception
	 */
	protected ResultSet executePreparedQuery(String statement)
			throws SQLException, DataException {
		return executePreparedQuery(statement,null);
	}

	/**
	 * Creates a string for an SQL statement, replacing parameter markers
	 * (?) with the corresponding parameters.
	 *
	 * @param sql
	 *        String SQL statement of the PreparedStatement.
	 * @param params
	 *        Parameters for the SQL statement.
	 * @return The string containing the SQL statement to be executed.
	 */
	protected String showPreparedStatement(String sql,Object[] params) {
		String tmp = fix(sql);
		if (params==null) {
			return tmp;
		}
		for (int i = 0; i < params.length; i++) {
			String str = null;
			if(params[i] == null) {
				str = StringConstants.NULL;
			} else {
				str=SqlUtils.sqlString(params[i]);
			}
			tmp=tmp.replaceFirst("\\?", str); //$NON-NLS-1$
		}
		return tmp;
	}

	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);

		JdbcConnectionProvider jdbcProvider = getResource(JdbcConnectionProvider.class);
		if (jdbcProvider==null) {
			throw Exceptions.initializationException(NO_PROVIDER);
		}

		setConnection(jdbcProvider.getConnection());
		if (connection==null) {
			throw Exceptions.initializationException
			(NULL_CONNECTION, Connection.class);
		}
		setSchema(jdbcProvider.getDbSchema());
	}

	@Override
	public void open() throws DataException {
		super.open();
		statements.clear();
	}

	@Override
	public void close() throws DataException {
		try {
			for (PreparedStatement ps : statements.values()) {
				ps.close();
			}
			statements.clear();
		} catch (SQLException e) {
			throw new DataException(e);
		}
		super.close();
	}
}