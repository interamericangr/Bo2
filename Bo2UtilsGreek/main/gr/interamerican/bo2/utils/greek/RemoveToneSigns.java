package gr.interamerican.bo2.utils.greek;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.AssociationTable;

/**
 * Removes tone signs from Greek letters.
 */
public class RemoveToneSigns {
	
	/**
	 * Singleton instance.
	 */
	private static final RemoveToneSigns INSTANCE = new RemoveToneSigns();
	
	
	
	/**
	 * Gets an instance of RemoveToneSigns.
	 *
	 * @return Returns the instance
	 */
	public static RemoveToneSigns getInstance() {
		return INSTANCE;
	}

	/**
	 * Mapping between characters with and without tone.
	 */
	private AssociationTable<Character, Character> letters = 
			new AssociationTable<Character, Character>();

	/**
	 * Creates a new RemoveToneSigns object.
	 * 
	 */
	private RemoveToneSigns() {
		super();
		letters.associate('á', 'Ü');
		letters.associate('å', 'Ý');
		letters.associate('é', 'ß');
		letters.associate('ï', 'ü');
		letters.associate('ç', 'Þ');
		letters.associate('õ', 'ý');
		letters.associate('ù', 'þ');
		letters.associate('ú', 'À');
		letters.associate('û', 'à');
		letters.associate('Á', '¢');
		letters.associate('Å', '¸');
		letters.associate('É', 'º');
		letters.associate('Ï', '¼');
		letters.associate('Ç', '¹');
		letters.associate('Õ', '¾');
		letters.associate('Ù', '¿');
	}
	
	/**
	 * Removes the tone sign from the specified character.
	 * 
	 * @param signed
	 *        Character to remove the tone sign.
	 *        
	 * @return If the specified character is a Greek character with 
	 *         a tone sign, then returns the same character without
	 *         the tone sign. otherwise, returns the specified character. 
	 */
	public char removeToneSign(char signed) {
		Character unsigned = letters.getLeft(signed);
		return Utils.notNull(unsigned, signed);
	}
	
	/**
	 * Removes the tone signs from the specified string.
	 * 
	 * @param signed
	 *        String containing tone signs.
	 *        
	 * @return Returns the specified string after having removed all
	 *         tone signs. 
	 */
	public String removeToneSigns(String signed) {
		char[] input = signed.toCharArray();
		char[] output = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			output[i] = removeToneSign(input[i]);
		}
		return new String(output);
	}
	
	
			
	
	

}
