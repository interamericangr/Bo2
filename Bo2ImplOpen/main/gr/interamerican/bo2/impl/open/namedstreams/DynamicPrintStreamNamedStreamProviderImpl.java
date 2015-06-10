package gr.interamerican.bo2.impl.open.namedstreams;

import static gr.interamerican.bo2.impl.open.namedstreams.InvalidNsdUtility.invalid;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.File;
import java.io.PrintStream;
import java.util.Properties;

/**
 * extension to {@link NamedStreamsManagerImpl} to support ad-hoc naming of {@link PrintStream}s on
 * the workDir.
 */
public class DynamicPrintStreamNamedStreamProviderImpl extends NamedStreamsManagerImpl {


	/**
	 * default constructor
	 *
	 * @param properties
	 */
	public DynamicPrintStreamNamedStreamProviderImpl(Properties properties) {
		super(properties);
	}

	@Override
	NamedStreamDefinition getDefinition(String name) throws InitializationException {
		String definition = properties.getProperty(name);
		if (definition!=null){
			return super.getDefinition(name);
		}
		String workDir = properties.getProperty("workDir"); //$NON-NLS-1$
		if (StringUtils.isNullOrBlank(workDir)) {
			throw invalid("Work dir not set", name); //$NON-NLS-1$
		}
		String uri=StringConstants.EMPTY;
		uri = workDir + File.pathSeparator + name;
		NamedStreamDefinition nsd = createNameStreamDefinitionWithMandatoryOptions(name, uri,
				StreamType.PRINTSTREAM, StreamResource.FILE);
		return nsd;
	}
}
