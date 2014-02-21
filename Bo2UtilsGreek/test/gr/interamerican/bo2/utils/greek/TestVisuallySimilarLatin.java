package gr.interamerican.bo2.utils.greek;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.utils.greek.VisuallySimilarLatin;

/**
 * Tests for {@link VisuallySimilarLatin}.
 */
public class TestVisuallySimilarLatin {
	
	/**
	 * tests the constructor.
	 */
	@Test
	public void testConstructor() {
		VisuallySimilarLatin obj = new VisuallySimilarLatin();
		Assert.assertTrue(obj.associations.size()>0);		
	}
	
	/**
	 * tests getGreek(c).
	 */
	@Test
	public void testGetGreek() {
		VisuallySimilarLatin obj = new VisuallySimilarLatin();		
		Character latin = Character.toUpperCase('a');
		Character greek = Character.toUpperCase('á');
		Character actual = obj.getGreek(latin);
		Assert.assertEquals(greek, actual);
		Assert.assertNull(obj.getGreek('f')); 		
	}
	
	/**
	 * tests getlatin(c).
	 */
	@Test
	public void testGetLatin() {
		VisuallySimilarLatin obj = new VisuallySimilarLatin();		
		Character latin = Character.toUpperCase('a');
		Character greek = Character.toUpperCase('á');
		Character actual = obj.getLatin(greek);
		Assert.assertEquals(latin, actual);
		Assert.assertNull(obj.getLatin('â')); 		
	}
	
	


}
