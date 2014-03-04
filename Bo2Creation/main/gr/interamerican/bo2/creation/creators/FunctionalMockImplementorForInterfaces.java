package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * {@link ImplementorForInterfaces} that supports all types of
 * interfaces by adding a mock method implementation for any method
 * that is not a property accessor.
 */
public class FunctionalMockImplementorForInterfaces 
extends ImplementorForInterfaces {
	
	@Override
	protected String getSuffix() {
		return "_FmInterfaceImplementationBybo2"; //$NON-NLS-1$
	}
	
	@Override
	protected void supportRemainingMethods() {
		Set<Method> notImplemented = getMethodsNotYetImplemented();
		for (Method method : notImplemented) {
			doSupportMethodWithMock(method);
		}
	}
	
	@Override
	protected void validatePossibleImplementation() throws ClassCreationException {
		if (!analysis.getClazz().isInterface()) {
			throw CodeGenerationUtilities.typeNotSupported(analysis.getClazz());
		}				
		if (analysis.isContainsOddProperties()) {
			throw cantSupportOddProperties();
		}
	}

}
