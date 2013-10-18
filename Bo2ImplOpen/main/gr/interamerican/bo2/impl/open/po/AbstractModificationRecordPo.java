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

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2State;
import gr.interamerican.bo2.impl.open.state.CrudStates;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link AbstractBasePo} with {@link ModificationRecord}.
 * 
 * There is no need to set explicitly the last modification
 * user id. The <code>tidy()</code> method will set the user
 * id of the current {@link Bo2Session} as last modification 
 * user to any child AbstractModificationRecordPo where it 
 * is empty. 
 * 
 * @param <K> 
 * 
 */
public abstract class AbstractModificationRecordPo<K extends Key> 
extends AbstractBasePo<K> 
implements ModificationRecord {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * States to ignore fixing of children.
	 */
	private static Set<Bo2State> prePersistStates = new HashSet<Bo2State>();
	
	static {
		prePersistStates = new HashSet<Bo2State>();
		prePersistStates.add(CrudStates.PRE_STORE);		
		prePersistStates.add(CrudStates.PRE_UPDATE);
	}


	/**
	 * Creates a new AbstractModificationRecordPo object.
	 */
	public AbstractModificationRecordPo() {
		super();
	}
	
	/**
	 * Modification record.
	 */
	private ModificationRecord mdf = new ModificationRecordImpl();

	public Date getLastModified() {
		return mdf.getLastModified();
	}

	public void setLastModified(Date lastModified) {
		mdf.setLastModified(lastModified);
	}

	public String getLastModifiedBy() {
		return mdf.getLastModifiedBy();
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		mdf.setLastModifiedBy(lastModifiedBy);
	}
	
	
	/**
	 * Sets the lastModifiedBy property.
	 */
	void setModifiedBy() {
		if (prePersistStates.contains(Bo2Session.getState())) {			
			if (mdf.getLastModifiedBy()==null) {
				String userId = Bo2Session.getUserId();
				mdf.setLastModifiedBy(userId);					
			}				
		}	
	}
	
	@Override
	protected void doTidy() {
		setModifiedBy();
		super.doTidy();
	}

}
