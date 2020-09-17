package ru.probe.config;

/**
 * Use bean for get auth settings
 * @see ConfigInjectorFactory
 * @author maksim
 *
 */
public class AuthConfig {
	
	/**
	 * time of token's life
	 */
	private final int EXPIRATION_TIME;
	
	/**
	 * token key in a <i>header</i> or <i>return message</i>
	 */
	private final String KEY_NAME_TOKEN;
	
	/**
	 * token's secret key
	 */
	private final String SECRET_KEY;
	
	/**
	 * main config constructor
	 * 
	 * @param KEY_NAME_TOKEN
	 * @param SECRET_KEY
	 * @param EXPIRATION_TIME
	 */
	public AuthConfig(
				String	KEY_NAME_TOKEN,
				String	SECRET_KEY,
				int		EXPIRATION_TIME
			) {
		this.KEY_NAME_TOKEN = KEY_NAME_TOKEN;
		this.SECRET_KEY = SECRET_KEY;
		this.EXPIRATION_TIME = EXPIRATION_TIME;
	}
	
	/**
	 * KEY_NAME_TOKEN getter
	 * @return KEY_NAME_TOKEN as String
	 */
	public String getKEY_NAME_TOKEN() {
		return this.KEY_NAME_TOKEN;
	}

	/**
	 * SECRET_KEY getter
	 * @return SECRET_KEY as String
	 */
	public String getSECRET_KEY() {
		return this.SECRET_KEY;
	}

	/**
	 * EXPIRATION_TIME getter
	 * @return EXPIRATION_TIME as String
	 */
	public int getEXPIRATION_TIME() {
		return this.EXPIRATION_TIME;
	}
	
}
