package environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.InvalidSettingKey;

public class Settings {
	private Map<SettingKey, Object> settings;
	private List<SettingKey> initialisedSettingKeys;
	
	public Settings(){
		settings = new HashMap<SettingKey, Object>();
		initialisedSettingKeys = new ArrayList<SettingKey>();
	}
	
	/**
	 * Adds a setting to the Castle environment
	 * @param key The name/key of the setting. This is an enum of type {@link SettingKey}
	 * @param value The value of the setting
	 */
	public void addSetting(SettingKey key, Object value){
		initialisedSettingKeys.add(key);
		settings.put(key, value);
	}
	
	/**
	 * Updates the setting with key to the new value
	 * @param key The key of the setting to update
	 * @param newValue The new value for the variable
	 */
	public void updateSetting(SettingKey key, Object newValue){
		settings.put(key, newValue);
	}
	
	/**
	 * Determines if a setting exists in the Castle environment
	 * @param key The name/key of the setting
	 * @return True if the setting exists in the Castle environment
	 */
	public boolean settingExists(SettingKey key){
		return initialisedSettingKeys.contains(key);
	}
	
	/**
	 * Deletes a setting from the Castle environment
	 * @param key The name/key of the setting to delete
	 */
	public void deleteSetting(SettingKey key){
		initialisedSettingKeys.remove(key);
		settings.remove(key);
	}
	
	private void checkIfExists(SettingKey key) throws InvalidSettingKey {
		if(!settingExists(key)){
			throw new InvalidSettingKey("The key "+key.name()+" does not exist in the settings");
		}
	}
	
	/**
	 * Gets the Object value for a key
	 * @param key The key of the setting
	 * @return The corresponding value of the key
	 * @throws InvalidSettingKey If the key does not exist in the setting
	 */
	public Object getSettingValue(SettingKey key) throws InvalidSettingKey{
		checkIfExists(key);
		return settings.get(key);
	}
}
