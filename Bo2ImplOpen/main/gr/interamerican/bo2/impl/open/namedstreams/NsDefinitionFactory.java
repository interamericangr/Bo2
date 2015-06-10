package gr.interamerican.bo2.impl.open.namedstreams;

import static gr.interamerican.bo2.impl.open.namedstreams.InvalidNsdUtility.invalid;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.ENCODING_PREFIX;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.RECORD_LENGTH_PREFIX;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Factory of {@link NamedStreamDefinition} objects.
 */
public class NsDefinitionFactory {

	/**
	 * Gets the definition of the stream with the specified logical name.
	 * 
	 * @param name
	 *        Stream name.
	 * @param properties 
	 *        Properties containing NamedStream definition descriptions.
	 *        
	 * @return Returns the definition.
	 * 
	 * @throws InitializationException
	 */
	public NamedStreamDefinition create(String name, Properties properties) 
	throws InitializationException {
		String definition = properties.getProperty(name);
		if (definition==null) {
			String problem = "No description"; //$NON-NLS-1$ 
			throw invalid(problem, name);
		}
		String[] attributes = TokenUtils.splitTrim(definition, StringConstants.COMMA);
		if (attributes.length<3) {
			String problem = "Invalid description"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		StreamType type = StringUtils.ignoreCaseValueOf
			(StreamType.values(), attributes[1]);
		if (type==null) {
			String problem = "Unknown type " + attributes[1]; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		StreamResource resourceType = StringUtils.ignoreCaseValueOf
			(StreamResourceEnum.values(), attributes[2]);
		if (resourceType==null) {
			String problem = "Unknown resource type " + attributes[2]; //$NON-NLS-1$
			throw invalid(problem, name);
		}

		NamedStreamDefinition nsd = new NamedStreamDefinition();
		nsd.setName(name);
		String definitionUri = attributes[0];
		nsd.setType(type);
		nsd.setResourceType(resourceType);
		nsd.setRecordLength(0); //initialize with zero
		
		nsd.setUri(definitionUri);		
		
		String optionalAttribute = ArrayUtils.safeGet(attributes, 3);
		handleOptionalDefinitionElement(nsd, optionalAttribute);
		optionalAttribute = ArrayUtils.safeGet(attributes, 4);
		handleOptionalDefinitionElement(nsd, optionalAttribute);
		
		return nsd;
	}
	
	
	
	
	/**
	 * The first three definition attributes are mandatory. There are two more
	 * optional attributes, recordLength and encoding. RecordLength is an integer
	 * encoding is a Charset name starting with the prefix {@value #ENCODING_PREFIX}
	 * <br/>
	 * For example enc:UTF-8 signifies the UTF-8 encoding.
	 * 
	 *  @see Charset
	 * 
	 * @param nsd
	 * @param attribute
	 * 
	 */
	void handleOptionalDefinitionElement(NamedStreamDefinition nsd, String attribute) {
		if(attribute!=null) {		
			if(attribute.trim().startsWith(ENCODING_PREFIX)) {
				String charset = attribute.trim().replace(ENCODING_PREFIX, StringConstants.EMPTY);
				nsd.setEncoding(Charset.forName(charset));
			} else if(attribute.trim().startsWith(RECORD_LENGTH_PREFIX)) {
				String recordLength = attribute.trim().replace(RECORD_LENGTH_PREFIX, StringConstants.EMPTY);
				int iLen = NumberUtils.string2Int(recordLength);
				nsd.setRecordLength(iLen);
			}
		}
	}

	

}
