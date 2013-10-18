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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import java.util.Map;
import java.util.Properties;

/**
 * Factory for {@link BatchProcessParm} objects.
 */
public interface BatchProcessParmsFactory {

	/**
	 * Creates a {@link BatchProcessParm} from a Properties object.
	 * 
	 * @param properties
	 * 
	 * @return Returns the {@link BatchProcessParm}.
	 */	
	BatchProcessParm<?> createParameter(Properties properties);
	
	/**
	 * Creates a {@link BatchProcessParm} from a BatchProcessInput and a criteria object.
	 * 
	 * @param input
	 * @param criteria
	 * @param inputFileDefinitions 
	 *        key: logical name (no spaces)
	 *        value: filesystem path.
	 * 
	 * @return Returns the {@link BatchProcessParm}.
	 */
	BatchProcessParm<?> createParameter(BatchProcessInput input, Object criteria, Map<String, String> inputFileDefinitions);
	
}
