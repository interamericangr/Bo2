package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * {@link HttpServletResponseWrapper} that facilitates recording of
 * the {@link ServletOutputStream}
 */
public class RecordingServletResponseWrapper extends HttpServletResponseWrapper {

	/**
	 * {@link ServletOutputStream} decorator.
	 */
	RecordingServletOutputStream sos;
	
	/**
	 * Creates a new WsServletRequestWrapper object. 
	 *
	 * @param response
	 * @throws IOException 
	 */
	public RecordingServletResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		this.sos = new RecordingServletOutputStream(response.getOutputStream());
	}
	
	@Override
    public ServletOutputStream getOutputStream() throws IOException {
       return sos;
    }
	
	/**
	 * Gets the recorded contents of the decorated {@link ServletOutputStream}.
	 * 
	 * @return contents of the decorated {@link ServletOutputStream}.
	 */
	public byte[] getPayload() {
		return sos.getPayload();
	}
	
}
