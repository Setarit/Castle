import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import castle.CastleApi;
import castle.DefaultCastleApi;
import exception.CastleException;

public class DefaultCastleApiTest {
	private CastleApi api;
	private CastleApi apiGerman;

	@Before
	public void setUp() throws CastleException {		
		api = DefaultCastleApi.getInstance();
		apiGerman = DefaultCastleApi.getInstance("de");
	}
	
	@Test
	public void translateGivesTranslatedString() throws CastleException{
		String translated = api.translate("papier");
		assertEquals("paper", translated);
		String translatedSentence = api.translate("Het Spaanse graan heeft de orkaan doorstaan.");
		assertEquals("The rain in Spain.", translatedSentence);
	}
	
	@Test(expected=CastleException.class)
	public void translateThrowsCastleExceptionWhenTheRequestedTranslationDoesNotExistInTheTranslationsNorInTheFallBackTranslationsFile() throws CastleException{
		api.translate("setarit");
	}
	
	@Test(expected=CastleException.class)
	public void setFallBackLanguageThrowsCastleExceptionWhenTheTranslationLanguageFileDoesNotExist() throws CastleException{
		api.setFallBackLanguage("nl");
	}
	
	@Test
	public void getFallBackLanguageReturnsEnglishAsDefault() throws CastleException{
		assertEquals("en", api.getFallBackLanguage());
	}
	
	@Test
	public void setFallBackLanguageChangesTheFallBackLanguage() throws CastleException{
		api.setFallBackLanguage("de");
		assertEquals("de", api.getFallBackLanguage());
	}
	
	@Test
	public void getLanguageReturnsISO639CodeOfTheUsedLanguage() throws CastleException{
		assertEquals("de", apiGerman.getLanguage());
	}
	
	@Test(expected=CastleException.class)
	public void translateFromLanguageThrowsCastleException() throws CastleException{
		api.translateFromLanguage("papier", "nl");
	}

	@Test
	public void translateWillUseFallbackLanguageIfStringToTranslateNotFound() throws CastleException {
		assertEquals("door", apiGerman.translate("door"));
	}	
}
