package gr.interamerican.bo2.impl.open.jee.servlet;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 */
public class TestHttpServletUtils {
	
	/**
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetGetParameter() {
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(req.getQueryString()).thenReturn("a=A&b=B");
		String expected = "A";
		String actual = HttpServletUtils.getGetParameter(req, "a");
		Assert.assertEquals(expected, actual);
		
		actual = HttpServletUtils.getGetParameter(req, "c");
		Assert.assertNull(actual);
		
		Mockito.when(req.getQueryString()).thenReturn("a=A&bb=%20");
		expected = " ";
		actual = HttpServletUtils.getGetParameter(req, "bb");
		Assert.assertEquals(expected, actual);
		
		Mockito.when(req.getQueryString()).thenReturn("a=&b=");
		expected = "";
		actual = HttpServletUtils.getGetParameter(req, "a");
		Assert.assertEquals(expected, actual);
		actual = HttpServletUtils.getGetParameter(req, "b");
		Assert.assertEquals(expected, actual);
		
		Mockito.when(req.getQueryString()).thenReturn("");
		actual = HttpServletUtils.getGetParameter(req, "a");
		Assert.assertNull(actual);
	}

}
