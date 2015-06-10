/**
 *
 */
package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.File;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 *
 */
public class TestDynamicPrintStreamNamedStreamProviderImpl {

	/**
	 * Test method for
	 * {@link DynamicPrintStreamNamedStreamProviderImpl#getDefinition(java.lang.String)} .
	 *
	 * @throws InitializationException
	 */
	@Test
	public void testGetDefinition() throws InitializationException {
		String WORKDIR = "workDir"; //$NON-NLS-1$
		Properties p=new Properties();
		p.setProperty(WORKDIR, WORKDIR);
		DynamicPrintStreamNamedStreamProviderImpl impl = new DynamicPrintStreamNamedStreamProviderImpl(
				p);
		NamedStreamDefinition nsp = impl.getDefinition(StringConstants.ONE);
		Assert.assertEquals(StringConstants.ONE, nsp.getName());
		Assert.assertEquals(WORKDIR + File.pathSeparator + StringConstants.ONE, nsp.getUri());
		Assert.assertEquals(StreamType.PRINTSTREAM, nsp.getType());
		Assert.assertEquals(StreamResource.FILE, nsp.getResourceType());
	}

	/**
	 * Test method for
	 * {@link DynamicPrintStreamNamedStreamProviderImpl#getDefinition(java.lang.String)} .
	 *
	 * @throws InitializationException
	 */
	@Test(expected = InitializationException.class)
	public void testGetDefinitionWorkDirException() throws InitializationException {
		DynamicPrintStreamNamedStreamProviderImpl impl = new DynamicPrintStreamNamedStreamProviderImpl(
				new Properties());
		impl.getDefinition(StringConstants.ONE);
	}
}
