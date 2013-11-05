package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

/**
 * {@link ServletOutputStream} decorator.
 * <br/><br/>
 * The following methods are not overridden
 * <ul>
 * 		<li>{@link ServletOutputStream}{@link #write(byte[])}
 * 		<li>{@link ServletOutputStream}{@link #write(byte[], int, int)}
 * </ul>
 * since they delegate to
 * 
 * {@link ServletInputStream}{@link #write(int)}
 * 
 */
public class RecordingServletOutputStream extends ServletOutputStream {
	
	/**
	 * Stream is recorded here.
	 */
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	/**
	 * Decorated instance
	 */
	private ServletOutputStream sos;
	
	/**
	 * Creates a new WsLoggingServletInputStream object. 
	 * @param sos 
	 */
	public RecordingServletOutputStream(ServletOutputStream sos) {
		this.sos = sos;
	}
	
	@Override
	public void write(int b) throws IOException {
		baos.write(b); //one byte
		sos.write(b);
	}
	
	@Override
	public void flush() throws IOException {
		sos.flush();
	}
	
	@Override
	public void close() throws IOException {
		sos.close();
	}
	
	/**
	 * Gets the recorded contents of the decorated {@link ServletOutputStream}.
	 * 
	 * @return contents of the decorated {@link ServletOutputStream}.
	 */
	public byte[] getPayload() {
		return baos.toByteArray();
	}

}
