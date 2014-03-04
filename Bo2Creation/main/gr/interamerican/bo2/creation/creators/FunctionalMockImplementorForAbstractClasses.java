package gr.interamerican.bo2.creation.creators;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 
 */
public class FunctionalMockImplementorForAbstractClasses 
extends ImplementorForAbstractClasses {
	
	@Override
	protected String getSuffix() {
		return "_FmAbstractClassImplementationBybo2"; //$NON-NLS-1$
	}
	 
	@Override
	protected void supportRemainingMethods() {
		Set<Method> notImplemented = getMethodsNotYetImplemented();
		for (Method method : notImplemented) {
			doSupportMethodWithMock(method);
		}
	}
}
