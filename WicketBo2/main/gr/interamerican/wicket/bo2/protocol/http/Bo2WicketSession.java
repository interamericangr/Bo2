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

import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.wicket.bo2.markup.html.form.DropDownChoiceForEntry;
import gr.interamerican.wicket.bo2.markup.html.form.DropDownChoiceForEntryOwner;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

/**
 * Common base class for Bo2 wicket application sessions.
 * 
 * @param <A> Type of authorization identifier.
 * @param <L> Type of language id.
 */
public class Bo2WicketSession<A, L>  
extends WebSession 
implements Session<A, L> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	public static Bo2WicketSession get() {
		return (Bo2WicketSession) org.apache.wicket.Session.get();
	}

	/**
	 * User.
	 */
	User<A> user;
	
	/**
	 * Language id.
	 */
	L languageId;

	/**
	 * Creates a new Bo2WicketSession object. 
	 *
	 * @param request
	 * @param defaultLanguageId Default language id.
	 */
	public Bo2WicketSession(Request request, L defaultLanguageId) {
		super(request);
		this.languageId = defaultLanguageId;
	}
	
	/**
	 * Creates a new Bo2WicketSession object. 
	 *
	 * @param request
	 */
	public Bo2WicketSession(Request request) {
		super(request);
	}

	public User<A> getUser() {
		return user;
	}

	/**
	 * Sets the current user.
	 * 
	 * @param user
	 */
	public void setUser(User<A> user) {
		this.user = user;
	}
	
	public L getLanguageId() {		
		return languageId;
	}
	
	public void setLanguageId(L languageId) {
		this.languageId = languageId;
	}
	
	public boolean isAuthorized(A authorizationId) {
		if (user!=null) {
			return user.isAuthorized(authorizationId);
		}
		return false;
	}
	
	public boolean isLogin() {
		return this.user!=null;
	}
	
	/**
	 * Creates a new drop down choice for a collection of {@link TranslatableEntry}
	 * objects.<br/>
	 * 
	 * @param <T> Type of {@link TranslatableEntry} objects.
	 * @param id Drop down choice id.
	 * @param choices List of choices.
	 * 
	 * @return Returns a new drop down choice.
	 */
	public <T extends TranslatableEntry<?, ?, L>> DropDownChoice<T> 
	entriesDropDownChoice(String id, List<? extends T> choices) {
		return new DropDownChoiceForEntry<L, T>(id, choices, this);
	}
	
	/**
	 * Creates a new drop down choice for a collection of {@link TranslatableEntry}
	 * objects.<br/>
	 * 
	 * @param <T> Type of {@link TranslatableEntry} objects.
	 * @param id Drop down choice id.
	 * @param model Model.
	 * @param choices List of choices.
	 * 
	 * @return Returns a new drop down choice.
	 */
	public <T extends TranslatableEntry<?, ?, L>> DropDownChoice<T> 
	entriesDropDownChoice(String id, IModel<T> model, List<? extends T> choices) {
		return new DropDownChoiceForEntry<L, T>(id, model, choices, this);
	}
	
	/**
	 * Creates a new drop down choice for a collection of {@link TranslatableEntryOwner}
	 * objects.<br/>
	 *
	 * @param id Drop down choice id.
	 * @param choices List of choices.
	 * @param <T> Type of {@link TranslatableEntry} objects. 
	 * 
	 * @return Returns a new drop down choice.
	 */
	public <T extends TranslatableEntryOwner<?, ?, L>> DropDownChoice<T> 
	entryOwnersDropDownChoice(String id, List<? extends T> choices) {
		return new DropDownChoiceForEntryOwner<L, T>(id, choices, this);
	}
	
	/**
	 * Creates a new drop down choice for a collection of {@link TranslatableEntryOwner}
	 * objects.<br/>
	 *
	 * @param id Drop down choice id.
	 * @param model Model.
	 * @param choices List of choices.
	 * @param <T> Type of {@link TranslatableEntry} objects. 
	 * 
	 * @return Returns a new drop down choice.
	 */
	public <T extends TranslatableEntryOwner<?, ?, L>> DropDownChoice<T> 
	entryOwnersDropDownChoice(String id, IModel<T> model, List<? extends T> choices) {
		return new DropDownChoiceForEntryOwner<L, T>(id, model, choices, this);
	}

}
