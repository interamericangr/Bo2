package gr.interamerican.bo2.utils.greek;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link VisuallySimilarLatin}.
 */
public class TestVisuallySimilarLatin {
	
	/**
	 * tests getGreek(c).
	 */
	@Test
	public void testGetGreek() {
		VisuallySimilarLatin obj = VisuallySimilarLatin.getInstance();	
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
		VisuallySimilarLatin obj = VisuallySimilarLatin.getInstance();		
		Character latin = Character.toUpperCase('a');
		Character greek = Character.toUpperCase('á');
		Character actual = obj.getLatin(greek);
		Assert.assertEquals(latin, actual);
		Assert.assertNull(obj.getLatin('â')); 		
	}
	
	


}
