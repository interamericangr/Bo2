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
package gr.interamerican.bo2.samples.hibernate.queries;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.hibernate.AbstractHqlQuery;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.ConditionalStringBuilder;

import java.util.Date;
import java.util.Map;

/**
 * 
 */
public abstract class DynamicQueryImpl extends AbstractHqlQuery<Object> {

	@Override
	@SuppressWarnings("nls")
	protected Map<String, Object> parameters() {
		Map<String, Object> parms = this.getNamedParameters();		
		CollectionUtils.modify(parms, "string", StringUtils.surround(string, "%"));
		return parms;
	}


	@SuppressWarnings("nls")
	@Override
	protected String hql() {
		Map<String, Object> parms = this.getNamedParameters();
		boolean hasWhere = CollectionUtils.containsNull(parms.values());		
		ConditionalStringBuilder main = new ConditionalStringBuilder();		
		main.appendIf("select * from MYTABLE ", true);		
		main.appendIf(" where ", hasWhere);
		
		ConditionalStringBuilder where = new ConditionalStringBuilder();
		where.appendIfNotNull(" int_1 = :integer1 ", integer1);		
		where.appendIfNotNull(" int_2 = :integer2 ", integer2);
		where.appendIfNotNull(" string = :string ", string);
		where.appendIfNotNull(" date = :date ", date);
		
		String orderClause = " order by papaki ";
		return StringUtils.concat(main.toString(), where.toString(), orderClause);
	}
	
	
	
	
	
	/**
	 * integer1
	 */
	@Property @Parameter Integer integer1;
	/**
	 * integer2
	 */
	@Property @Parameter Integer integer2;
	/**
	 * string
	 */
	@Property @Parameter String string;
	/**
	 * date
	 */
	@Property @Parameter Date date;
	
	
	
	
	

}
