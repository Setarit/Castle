package castle;

import java.util.Locale;

import core.Translator;
import environment.DisplayLanguage;
import environment.SettingKey;
import environment.Settings;
import exception.CastleException;
import exception.InvalidSettingKey;
import exception.NoSuchStringToTranslate;

public class DefaultCastleApi implements CastleApi {
	private static DefaultCastleApi instance;	
	private DefaultCastleApi() throws CastleException{
		settings = new Settings();
		settings.addSetting(SettingKey.LANGUAGE, DisplayLanguage.getDisplayLanguage());		
		translator = new Translator(settings);
		setFallBackLanguage("en");
	}
	public DefaultCastleApi(String language) throws CastleException {
		settings = new Settings();
		try{
			settings.addSetting(SettingKey.LANGUAGE, DisplayLanguage.convertLetterCodeToLocale(language).getLanguage());		
			translator = new Translator(settings);
			setFallBackLanguage("en");
		}catch (CastleException exception) {
			throw new CastleException(exception);
		}
	}
	/**
	 * Creates an instance of the CastleApi.
	 * Automatically determines the language to use by the screen language
	 * @return An instance of the CastleApi
	 * @throws CastleException If something goes wrong :-) (Check the docs and exception message)
	 */
	public static CastleApi getInstance() throws CastleException {
		if(instance == null){
			return new DefaultCastleApi();
		}
		return instance;
	}
	
	/**
	 * Creates an instance of the CastleApi.	
	 * @param language The ISO 639 letter code of the language to use
	 * @return An instance of the CastleApi
	 * @throws CastleException If something goes wrong :-) (Check the docs and exception message)
	 */
	public static CastleApi getInstance(String language) throws CastleException {
		if(instance == null){
			return new DefaultCastleApi(language);
		}
		return instance;
	}
	
	//Required classes
	private Settings settings;
	private Translator translator;
	//END Required classes
	
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String translate(String key) throws CastleException {		
		try {
			return translator.translate(key);
		} catch (InvalidSettingKey e) {
			throw new CastleException(e);
		} catch (NoSuchStringToTranslate e) {
			throw new CastleException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String translateFromLanguage(String key, String language) throws CastleException {
		throw new CastleException("Not yet implemented");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFallBackLanguage(String language) throws CastleException {
		Locale languageCode = DisplayLanguage.convertLetterCodeToLocale(language);
		if(settings.settingExists(SettingKey.FALLBACKLANGUAGE)){
			settings.updateSetting(SettingKey.FALLBACKLANGUAGE, languageCode.getLanguage());
		}else{
			settings.addSetting(SettingKey.FALLBACKLANGUAGE, languageCode.getLanguage());
		}
		translator.setFallBackLanguage(language);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFallBackLanguage() throws CastleException {
		try {
			return (String) settings.getSettingValue(SettingKey.FALLBACKLANGUAGE);
		} catch (InvalidSettingKey e) {
			throw new CastleException(e);
		}
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String getLanguage() throws CastleException {
		try {
			return (String) settings.getSettingValue(SettingKey.LANGUAGE);
		} catch (InvalidSettingKey e) {
			throw new CastleException(e);
		}
	}

}
