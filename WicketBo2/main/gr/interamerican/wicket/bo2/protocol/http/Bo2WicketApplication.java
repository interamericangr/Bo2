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

import java.util.Collection;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.strategies.page.SimplePageAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.wicket.bo2.SafeWebPage;
import gr.interamerican.wicket.def.WicketOutputMedium;

/**
 * Base class for Wicket applications using Bo2.<br>
 * Extending this class for the {@link Application} of your web project :
 * <ul>
 * <li>enables the use of the {@link Bo2WicketRequestCycle} - as well the
 * automated transaction management be bo2
 * <li>the automatic displaying of errors on pages that implement the
 * {@link WicketOutputMedium}
 * <li>a default {@link IAuthorizationStrategy} as described bellow
 * </ul>
 * In order to view a page one of these conditions has to be met :
 * <ul>
 * <li>The page to view must be the one returned by the
 * {@link #getHomePage()}</li>
 * <li>The method {@link Bo2WicketSession#isLogin()} of the current session must
 * return true</li>
 * <li>The page you wish to view must be marked with the {@link SafeWebPage}
 * interface</li>
 * </ul>
 * 
 * @param <A>
 *            Type of authorization identifier.
 * @param <L>
 *            Type of language id.
 */
public abstract class Bo2WicketApplication<A, L extends Comparable<? super L>> extends WebApplication {

	@Override
	public Session newSession(Request request, Response response) {
		return new Bo2WicketSession<A, L>(request);
	}

	/**
	 * Returns if a Page is safe to display without authorization.<br>
	 * This implementation considers safe the Home Page by default (which should
	 * be a login page)or anything that implements the {@link SafeWebPage}.
	 * 
	 * @param pageClass
	 *            Class of Page
	 * @return If it is safe
	 */
	protected boolean isSafePage(Class<? extends Page> pageClass) {
		return getHomePage().equals(pageClass) || SafeWebPage.class.isAssignableFrom(pageClass);
	}

	@Override
	protected void init() {
		super.init();
		getRequestCycleListeners().add(new PageRequestHandlerTracker());
		getRequestCycleListeners().add(new Bo2RequestCycleListener());

		SimplePageAuthorizationStrategy authorizationStrategy = new SimplePageAuthorizationStrategy(WebPage.class,
				getHomePage()) {
			@Override
			protected <T extends Page> boolean isPageAuthorized(Class<T> pageClass) {
				return super.isPageAuthorized(pageClass) || isSafePage(pageClass);
			}

			@Override
			protected boolean isAuthorized() {
				return Bo2WicketSession.get().isLogin();
			}
		};
		getSecuritySettings().setAuthorizationStrategy(authorizationStrategy);
	}

	/**
	 * Default language ids.
	 * 
	 * The login screen, should be drawn according to this language id.
	 * 
	 * @return Returns the id of the default language.
	 */
	public abstract L defaultLanguageId();

	/**
	 * Collection with the languages supported by the system.
	 * 
	 * @return Returns the available languages.
	 */
	public abstract Collection<TranslatableEntry<L, ?, L>> supportedLanguages();
}