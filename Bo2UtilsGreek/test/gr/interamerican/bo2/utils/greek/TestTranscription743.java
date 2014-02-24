package gr.interamerican.bo2.utils.greek;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestTranscription743 {
	
	/**
	 * test transcriptWord()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTranscriptWord() {
		Transcription743 instance = Transcription743.getInstance();
		
		String[] inputs = 
			{"Έλα", "Εύη", "ΕΥΑΝΘΙΑ", "Αυξέντιος", "Λουίζα", "Μπέκιος", "Μπαρτζώκας", 
 			 "Μπαμπάκας", "Αρχιμήδης", "Παλίμψηστος", "Χανιά", "ΧΙΟΣ", "Ησαύ" };
		String[] expecteds = 
			{"Ela", "Evi", "EVANTHIA", "Afxentios", "Louiza", "Bekios", "Bartzokas", 
			 "Bampakas", "Archimidis", "Palimpsistos", "Chania", "CHIOS", "Isaf" };
		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			String actual = instance.transcriptWord(inputs[i]);
			assertEquals(expecteds[i],actual);
		}
	}
	
	/**
	 * test transcriptWord()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTranscript() {
		Transcription743 instance = Transcription743.getInstance();
		
		String[] inputs = 
			{"Η Εύη Μπεμπέκου πήγε στη ΔΕΗ (Δημόσια Επιχείριση Ηλεκτρισμού)",
			 "Ο παπάς ο παχύς έφαγε παχιά φακή"};
		String[] expecteds = 
			{"I Evi Bempekou pige sti DEI (Dimosia Epicheirisi Ilektrismou)",
			 "O papas o pachys efage pachia faki"};

		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			String actual = instance.transcript(inputs[i]);
			assertEquals(expecteds[i],actual);
		}
	}

}
