package es.deusto.spq.server.jdo;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@PersistenceCapable
public class User {

	protected static final Logger logger = LogManager.getLogger();

	private static SecureRandom sr = new SecureRandom();

	@PrimaryKey
	String login = null;
	byte[] password = null;
	byte[] salt = null;

	public User() {
		//For datanucleus
	}

	public User(String login, String password) {
		this.login = login;
		this.salt = new byte[16];
		sr.nextBytes(salt);
		this.setPassword(password);
	}

	public String getLogin() {
		return this.login;
	}
	
	public byte[] genPassHash(String password) {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		byte[] ret = null;
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			ret = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			//This should never happen
			logger.error("The hashing algorithm PBKDF2WithHmacSHA1 is not supported on this JVM.");
			e.printStackTrace();
		}
		return ret;
	}

	public void setPassword(String password) {
		this.password = this.genPassHash(password);
	}

	public boolean isPasswordCorrect(String password) {
		byte[] test = this.genPassHash(password);
		return test.equals(this.password);
	}
}
