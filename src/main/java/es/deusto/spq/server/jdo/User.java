package es.deusto.spq.server.jdo;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.database.PMF;
import es.deusto.spq.utils.HexUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PersistenceCapable
public class User {

	protected static final Logger logger = LogManager.getLogger();

	private static SecureRandom sr = new SecureRandom();

	@PrimaryKey
	String login = null;
	String password = null;
	String salt = null;

	public User() {
		//For datanucleus
	}

	public User(String login, String password) {
		this.login = login;
		byte[] saltBytes = new byte[16];
		sr.nextBytes(saltBytes);
		this.salt = HexUtils.bytesToHex(saltBytes);
		this.setPassword(password);
	}

	public String getLogin() {
		return this.login;
	}
	
	public byte[] genPassHash(String password) {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), HexUtils.hexToBytes(salt), 65536, 128);
		byte[] ret = null;
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			ret = factory.generateSecret(spec).getEncoded();
			System.out.println("Pashash: "+ Arrays.toString(ret));
			System.out.println("salt: "+ salt);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			//This should never happen
			logger.error("The hashing algorithm PBKDF2WithHmacSHA1 is not supported on this JVM.");
			e.printStackTrace();
		}
		return ret;
	}

	public void setPassword(String password) {
		this.password = HexUtils.bytesToHex(this.genPassHash(password));
		System.out.println("testing entrada : "+ this.password);
	}

	public boolean isPasswordCorrect(String password) {
		byte[] test = this.genPassHash(password);
		System.out.println("testing salida : "+ this.password);
		return Arrays.equals(test, HexUtils.hexToBytes(this.password));
	}
	
	public void save() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(this);
			tx.commit();
		} catch (Exception ex) {
			System.out.println(" $ Error storing an object: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}

	public void delete() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();			
			pm.deletePersistent(this);			
			tx.commit();
		} catch (Exception ex) {
			System.out.println(" $ Error deleting an object: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}

	public static List<User> getAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();

		List<User> users = new ArrayList<>();
		
		try {
			tx.begin();
			
			Extent<User> extent = pm.getExtent(User.class, true);

			for (User user : extent) {
				users.add(user);
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error retrieving all the Categories: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return users;		
	}

	public static User find(String login) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		User result = null; 
		//TODO falla
		try {
			tx.begin();	
			Query<?> query = pm.newQuery("SELECT  FROM " + User.class.getName() + " WHERE login == '" + login + "'");
			query.setUnique(true);
			result = (User) query.execute();
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying a Category: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return result;
	}
}
