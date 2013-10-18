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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DoubleBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.SelectionBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryOwnerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MultipleCachedEntriesBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesOwnersBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.SelectableBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.descriptors.TranslatableBoPropertyDescriptorWrapper;

/**
 * Maps {@link BoPropertyDescriptor} type to {@link BoPDComponentFactory}.
 */
public class BoPDTypeBasedFactorySelection 
extends TypeBasedSelection<BoPDComponentFactory<?>> {
	
	/**
	 * Singleton. 
	 */
	public static final BoPDTypeBasedFactorySelection INSTANCE = new BoPDTypeBasedFactorySelection();
	
	static {
		INSTANCE.registerSelection(StringBoPropertyDescriptor.class, new StringBoPDComponentFactory());		
		INSTANCE.registerSelection(DoubleBoPropertyDescriptor.class, new DoubleBoPDComponentFactory());
		INSTANCE.registerSelection(IntegerBoPropertyDescriptor.class, new IntegerBoPDComponentFactory());
		INSTANCE.registerSelection(LongBoPropertyDescriptor.class, new LongBoPDComponentFactory());
		INSTANCE.registerSelection(BigDecimalBoPropertyDescriptor.class, new BigDecimalBoPDComponentFactory());
		INSTANCE.registerSelection(BooleanBoPropertyDescriptor.class, new BooleanBoPDComponentFactory());
		INSTANCE.registerSelection(CachedEntryBoPropertyDescriptor.class, new CachedEntryBoPDComponentFactory());
		INSTANCE.registerSelection(CachedEntryOwnerBoPropertyDescriptor.class, new CachedEntryOwnerBoPDComponentFactory());
		INSTANCE.registerSelection(DateBoPropertyDescriptor.class, new DateBoPDComponentFactory());
		INSTANCE.registerSelection(FloatBoPropertyDescriptor.class, new FloatBoPDComponentFactory());
		INSTANCE.registerSelection(TranslatableBoPropertyDescriptorWrapper.class, new TranslatableBoPDWrapperComponentFactory<BoPropertyDescriptor<?>>());
		INSTANCE.registerSelection(SelectionBoPropertyDescriptor.class,new SelectionBoPDComponentFactory());
		INSTANCE.registerSelection(SelectableBoPropertyDescriptor.class,new SelectableBoPDComponentFactory());
		INSTANCE.registerSelection(MultipleCachedEntriesBoPropertyDescriptor.class, new MultipleCachedEntriesBoPDComponentFactory());
		INSTANCE.registerSelection(MoneyBoPropertyDescriptor.class, new MoneyBoPDComponentFactory());
		INSTANCE.registerSelection(PalleteCachedEntriesBoPropertyDescriptor.class, new PalleteCachedEntriesForBoPDComponentFactory());
		INSTANCE.registerSelection(PalleteCachedEntriesOwnersBoPropertyDescriptor.class, new PalleteCachedEntriesOwnersBoPDComponentFactory());
	}

}
