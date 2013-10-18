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
package gr.interamerican.bo2.creation.update;

import gr.interamerican.bo2.creation.exception.ClassCreationException;

import java.util.Arrays;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * {@link MultipleClassUpdater} does the changes that would be done
 * by an array of {@link AbstractClassUpdater} in one step.
 * 
 */
public class MultipleClassUpdater 
extends AbstractClassUpdater {
	
	/**
	 * Updaters that will do the job.
	 */
	List<AbstractClassUpdater> updaters;

	
	/**
	 * Creates a new MultipleClassUpdater object. 
	 *
	 * @param updaters
	 */
	public MultipleClassUpdater(AbstractClassUpdater... updaters) {
		this(Arrays.asList(updaters));
	}
	
	/**
	 * Creates a new MultipleClassUpdater object. 
	 *
	 * @param updaters
	 */
	public MultipleClassUpdater(List<AbstractClassUpdater> updaters) {
		super();
		this.updaters = updaters;
	}


	@Override
	public void updateType(CtClass typeToUpdate) 
	throws CannotCompileException, NotFoundException, ClassCreationException {
		for (AbstractClassUpdater updater : updaters) {
			updater.updateType(typeToUpdate);
		}		
	}

}
