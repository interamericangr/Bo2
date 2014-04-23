package gr.interamerican.bo2.utils.greek;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link GreekLetters}.
 */
public class TestGreekLetters {
	
	/**
	 * Tests getVowelsSet().
	 */
	@Test
	public void testGetVowels() {
		GreekLetters gl = GreekLetters.getInstance();
		Set<Character> set = gl.getVowelsSet();
		for (Character character : set) {
			System.out.println(character);
		}
		Assert.assertEquals(34, set.size());		
	}
	
	/**
	 * Tests getSoundfulConsonantsSet().
	 */
	@Test
	public void testGetSoundfulConsonantsSet() {
		GreekLetters gl = GreekLetters.getInstance();
		Set<Character> set = gl.getSoundfulConsonantsSet();
		Assert.assertEquals(18, set.size());		
	}
	
	/**
	 * Tests getSoundlessConsonantsSet().
	 */
	@Test
	public void testGetSoundlessConsonantsSet() {
		GreekLetters gl = GreekLetters.getInstance();
		Set<Character> set = gl.getSoundlessConsonantsSet();
		Assert.assertEquals(17, set.size());		
	}
	
	/**
	 * Tests getSoundlessConsonantsSet().
	 */
	@Test
	public void testGetConsonantsSet() {
		GreekLetters gl = GreekLetters.getInstance();
		Set<Character> set = gl.getConsonantsSet();
		Assert.assertEquals(35, set.size());		
	}

}
