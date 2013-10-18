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
package gr.interamerican.bo2.impl.open;

import gr.interamerican.bo2.impl.open.annotations.TestSuiteBo2ImplOpenAnnotations;
import gr.interamerican.bo2.impl.open.beans.TestSuiteBo2ImplOpenBeans;
import gr.interamerican.bo2.impl.open.creation.TestSuiteBo2ImplOpenCreation;
import gr.interamerican.bo2.impl.open.doc.TestSuiteBo2ImplOpenDoc;
import gr.interamerican.bo2.impl.open.jdbc.TestSuiteBo2ImplOpenJdbc;
import gr.interamerican.bo2.impl.open.modifications.TestSuiteBo2ImplOpenModifications;
import gr.interamerican.bo2.impl.open.namedstreams.TestSuiteBo2ImplOpenNamedStreams;
import gr.interamerican.bo2.impl.open.operations.TestSuiteBo2ImplOpenOperations;
import gr.interamerican.bo2.impl.open.parse.TestSuiteBo2ImplOpenParse;
import gr.interamerican.bo2.impl.open.po.TestSuiteBo2ImplOpenPo;
import gr.interamerican.bo2.impl.open.properties.TestSuiteBo2ImplOpenProperties;
import gr.interamerican.bo2.impl.open.records.TestSuiteBo2ImplOpenRecords;
import gr.interamerican.bo2.impl.open.runtime.TestSuiteBo2ImplOpenRuntime;
import gr.interamerican.bo2.impl.open.streams.TestSuiteBo2ImplOpenStreams;
import gr.interamerican.bo2.impl.open.transformations.TestSuiteBo2ImplOpenTransformations;
import gr.interamerican.bo2.impl.open.utils.TestSuiteBo2ImplOpenUtils;
import gr.interamerican.bo2.impl.open.workers.TestSuiteBo2ImplOpenWorkers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Test suite for package <code>gr.interamerican.bo2.impl.open</code>.
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestSuiteBo2ImplOpenAnnotations.class,
		TestSuiteBo2ImplOpenBeans.class,
		TestSuiteBo2ImplOpenCreation.class,
		TestSuiteBo2ImplOpenJdbc.class,
		TestSuiteBo2ImplOpenNamedStreams.class,
		TestSuiteBo2ImplOpenPo.class,
		TestSuiteBo2ImplOpenProperties.class,
		TestSuiteBo2ImplOpenRecords.class,
		TestSuiteBo2ImplOpenRuntime.class,
		TestSuiteBo2ImplOpenStreams.class,
		TestSuiteBo2ImplOpenUtils.class,
		TestSuiteBo2ImplOpenOperations.class,
		TestSuiteBo2ImplOpenWorkers.class,		
		TestSuiteBo2ImplOpenParse.class,
		TestSuiteBo2ImplOpenDoc.class,
		TestSuiteBo2ImplOpenTransformations.class,
		TestSuiteBo2ImplOpenModifications.class,
		
	}
)
public class TestSuiteBo2ImplOpen {
	/* empty */
}
