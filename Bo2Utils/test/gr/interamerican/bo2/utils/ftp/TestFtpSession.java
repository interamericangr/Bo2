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
package gr.interamerican.bo2.utils.ftp;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link FtpSession}.
 */
public class TestFtpSession {
	
	/**
	 * Creates an FtpSesion object for the test.
	 * 
	 * The object mocks FTP, so it does not need an FTP server.
	 * @param succeedOnLogin 
	 *        Specifies if this FTP client mocks its login method by
	 *        imitating a successful or a failed login.
	 * @param succeedOnUploadDownload
	 *        Specifies if this FTP client mocks the upload and download
	 *        operations by imitating successful or failed operations.   
	 * 
	 * @return Returns an FtpSession object that is testable without the
	 *         need for an FTP server.
	 */	
	FtpSession sample(final boolean succeedOnLogin, final boolean succeedOnUploadDownload)  {
		try {
			FTPClient client = mockFtpClient(succeedOnLogin, succeedOnUploadDownload);
			return sample(client);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
	}
	
	/**
	 * Creates an FtpSesion object for the test.
	 * 
	 * The object mocks FTP, so it does not need an FTP server.
	 * @param client 
	 *        Mock FTPClient.
	 * 
	 * @return Returns an FtpSession object that is testable without the
	 *         need for an FTP server.
	 */
	@SuppressWarnings("nls")
	FtpSession sample(final FTPClient client)  {
		FtpSession session = new FtpSession("ftp.host.com", "foo", "foo", 21) {			
			@Override
			FTPClient newFtpClient() {				
				return client;
			}
		};
		return session;
	}
	

	
	/**
	 * Creates a mock FTPClient that does not need an FTP server.
	 * 
	 * @param succeedOnLogin 
	 *        Specifies if this FTP client mocks its login method by
	 *        imitating a successful or a failed login.
	 * @param succedOnUploadDownload
	 *        Specifies if this FTP client mocks the upload and download
	 *        operations by imitating successful or failed operations.   
	 * 
	 * @return Returns an FtpSession object.
	 * @throws IOException 
	 */
	FTPClient mockFtpClient(boolean succeedOnLogin, boolean succedOnUploadDownload) throws IOException {
		FTPClient client = mock(FTPClient.class);
		when(client.login(anyString(), anyString())).thenReturn(succeedOnLogin);
		when(client.getReplyCode()).thenReturn(250);
		when(client.storeFile(anyString(), (InputStream)anyObject())).thenReturn(succedOnUploadDownload);
		when(client.retrieveFile(anyString(), (OutputStream)anyObject())).thenReturn(succedOnUploadDownload);
		when(client.getReplyString()).thenReturn("Mock FTP reply string");	 //$NON-NLS-1$
		return client;
	}
	
	/**
	 * Creates a mock FTPClient that throws a SocketException
	 * on connect().
	 * 
	 * @return Returns an FtpSession object.
	 * @throws IOException 
	 */
	FTPClient socketExMockFtpClient() throws IOException {
		FTPClient client = mock(FTPClient.class);
		when(client.login(anyString(), anyString())).thenReturn(true);
		when(client.getReplyCode()).thenReturn(250);
		when(client.storeFile(anyString(), (InputStream)anyObject())).thenReturn(true);
		when(client.retrieveFile(anyString(), (OutputStream)anyObject())).thenReturn(true);
		when(client.getReplyString()).thenReturn("Mock FTP reply string");	 //$NON-NLS-1$
		doThrow(SocketException.class).when(client).connect("ftp.host.com", 21); //$NON-NLS-1$
		return client;
	}
	
	/**
	 * Work path.
	 * 
	 * @return Returns the work path.
	 */
	@SuppressWarnings("nls")
	String workPath() {
		String resourcePath = "/gr/interamerican/bo2/deployparms/deployment.properties"; 
		Properties properties = CollectionUtils.readProperties(resourcePath);	
		return properties.getProperty("streamsWorkDirectory");
	}
	
	/**
	 * Creates a sample input stream.
	 * 
	 * @return Returns the input stream.
	 * @throws FileNotFoundException
	 */
	InputStream sampleInputstream() throws FileNotFoundException {
		String path = workPath() + "existing.txt"; //$NON-NLS-1$
		return new FileInputStream(path);
	}
	
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor() {
		String host = "ftp.host.com";
		String userid = "foo";
		String password = "password";
		int port = 100;
		FtpSession session = new FtpSession(host, userid, password, port);
		Assert.assertEquals(host, session.host);
		Assert.assertEquals(userid, session.userid);
		Assert.assertEquals(password, session.password);
		Assert.assertEquals(port, session.port);
	}
	
	/**
	 * Tests connect.
	 * 
	 * @throws FtpException 
	 */	
	@Test
	public void testConnect_succeed() 
	throws FtpException {		
		FtpSession session = sample(true,true);
		session.connect();
	}
	
	/**
	 * Tests connect fail with SocketException thrown.
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 * @throws SocketException 
	 */	
	@Test(expected=FtpException.class)
	public void testConnect_failWithSocketException() 
	throws FtpException, SocketException, IOException {
		FTPClient c = socketExMockFtpClient();			
		FtpSession session = sample(c);
		session.connect();
	}
	
	/**
	 * Tests connect fail.
	 * @throws FtpException 
	 */
	@Test(expected=FtpException.class)
	public void testConnect_failOnLogin() throws FtpException {		
		FtpSession session = sample(false, false);
		session.connect();
	}
	
	/**
	 * Tests disconnect.
	 * 
	 * @throws FtpException 
	 */	
	@Test
	public void testDisconnect_succeed() 
	throws FtpException {		
		FtpSession session = sample(true,true);
		session.connect();
		session.disconnect();
	}
	
	/**
	 * Tests upload().
	 * 
	 * @param succeed
	 *            Indicates if the operation will be successful.
	 * @param binary
	 *            Flag if the file is binary.
	 * 
	 * @throws FtpException
	 * @throws IOException
	 */	
	void testUpload(boolean succeed, boolean binary) 
	throws FtpException, IOException {		
		FtpSession session = sample(true,succeed);
		session.connect();
		session.upload("remote.file1", sampleInputstream(), binary); //$NON-NLS-1$
		session.upload("remote.file2", sampleInputstream(), binary); //$NON-NLS-1$
	}
	
	/**
	 * Tests upload().
	 * 
	 * @param succeed
	 *        Indicates if the operation will be successful. 
	 * @param binary 
	 *        Flags if the file is treated as binary.
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@SuppressWarnings("nls")
	void testDownload(boolean succeed, boolean binary) 
	throws FtpException, IOException {		
		FtpSession session = sample(true,succeed);
		session.connect();
		String part1 = succeed ? "ok" : "nok";
		String part2 = binary ? ".bin" : ".txt";
		String filename = workPath() + "ftp." + part1 + part2;
		OutputStream stream = new FileOutputStream(filename);
		session.download("remote.file", stream, binary); 		
	}
	
	/**
	 * Tests upload().
	 * 
	 * @throws FtpException 
	 */
	@SuppressWarnings("nls")
	@Test(expected=FtpException.class)
	public void testUpload_failNotConnected() 
	throws FtpException {	
		FtpSession session = new FtpSession("h", "u", "p", 21);
		session.upload("foo.txt", mock(InputStream.class), true);
	}
	
	/**
	 * Tests upload().
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test
	public void testUpload_textFileSucceed() 
	throws FtpException, IOException {		
		testUpload(true, false);
	}
	
	/**
	 * Tests upload.
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test
	public void testUpload_binaryFileSucceed() 
	throws FtpException, IOException {		
		testUpload(true, true);
	}
	
	/**
	 * Tests upload().
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test(expected=FtpException.class)
	public void testUpload_textFileFail() 
	throws FtpException, IOException {		
		testUpload(false,true);
	}
	
	/**
	 * Tests upload.
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test(expected=FtpException.class)
	public void testUpload_binaryFileFail() 
	throws FtpException, IOException {		
		testUpload(false, true);
	}
	
	/**
	 * Tests upload().
	 * 
	 * @throws FtpException 
	 */
	@SuppressWarnings("nls")
	@Test(expected=FtpException.class)
	public void testDownload_failNotConnected() 
	throws FtpException {	
		FtpSession session = new FtpSession("h", "u", "p", 21);
		session.download("foo.txt", mock(OutputStream.class), true);
	}
	
	
	
	/**
	 * Tests download().
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test
	public void testDownload_textFileSucceed() 
	throws FtpException, IOException {		
		testDownload(true, false);
	}
	
	/**
	 * Tests download.
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test
	public void testDownload_binaryFileSucceed() 
	throws FtpException, IOException {		
		testDownload(true, true);
	}
	
	/**
	 * Tests download().
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test(expected=FtpException.class)
	public void testDownload_textFileFail() 
	throws FtpException, IOException {		
		testDownload(false,true);
	}
	
	/**
	 * Tests download.
	 * 
	 * @throws FtpException 
	 * @throws IOException 
	 */	
	@Test(expected=FtpException.class)
	public void testDownload_binaryFileFail() 
	throws FtpException, IOException {		
		testDownload(false, true);
	}
	
	
	
	
	

}
