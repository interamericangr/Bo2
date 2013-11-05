package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * {@link HttpServletRequestWrapper} that facilitates recording of
 * the {@link ServletInputStream}
 */
public class RecordingServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * {@link ServletInputStream} decorator.
	 */
	RecordingServletInputStream sis;
	
	/**
	 * Creates a new WsServletRequestWrapper object. 
	 *
	 * @param request
	 * @throws IOException 
	 */
	public RecordingServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.sis = new RecordingServletInputStream(request.getInputStream());
	}
	
	@Override
    public ServletInputStream getInputStream() throws IOException {
       return sis;
    }
	
	/**
	 * Gets the recorded contents of the decorated {@link ServletInputStream}.
	 * 
	 * @return contents of the decorated {@link ServletInputStream}.
	 */
	public byte[] getPayload() {
		return sis.getPayload();
	}
	
}
