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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.utils.reflect.analyze.AbstractObjectStructureAnalyzer;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ModificationRecordAnalyzer.
 * 
 * @deprecated Use ModificationRecordFieldsAnalyzer
 */
@Deprecated
public class ModificationRecordAnalyzer 
extends AbstractObjectStructureAnalyzer {
	
	@Override
	@SuppressWarnings("nls")
	protected List<VariableDefinition<?>> whichFieldsToInclude(Object object) {
		List<VariableDefinition<?>> fields = new ArrayList<VariableDefinition<?>>();
		if (object!=null && object instanceof ModificationRecord) {
			ModificationRecord mr = (ModificationRecord) object;
			
			VariableDefinition<String> lmb = 
				new VariableDefinition<String>("lastModifiedBy", String.class);
			lmb.setValue(mr.getLastModifiedBy());
			
			VariableDefinition<Date> lma = 
				new VariableDefinition<Date>("lastModified", Date.class);
			lma.setValue(mr.getLastModified());
			
			fields.add(lmb);
			fields.add(lma);
		}
		return fields;
	}

}
