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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ftp Client bean.
 * 
 * 
 */
public class FtpSession {
	/**
	 * Could not connect message.
	 */
	private static final String COULD_NOT_CONNECT = 
		"Could not connect to FTP server."; //$NON-NLS-1$
	
	/**
	 * Could not connect message.
	 */
	private static final String LOGIN_FAILED = 
		"Login to FTP server failed."; //$NON-NLS-1$
	
	/**
	 * Could not connect message.
	 */
	private static final String NOT_CONNECTED = 
		"Not connected to FTP server."; //$NON-NLS-1$
	
	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FtpSession.class);

    /**
     * Host name.
     */
	String host;
    /**
     * User id.
     */
	String userid;
	/**
	 * Password.
	 */
    String password;
    /**
     */
    int port;
    /**
     * FTPClient.
     */
    FTPClient ftp;

    /**
     * Creates a new FtpBean.
     * 
     * @param host
     * @param userid
     * @param password
     * @param port
     */
    public FtpSession(String host, String userid, String password, int port) {
        super();
        this.host = host;
        this.userid = userid;
        this.password = password;
        this.port = port;        
    }
    
    /**
     * Creates a new FTPClient.
     * 
     * @return Returns a new FTPClient.
     */
    FTPClient newFtpClient() {
    	return new FTPClient();
    }
    
    /**
     * Connects.
     * 
     * @throws FtpException
     */
    public void connect() throws FtpException {
    	try {
			ftp = newFtpClient();
			ftp.connect(host, port);
			int reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				disconnectOnFailure();
			    throw new FtpException(COULD_NOT_CONNECT);
			}
			boolean loggedIn = ftp.login(userid, password);
			if (!loggedIn) {
				disconnectOnFailure();
				throw new FtpException(LOGIN_FAILED);				
			}
		} catch (SocketException se) {			
			disconnectOnFailure();		
			throw new FtpException(COULD_NOT_CONNECT, se);
		} catch (IOException ioe) {
			disconnectOnFailure();	
			throw new FtpException(ioe);
		}
	}

	/**
	 * changes the remote directory to the given one.
	 * 
	 * @param path
	 * @throws FtpException
	 */
	public void changeRemoteDir(String path) throws FtpException {
		try {
			ftp.changeWorkingDirectory(path);
		} catch (IOException ioe) {
			disconnectOnFailure();
			throw new FtpException(ioe);
		}

    }
    
    /**
     * Disconnects.
     * 
     * @throws FtpException 
     */
    public void disconnect() throws FtpException {
    	if (ftp==null) {
    		throw new FtpException(NOT_CONNECTED);
    	}
    	try {			
    		ftp.logout();
			ftp.disconnect();			
		} catch (IOException e) {
			throw new FtpException(e);
		} finally {
			ftp=null;
		}
    }
    
    /**
     * Disconnects. 
     */
    private void disconnectOnFailure() {    	
		try {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		} catch (IOException e) {
			/*
			 * This exception is not re thrown, because
			 * this method is used only by exception handling
			 * blocks. 
			 */
			logger.error(e.toString());
		} finally {
			ftp=null;
		}
    }

   

    
    /**
     * Download a remore file to a local OutputStream.
     * 
     * @param remote
     * @param local
     * @param isBinaryFile
     * @throws FtpException
     */
    public void download(String remote, OutputStream local, boolean isBinaryFile) 
    throws FtpException {
    	if (ftp==null) {
    		throw new FtpException(NOT_CONNECTED);
    	}
        try {
			if (isBinaryFile) {
				ftp.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
			}
			boolean ok = ftp.retrieveFile(remote, local);
			if (!ok) {
				String msg = ftp.getReplyString();
				throw new FtpException(msg);
			}
			local.close();
		} catch (FileNotFoundException fnfe) {
			throw new FtpException(fnfe);
		} catch (IOException ioe) {
			throw new FtpException(ioe);
		}       
    }
    
    /**
	 * upload a local file to the ftp.
	 * 
	 * @param remote
	 * @param local
	 * @param isBinaryFile
	 * @throws FtpException
	 */
    public void upload(String remote, InputStream local, boolean isBinaryFile) 
    throws FtpException {
    	if (ftp==null) {
    		throw new FtpException(NOT_CONNECTED);
    	}
        try {
			if (isBinaryFile) {
				ftp.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
			}
			boolean ok = ftp.storeFile(remote, local);
			if (!ok) {
				String msg = ftp.getReplyString();
				throw new FtpException(msg);
			}
			local.close();
		} catch (FileNotFoundException fnfe) {
			throw new FtpException(fnfe);
		} catch (IOException ioe) {
			throw new FtpException(ioe);
		}
    }
   
}

