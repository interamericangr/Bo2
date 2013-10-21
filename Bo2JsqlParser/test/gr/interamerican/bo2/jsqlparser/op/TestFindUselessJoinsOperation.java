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
package gr.interamerican.bo2.jsqlparser.op;

import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


/**
 * test case for {@link UselessJoinsFinder}
 */
public class TestFindUselessJoinsOperation {

	/**
	 * sqls to test. map between the sql staement and the number of joins needed to be removed.
	 */
	static Map<String, Integer> sqls = new HashMap<String, Integer>();
	static {
		sqls.put("select a.a,b.b,l.j from a,b,c join e on e.a=a.a join f on f.a=a.a " //$NON-NLS-1$
				+ "join k on k.a=a.a join l on k.b=l.b where d.a=a.a", 3); //$NON-NLS-1$
		// c , e and f should be removed
		sqls.put("select * from a,b,c", 0); //$NON-NLS-1$
		sqls.put("select a.* from a,b,c", 2); //$NON-NLS-1$
		sqls.put("select a.a,(select b.b from b) from a,b", 1); //$NON-NLS-1$
		sqls.put("select a.* from a,b,c group by b.a having max(b.a)>0", 1); //$NON-NLS-1$
	}

	/**
	 * test method for {@link UselessJoinsFinder#find(String)}
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testFind() throws SqlParseException {
		for (String sql : sqls.keySet()) {
			Assert.assertEquals(sqls.get(sql).intValue(), UselessJoinsFinder.INSTANCE.find(sql).size());
		}
	}

}
