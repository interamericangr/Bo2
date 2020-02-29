package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentInfoUtility;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} that returns a summary for the current Bo2 deployment configuration.
 * Can be used for troubleshooting a webapp's configuration.
 */
public class Bo2ConfigurationSummaryServlet extends HttpServlet {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html"); //$NON-NLS-1$
		resp.getWriter().append(getHtml());
	}
	
	/**
	 * Gets the html.
	 *
	 * @return the HTML to send
	 */
	@SuppressWarnings("nls")
	String getHtml() {
		String configurationSummary = Bo2DeploymentInfoUtility.get().getConfigurationSummary();
		return StringUtils.concat(
				"<html><head><title>Bo2 configuration summary</title></head><body>",
				configurationSummary.replace(System.lineSeparator(), "<br>\n"),
				"</body></html>");
	}

}
