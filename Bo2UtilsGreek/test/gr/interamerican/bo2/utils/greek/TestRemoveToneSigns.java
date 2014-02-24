package gr.interamerican.bo2.utils.greek;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for RemoveToneSigns.
 */
public class TestRemoveToneSigns {
	
	/**
	 * test containsNonGreekLetters
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveToneSigns() {
		RemoveToneSigns instance = RemoveToneSigns.getInstance();
		
		String[] inputs = 
			{"’ντε στην υγειά μας", "Ησαΐα", "’", "Like Έεε,’αα,Όοο,Ώωω" };
		String[] expecteds = 
			{"Αντε στην υγεια μας", "Ησαϊα", "Α", "Like Εεε,Ααα,Οοο,Ωωω" };
		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			String actual = instance.removeToneSigns(inputs[i]);
			assertEquals(expecteds[i],actual);
		}
	}
	
	/**
	 * test containsNonGreekLetters
	 */	
	@Test
	public void testContainsNonGreekLetters() {
		RemoveToneSigns instance = RemoveToneSigns.getInstance();		
		Assert.assertEquals('α', instance.removeToneSign('ά'));
	}

}
