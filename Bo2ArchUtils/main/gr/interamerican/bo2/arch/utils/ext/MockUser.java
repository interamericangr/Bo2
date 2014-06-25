package gr.interamerican.bo2.arch.utils.ext;

import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

/**
 * Mock user.<br>
 * Mock implementation of {@link User}
 * 
 * @param <A>
 * 
 */
public class MockUser<A>
implements User<A>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new MockUser object.
	 *
	 * @param authorized
	 */
	public MockUser(boolean authorized) {
		super();
		this.authorized = authorized;
	}

	/**
	 * Creates a new MockUser object.
	 *
	 * @param authorized
	 * @param authToken
	 */
	public MockUser(boolean authorized, String authToken) {
		super();
		this.authorized = authorized;
		this.authToken = authToken;
	}

	/**
	 * Assigns a new value to the userId.
	 *
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Assigns a new value to the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * User id.
	 */
	String userId;
	/**
	 * Password.
	 */
	String password;
	/**
	 * indication if the user is authorized.
	 */
	boolean authorized;

	/**
	 * used for checking user authorization regarding amounts.
	 */
	String authToken;

	@Override
	public boolean isAuthorized(A authorizationId) {
		return authorized;
	}

	/**
	 * include pml authorization.
	 * 
	 * @deprecated TODO Will just return authToken as soon OnE goes live for motor
	 */
	@SuppressWarnings("nls")
	@Override
	@Deprecated
	public String getAuthorizationToken(A authorizationId) {
		String[] auth = TokenUtils.splitTrim((String) authorizationId, StringConstants.MINUS);
		if (((String) authorizationId).length() < 3) {
			String msg = "Invalid authorization id: " + authorizationId; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		String branchCd = auth[1];
		String authCd = auth[2];
		if (authCd.equals("419")) {
			if (branchCd.equals("02")) {
				authToken = "69";
			} else if (branchCd.equals("03")) {
				authToken = "91";
			}
		}
		return authToken;
	}


	@Override
	public String authorizationDescription(A authorizationId) {
		return StringUtils.toString(authorizationId);
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getUserName() {
		return "Mock " + userId; //$NON-NLS-1$
	}

	@Override
	public String getUserPassword() {
		return password;
	}

	@Override
	public String getEmailAddress() {
		return "mock@email.com"; //$NON-NLS-1$
	}

}
