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
package gr.interamerican.wicket.bo2.markup.html.formcomponent;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.samples.utils.meta.ext.MoneyOwnerObject;
import gr.interamerican.bo2.samples.utils.meta.ext.MoneyOwnerObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

/**
 * 
 */
public class MoneyPage extends WebPage{

	/**
	 * MoneyOwnerObjectDescriptor.
	 */
	BasicBusinessObjectDescriptor<MoneyOwnerObject> moneyDesc = 
		new BasicBusinessObjectDescriptor<MoneyOwnerObject>();

	/**
	 * 
	 */
	private static final Map<Class<? extends BoPropertyDescriptor<?> >,BoPropertyDescriptor<?>> map = 
		new HashMap<Class<? extends BoPropertyDescriptor<?> >,BoPropertyDescriptor<?> >();
	
	/**
	 * 
	 */
	static {		
		getDescriptors();
	}
	
	/**
	 * 
	 */
	private static void getDescriptors(){
		MoneyOwnerObjectDescriptor  descriptor =  new MoneyOwnerObjectDescriptor();
		List<BoPropertyDescriptor<?>> descriptors = descriptor.getPropertyDescriptors();		 
		for(BoPropertyDescriptor<?> desc : descriptors){			 
			if (desc instanceof MoneyBoPropertyDescriptor){
				map.put(MoneyBoPropertyDescriptor.class, desc);
			}
		}	
	}
	/**
	 * Creates a new MoneyPage object. 
	 *
	 */
	public MoneyPage() {
		super();
        BoPropertyDescriptor<Money> moneyDescr = 
        	(MoneyBoPropertyDescriptor) map.get(MoneyBoPropertyDescriptor.class);
        Model<Money> model = new Model<Money>(new MoneyImpl(new BigDecimal("5"))); //$NON-NLS-1$
        SelfDrawnMoneyField selfDrawnMoneyField = new SelfDrawnMoneyField("component",model,(MoneyBoPropertyDescriptor) moneyDescr); //$NON-NLS-1$
        this.add(selfDrawnMoneyField);
	} 
	
	/**
	 * Creates a new MoneyPage object.
	 * 
	 * @param component
	 * 
	 */
	public MoneyPage(Component component) {
		super();
        this.add(component);
	} 
		
}
