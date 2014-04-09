package gr.interamerican.bo2.utils.inspect;

import gr.interamerican.bo2.samples.Child;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link FieldInspector}
 */
public class TestFieldInspector {
	
	/**
	 * test inspect()
	 */
	@Test
	public void testInspect() {
		PrintStream out = System.out;
		try {
			ByteArrayOutputStream pipeOut = new ByteArrayOutputStream();
			System.setOut(new PrintStream(pipeOut));
			new FieldInspectorImpl().inspect(new Child());
			String output = new String(pipeOut.toByteArray());
			
			Assert.assertFalse(StringUtils.isNullOrBlank(output));
			Assert.assertFalse(output.contains("static")); //$NON-NLS-1$
		} finally {
			System.setOut(out);
		}
		
	}
	
	/**
	 * Sample impl that omits static fields
	 */
	static class FieldInspectorImpl extends FieldInspector {
		
		@Override
		protected boolean shouldReportFieldValue(Object fieldValue) {
			return true;
		}
		
		@Override
		protected boolean shouldInspectField(Field field) {
			return !Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive();
		}
	}

}
