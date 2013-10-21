package gr.interamerican.bo2.utils.beans;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MessagesBean}.
 */
public class TestMessagesBean {
	
	/**
	 * Path to resource bundle file.
	 */
	private static final String PATH = "gr.interamerican.bo2.utils.beans.MessagesBean"; //$NON-NLS-1$
	
	/**
	 * Key of message 1.
	 */
	private static final String MSG1 = "message1"; //$NON-NLS-1$
	
	/**
	 * Key of message 2.
	 */
	private static final String MSG2 = "message2"; //$NON-NLS-1$

	
	/**
	 * Tests getString(msg)
	 */
	@Test
	public void testGetString() {
		MessagesBean bean = new MessagesBean(PATH);
		String msg = bean.getString(MSG1);
		Assert.assertNotNull(msg);		
	}
	
	/**
	 * Tests getString(msg) when key is not found.
	 */
	@Test
	public void testGetString_missingKey() {
		MessagesBean bean = new MessagesBean(PATH);
		String key = "nokey"; //$NON-NLS-1$
		String expected = "!"+key+"!";  //$NON-NLS-1$//$NON-NLS-2$
		String msg = bean.getString(key);
		Assert.assertEquals(expected, msg);		
	}
	
	/**
	 * Tests getString(msg)
	 */
	@Test
	public void testGetString_withParams() {
		MessagesBean bean = new MessagesBean(PATH);
		String msg = bean.getString(MSG2,"string", 1); //$NON-NLS-1$
		Assert.assertNotNull(msg);
		
	}

}
