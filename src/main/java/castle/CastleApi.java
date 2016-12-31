package castle;

import core.Translator;
import exception.CastleException;

/**
 * API interface for the framework
 * @author Setarit
 */
public interface CastleApi {
	/**
	 * Translates the text to the language of the current user.
	 * @param key The key of the text to translate
	 * @return The translated text.
	 * @throws CastleException See {@link Translator#translate(String)}
	 */
	String translate(String key) throws CastleException;
	/**
	 * Translates the text of the corresponding key to the language
	 * specified in the second parameter.
	 * @param key The key of the text to translate
	 * @param language The two characters language code
	 * @return The translated text
	 * @throws CastleException Not yet implemented
	 */
	String translateFromLanguage(String key, String language) throws CastleException;	
	/**
	 * Sets the fall back language
	 * If there is no language file available for the language of the
	 * user, it will use the fall back language.
	 * 
	 * The <b>default</b> fall back language is <b>English</b>
	 * @param language The two/three characters language code (ISO 639)
	 * @throws CastleException If language is an invalid letter code (ISO 639) or
	 * see {@link Translator#readTranslationFile(String, boolean)}
	 */
	void setFallBackLanguage(String language) throws CastleException;

	/**
	 * Gets the fall back language
	 * @return The fall back language as a ISO 639 string
	 * @throws CastleException If the fall back language is not configured
	 */
	String getFallBackLanguage() throws CastleException;
	
	/**
	 * Gets the translation language that is used
	 * @return The translation language as a ISO 639 string
	 * @throws CastleException If the translation language is not configuerd
	 */
	String getLanguage() throws CastleException;
}
