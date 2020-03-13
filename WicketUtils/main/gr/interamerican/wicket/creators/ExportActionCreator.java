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
package gr.interamerican.wicket.creators;

import java.io.Serializable;
import java.util.List;

import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;

/**
 * Interface to create a {@link SerializableRunnable} that will export data
 * based on an {@link ExportDataSetup} and the {@link SerializableSupplier}.
 * 
 * @param <T>
 *            Type of Entity to be reported
 */
@FunctionalInterface
public interface ExportActionCreator<T> extends Serializable {

	/**
	 * Creates the action.
	 * 
	 * @param input
	 *            Setup
	 * @param data
	 *            Data
	 * @return Action
	 */
	SerializableRunnable getCreator(ExportDataSetup<T> input, SerializableSupplier<List<T>> data);
}