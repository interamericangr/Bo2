package gr.interamerican.bo2.impl.open.jee.servlet;

import java.io.IOException;

import javax.servlet.ServletInputStream;

/**
 * {@link ServletInputStream} decorator.
 * <br/><br/>
 * The following methods are not overridden
 * <ul>
 * 		<li>{@link ServletInputStream}{@link #read(byte[])}
 * 		<li>{@link ServletInputStream}{@link #read(byte[], int, int)}
 * 		<li>{@link ServletInputStream}{@link #readLine(byte[], int, int)}
 * </ul>
 * since they delegate to
 * 
 * {@link ServletInputStream}{@link #read()}
 * <br/><br/>
 * Known limitations: Non ASCII characters are not recorded properly. 
 */
public class RecordingServletInputStream extends ServletInputStream {
	
	/**
	 * Stream is recorded here.
	 */
	private StringBuilder sb = new StringBuilder();
	
	/**
	 * Decorated instance
	 */
	private ServletInputStream sis;
	
	/**
	 * Creates a new WsLoggingServletInputStream object. 
	 * @param sis 
	 */
	public RecordingServletInputStream(ServletInputStream sis) {
		this.sis = sis;
	}
	
	@Override
	public int read() throws IOException {
		int ch = sis.read();
        if (ch != -1) {
        	sb.append((char)ch);
        }
        return ch;
	}
	
	@Override
	public int available() throws IOException {
		return sis.available();
	}
	
	@Override
	public synchronized void mark(int readlimit) {
		sis.mark(readlimit);
	}
	
	@Override
	public long skip(long n) throws IOException {
		return sis.skip(n);
	}
	
	@Override
	public void close() throws IOException {
		sis.close();
	}
	
	@Override
	public synchronized void reset() throws IOException {
		sis.reset();
	}
	
	@Override
	public boolean markSupported() {
		return sis.markSupported();
	}
	
	/**
	 * Gets the recorded contents of the decorated {@link ServletInputStream}.
	 * 
	 * @return contents of the decorated {@link ServletInputStream}.
	 */
	public String getPayload() {
		return sb.toString();
	}
	
}
