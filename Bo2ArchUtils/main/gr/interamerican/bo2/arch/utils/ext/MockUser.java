package gr.interamerican.bo2.arch.utils.ext;

import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.utils.StringUtils;

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

	@Override
	public String getAuthorizationToken(A authorizationId) {
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
