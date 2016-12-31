package environment;

import java.util.Locale;

import exception.InvalidLanguageLetterCode;

/**
 * Class for accessing the display language
 * @author Setarit
 *
 */
public class DisplayLanguage {
	public static final int MAX_LANGUAGE_CODE_LENGTH = 3;

	/**
	 * Determines the current display language
	 * @return The two letter code (ISO 639) of the display language
	 */
	public static String getDisplayLanguage(){
		return Locale.getDefault().getLanguage();
	}

	public static Locale convertLetterCodeToLocale(String language) throws InvalidLanguageLetterCode {
		if(language.length() > MAX_LANGUAGE_CODE_LENGTH){
			throw new InvalidLanguageLetterCode("The language code is invalid, this must be a string of maximum 3 characters");
		}
		return new Locale(language);		
	}
}
