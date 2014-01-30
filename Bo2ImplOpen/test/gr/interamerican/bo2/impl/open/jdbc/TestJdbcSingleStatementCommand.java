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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link JdbcSimpleQuestion}.
 */
public class TestJdbcSingleStatementCommand extends AbstractNonTransactionalProviderTest {


	/**
	 * Executes.
	 * 
	 * @param cmd
	 * @param id
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 */
	void exec(AbstractJdbcSingleStatementCommandSample cmd, int id)
			throws InitializationException, DataException {
		cmd.init(provider);
		cmd.open();
		cmd.setId(id);
		cmd.execute();
		cmd.close();
	}

	/**
	 * tests the question that uses named parameters.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 */
	@Test
	public void testExecute_withNamedParams()
			throws InitializationException, DataException {
		Integer id = UtilityForBo2Test.getNotExistingUserId();
		exec(new CommandWithNamedParams(), id);
	}

	/**
	 * tests the question that uses named parameters.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 */
	@Test
	public void testExecute_withAnno()
			throws InitializationException, DataException {
		Integer id = UtilityForBo2Test.getNotExistingUserId();
		exec(new CommandWithAnno(), id);
	}



	/**
	 * implementation to test.
	 * 
	 * Commands inheriting from this, are expected to never
	 * delete any record.
	 */
	@ManagerName("LOCALDB")
	private abstract class AbstractJdbcSingleStatementCommandSample
	extends JdbcSingleStatementCommand {

		/**
		 * id parameter.
		 */
		@Parameter private Integer id;

		/**
		 * Assigns a new value to the id.
		 *
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}


		@Override
		protected int executeUpdatePs(PreparedStatement ps, String statement, Object[] params) throws SQLException {
			int recs = super.executeUpdatePs(ps, statement, params);
			Assert.assertEquals(0, recs);
			return recs;
		}
	}



	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB")
	@ParametersOrder("id")
	private class CommandWithAnno
	extends AbstractJdbcSingleStatementCommandSample{
		/**
		 * sgl.
		 */
		@Sql private String sql = "delete from X__X.users where id = :specifiedId"; //$NON-NLS-1$
	}

	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB")
	private class CommandWithNamedParams
	extends AbstractJdbcSingleStatementCommandSample {
		/**
		 * sgl.
		 */
		@Sql private String sql = "delete from X__X.users where id = :id"; //$NON-NLS-1$
	}


}
