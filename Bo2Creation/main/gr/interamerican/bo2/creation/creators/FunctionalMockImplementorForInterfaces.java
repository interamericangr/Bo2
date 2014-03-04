package gr.interamerican.bo2.creation.creators;

import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.generateMethodDeclarationParameters;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gr.interamerican.bo2.creation.code.templatebean.EmptyMethodCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.MethodCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;

/**
 * {@link ImplementorForInterfaces} that supports all types of
 * interfaces by adding a mock method implementation for any method
 * that is not a property accessor.
 */
public class FunctionalMockImplementorForInterfaces 
extends ImplementorForInterfaces {
	
	/**
	 * Templates.
	 */
	protected EmptyMethodCodeTemplates emptyMethodTemplates = new EmptyMethodCodeTemplates();
	
	/**
	 * Set with the properties being marked as mocks.
	 */
	Set<String> mockMethods = new HashSet<String>();
	
	@Override
	protected String getSuffix() {
		return "_FmInterfaceImplementationBybo2"; //$NON-NLS-1$
	}
	
	/**
	 * Try to support a not implemented method by creating a mock
	 * implementation.
	 * 
	 * @param method
	 *        Method to implement with mock.
	 *        
	 * @return Returns true if the method is implemented, otherwise
	 *         returns false.
	 */
	protected boolean supportMethodWithMock(Method method) {
		MethodCodeTemplates templates = emptyMethodTemplates;						
		Class<?>[] args = method.getParameterTypes();
		String declarationParams=generateMethodDeclarationParameters(args);			
		Map<String, String> vars = Variables.variablesForEmptyMethod (
			method.getName(), method.getReturnType(), declarationParams);
		String code = templates.getMethod(vars, method.getReturnType());
		implementMethod(method, code);
		return true;
	}
	
	
	@Override
	protected void supportType() throws ClassCreationException {
		addSerialVersionUid();
		supportProperties();
		//support methods
		addBasicUpdaters();	
	}
	
	/**
	 * Adds support for the methods.
	 */
	protected void supportMethods() {
		Set<Method> abstractMethods = analysis.getAbstractMethods();
		for (Method method : abstractMethods) {
			supportMethodWithMock(method);
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
