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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.util.Date;

/**
 * Implementation of {@link ModificationRecord}.
 */
public class ModificationRecordImpl implements ModificationRecord {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ����� ������� ���������������.
	 */
	private Date lastModified;
	
	/**
	 * ������� ��� ����� ��� ��������� �����������.
	 */
	private String lastModifiedBy;

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (obj instanceof ModificationRecordImpl) {
			ModificationRecordImpl that = (ModificationRecordImpl) obj;
			return Utils.equals(this.lastModified, that.lastModified)
			    && Utils.equals(this.lastModifiedBy, that.lastModifiedBy);
		}
		return false;
	}
	
	@Override
	public int hashCode() {	
		Object[] values = {
				lastModified, lastModifiedBy
		};
		return Utils.generateHashCode(values);
	}
	
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		if(getLastModified()!=null) {
			return getLastModified().toString() + " - " + StringUtils.toString(getLastModifiedBy());
		}
		return "NULL - " + StringUtils.toString(getLastModifiedBy());
	}
	
}
