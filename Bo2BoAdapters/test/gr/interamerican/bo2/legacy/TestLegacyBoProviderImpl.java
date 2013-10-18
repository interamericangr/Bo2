package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.arch.utils.beans.SessionImpl;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link LegacyBoProviderImpl}.
 */
public class TestLegacyBoProviderImpl {

	/**
	 * tests getStreams()
	 * 
	 * TODO: geStreams() has not been properly implemented.
	 */
	@Test(expected=DataOperationNotSupportedException.class)
	public void testGetStreams() {
		LegacyBoProviderImpl prov = new LegacyBoProviderImpl();
		prov.getStreams();
	}

	/**
	 * tests getNotSupported().
	 */
	@Test()
	public void testNotSupported() {
		LegacyBoProviderImpl prov = new LegacyBoProviderImpl();
		DataOperationNotSupportedException ex = prov.notSupported(Object.class);
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}

	/**
	 * tests the creation.
	 * 
	 * 
	 */
	@Test()
	public void testCreation() {
		String userID = "mock"; //$NON-NLS-1$

		SessionImpl<String, String> session = new SessionImpl<String, String>();
		@SuppressWarnings("unchecked")
		User<String> user = Mockito.mock(User.class);
		Mockito.when(user.getUserId()).thenReturn(userID);
		session.setUser(user);
		Bo2Session.setSession(session);

		LegacyBoProviderImpl prov = new LegacyBoProviderImpl();
		Assert.assertEquals(userID, prov.getCurrentUser());

	}





}
