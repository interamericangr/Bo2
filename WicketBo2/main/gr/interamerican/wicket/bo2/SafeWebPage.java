package gr.interamerican.wicket.bo2;

import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

/**
 * Marker Interface for web pages that can be accessed without the need of log-in.
 * ( {@link Bo2WicketSession#isLogin()}.
 */
public interface SafeWebPage {
	// empty
}