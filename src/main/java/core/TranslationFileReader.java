package core;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import exception.CastleException;
import exception.MalformedTranslationFile;

/**
 * Loads the translation file to the memory
 * @author Setarit
 *
 */
public class TranslationFileReader {
	
	/**
	 * Default constructor
	 */
	public TranslationFileReader() {
		
	}
	
	/**
	 * Reads the translation file specified by the language code
	 * @param languageCode The code of the language
	 * @return A map containing all the translations
	 * @throws CastleException If there is no language file in the translations directory <b>or</b>
	 * When the translation file is malformed
	 * @throws FileNotFoundException If source is not found
	 */
	public Map<String, String> readTranslationFile(String languageCode) throws FileNotFoundException, CastleException {
		Map<String, String> translations = new HashMap<String, String>();
		Scanner fileScanner = new Scanner(getCorrectTranslationInputStream(languageCode));
		while(fileScanner.hasNextLine()){
			Scanner lineScanner = new Scanner(fileScanner.nextLine());
			lineScanner.useDelimiter("\t");
			String key = lineScanner.next();
			String value = lineScanner.next();
			if(value == null){
				malformedTranslationFile(lineScanner, fileScanner);				
			}
			translations.put(key, value);
			lineScanner.close();
		}
		fileScanner.close();
		return translations;
	}
	
	/**
	 * Invoked when the translation file is malformed
	 * @param lineScanner The scanner instance of the current line in the translations file
	 * @param fileScanner The scanner instance of the file
	 * @throws MalformedTranslationFile When the translation file is malformed
	 */
	private void malformedTranslationFile(Scanner lineScanner, Scanner fileScanner) throws MalformedTranslationFile {
		lineScanner.close();
		fileScanner.close();
		throw new MalformedTranslationFile("The translation file is not well formed");
	}

	/**
	 * Searches the language file specified by the language code
	 * @param languageCode The language code of the translation file
	 * @return The correct translation input stream
	 * @throws CastleException If there is no language file for the given language code in the translations directory
	 */
	private InputStream getCorrectTranslationInputStream(String languageCode) throws CastleException {	
		InputStream stream = getClass().getResourceAsStream("/translations/"+languageCode+".txt");
		if(stream == null){		
			throw new CastleException("There is no translation file for "+languageCode);
		}
		return stream;		
	}
}
