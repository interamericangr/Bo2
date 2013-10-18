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
package gr.interamerican.bo2.impl.open.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A condition checker can store boolean values associated with
 * a message.
 * 
 * The check method of the checker will return a list with the
 * messages associated with the values that are true.
 * 
 * @param <M> Type of message.
 */
public class ConditionChecker<M> {
	
	/**
	 * Map with controls.
	 */
	private HashMap<M, Boolean> controls = 
		new HashMap<M, Boolean>();
	
	/**
	 * Adds a control.
	 * 
	 * @param messageId
	 * @param condition
	 */
	public void addControl(M messageId, Boolean condition) {
		controls.put(messageId, condition);
	}
	
	/**
	 * Checks which conditions are true.
	 * 
	 * @return Returns a list with the messageIds of the conditions
	 *         that are true.
	 */
	public List<M> check() {
		List<M> trues = new ArrayList<M>();
		for (Map.Entry<M, Boolean> condition : controls.entrySet()) {
			if (condition.getValue()) {
				trues.add(condition.getKey());
			}			
		}
		return trues;
	}

}
