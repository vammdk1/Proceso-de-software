package es.deusto.testing.JDO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import es.deusto.spq.server.jdo.User;

public class JDOTest {
		
	User user;
	
	@Test
	public void TestUser() {
		user = new User("Nombre","Ab123456789");
		assertEquals("Nombre", user.getLogin());
		assertEquals(true, user.isPasswordCorrect("Ab123456789"));
		user.save();
		assertNotEquals(0,user.getAll().size());
		assertEquals(user.getLogin(), user.find(user.getLogin()).getLogin());
		//user.delete();
		//assertEquals(user.getLogin(), user.find(user.getLogin()).getLogin());
	}
	
}
