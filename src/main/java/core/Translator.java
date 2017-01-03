package core;

import java.util.HashMap;
import java.util.Map;

import environment.SettingKey;
import environment.Settings;
import exception.CastleException;
import exception.InvalidSettingKey;
import exception.NoSuchStringToTranslate;

/**
 * Core class of the translation
 * @author Setarit
 */
public class Translator {

	private Settings settings;

	/**
	 * Constructor for the Translator instance
	 * @param settings The shared variables
	 * @throws CastleException If the translation file does not exist, if the setting "LANGUAGE" is not set
	 * or when the translation file is malformed
	 */
	public Translator(Settings settings) throws CastleException {
		this.settings = settings;
		readTranslationFile((String) settings.getSettingValue(SettingKey.LANGUAGE), false);
	}

	/**
	 * Reads the translation file for the language specified in the settings
	 * @param ISO639LanguageCode The ISO 639 letter code of the translation file to read
	 * @param isDefaultLanguage True if the translation file is the default language 
	 * @throws CastleException If the translation file does not exist, if the setting "LANGUAGE" is not set
	 * or when the translation file is malformed
	 */
	public void readTranslationFile(String ISO639LanguageCode, boolean isDefaultLanguage) throws CastleException{
		TranslationFileReader translationsReader = new TranslationFileReader();
		try{
			Map<String, String> translations = translationsReader.readTranslationFile(ISO639LanguageCode);
			if(isDefaultLanguage){
				if(settings.settingExists(SettingKey.FALLBACK_TRANSLATIONS)){
					settings.updateSetting(SettingKey.FALLBACK_TRANSLATIONS, translations);
				}else{
					settings.addSetting(SettingKey.FALLBACK_TRANSLATIONS, translations);
				}
			}else{
				if(settings.settingExists(SettingKey.TRANSLATIONS)){
					settings.updateSetting(SettingKey.TRANSLATIONS, translations);
				}else{
					settings.addSetting(SettingKey.TRANSLATIONS, translations);
				}
			}
		}catch (Exception e) {
			throw new CastleException(e);
		}	
	}

	/**
	 * Translates the string to another language
	 * @param stringToBeTranslated The string to be translated
	 * @return The translated string
	 * @throws InvalidSettingKey If the translations are not yet read from the file
	 * @throws NoSuchStringToTranslate If the string to be translated does not exist in the translations and fall back translation file
	 */
	public String translate(String stringToBeTranslated) throws InvalidSettingKey, NoSuchStringToTranslate {
		String translated = translateToSpecifiedLanguage(stringToBeTranslated);
		if(translated == null){
			translated = translateToFallBackLanguage(stringToBeTranslated);
			if(translated == null) throw new NoSuchStringToTranslate("The string you passed to translate does not exist in the translations nor in the fall back translations file");
		}
		return translated;
	}
	
	/**
	 * Translates the string to the other language
	 * @param stringToBeTranslated The string to be translated
	 * @return The translated string
	 * @throws InvalidSettingKey If the translations are not yet read from the file
	 */
	private String translateToSpecifiedLanguage(String stringToBeTranslated) throws InvalidSettingKey{
		@SuppressWarnings("unchecked")
		HashMap<String, String> translations = (HashMap<String, String>) settings.getSettingValue(SettingKey.TRANSLATIONS);
		return translations.get(stringToBeTranslated);
	}
	
	/**
	 * Translate the string to the fall back language
	 * @param stringToBeTranslated The string to be translated
	 * @return The translated string
	 * @throws InvalidSettingKey If the fall back translations are not yet read from the file
	 */
	private String translateToFallBackLanguage(String stringToBeTranslated) throws InvalidSettingKey{
		@SuppressWarnings("unchecked")
		HashMap<String, String> fallBackTranslations = (HashMap<String, String>) settings.getSettingValue(SettingKey.FALLBACK_TRANSLATIONS);
		return fallBackTranslations.get(stringToBeTranslated);
	}

	/**
	 * Updates the fall back language and fall back translations
	 * @param language The language code of the fall back language
	 * @throws CastleException If the translation file does not exist, if the setting "LANGUAGE" is not set
	 * or when the translation file is malformed
	 */
	public void setFallBackLanguage(String language) throws CastleException {
		readTranslationFile(language, true);
	}

}
