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
package gr.interamerican.bo2.utils.greek;

import gr.interamerican.bo2.utils.greek.adapters.TestSuiteBo2UtilsGreekAdapters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;




/**
 * Test suite for package <code>gr.interamerican.bo2.utils.adapters.string</code>.
 * 
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{	
		TestGreekLetters.class,
		TestGreekUtils.class,
		TestRemoveToneSigns.class,
		TestVisuallySimilarLatin.class,
		TestTranscription743.class,
		TestSuiteBo2UtilsGreekAdapters.class,
	}
)
public class TestSuiteBo2UtilsGreek {
	/* empty */
}
