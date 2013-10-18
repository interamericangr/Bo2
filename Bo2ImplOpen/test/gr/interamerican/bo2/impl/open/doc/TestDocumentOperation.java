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
package gr.interamerican.bo2.impl.open.doc;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.bean.Samples;
import gr.interamerican.bo2.samples.bean.TripInfo;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Range;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestDocumentOperation {
	
	/**
	 * Unit test for init.
	 * 
	 * @throws InitializationException
	 */
	@Test
	public void testInit() throws InitializationException {
		DocOp op = new DocOp();
		op.init(Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider());
		Assert.assertNotNull(op.documentEngine);		
	}
	
	/**
	 * Unit test for execute.
	 * 
	 * @throws InitializationException
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test
	public void testExecute() throws InitializationException, DataException, LogicException {
		DocOp op = new DocOp();
		op.init(Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider());
		op.open();
		op.execute();
		op.close();
	}
	
	
	/**
	 * Sample document operation.
	 */
	@ManagerName("ODF")
	@SuppressWarnings("nls")
	private class DocOp extends DocumentOperation {
		
		
		@Override
		protected void work() 
		throws DataException, LogicException, DocumentEngineException {
			
			Date from;
			Date to;
			try {
				from = DateUtils.getDate("31/01/2012");
				to = DateUtils.getDate("30/03/2012");
			} catch (ParseException e) {
				throw new DataException(e);
			}
			
			String workDir = UtilityForBo2Test.getOdfWorkDirectory();			
			BusinessDocument doc = documentEngine.newDocument();

			String text = "Summary\nThis document contains a proposal letter to the Astair Family. It also contains some terms.";
			doc.addParagraph(text);
			doc.pageBreak();
			/*
			 * Now create a letter and append it.
			 */ 
			BusinessDocument letterTemplate = 
				documentEngine.openDocument(workDir+"LetterTemplateToFamily.odt");
			TripInfo info = new TripInfo();

			Range<Date> period = new Range<Date>(from, to);
			info.setPeriod(period);
			info.setFamily(Samples.theAstaireFamily());
			info.setDestination("New York");
			letterTemplate.setFields(info);
			
			doc.append(letterTemplate);
			doc.pageBreak();
			/*
			 * Now append a small text.
			 */
			BusinessDocument smallText = 
				documentEngine.openDocument(workDir+"FixedSmallText.odt");
			doc.append(smallText);
			doc.append(smallText);
			doc.append(smallText);
			doc.pageBreak();			
			String lastPara = StringUtils.concat(
					"This is the last page.\n",
					"This page closes the document\n");
			doc.addParagraph(lastPara);
			/*
			 * Now save the document.
			 */
			String path = workDir+"BusinessDocument.odt";
			documentEngine.saveDocument(doc, path);
			
			
		}		
	}

}
