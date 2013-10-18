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
package gr.interamerican.wicket.bo2.protocol.http;

import java.util.ArrayList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

/**
 * Page used to test {@link Bo2WicketSession}.
 */
public class Bo2WicketSessionTestPage extends WebPage{
	 
	
		
	/**
	 * 
	 */
	private FeedbackPanel feedbackPanel = new FeedbackPanel("feedBackPanel"); //$NON-NLS-1$
	
	
	
	

	/**
	 * Creates a new WicketPage object. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Bo2WicketSessionTestPage(){
		add(feedbackPanel);
		
		

		

        
        /*
         * Bo2WicketSession
         */
        Bo2WicketSession bo2WicketSession = new Bo2WicketSession(getRequest(), "el"); //$NON-NLS-1$
        add(bo2WicketSession.entriesDropDownChoice("bo2entriesDrowDown", new ArrayList())); //$NON-NLS-1$
        add(bo2WicketSession.entriesDropDownChoice("bo2entriesDrowDown1", new Model(), new ArrayList())); //$NON-NLS-1$
        add(bo2WicketSession.entryOwnersDropDownChoice("bo2entryOwnerDropDown", new ArrayList())); //$NON-NLS-1$
        add(bo2WicketSession.entryOwnersDropDownChoice("bo2entryOwnerDropDown1",new Model(),new ArrayList())); //$NON-NLS-1$
        
	}


	

	



	
	
	


}
