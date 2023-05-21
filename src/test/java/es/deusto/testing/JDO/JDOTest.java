package es.deusto.testing.JDO;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.server.Resource;
import es.deusto.spq.server.jdo.User;

public class JDOTest {
	
	Resource resource;
	
	@Mock
    private PersistenceManager persistenceManager;
	
	@Mock
    private Transaction transaction;
	
	@Mock
	User user;
	
	@Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // initializing static mock object PersistenceManagerFactory
        try (MockedStatic<JDOHelper> jdoHelper = Mockito.mockStatic(JDOHelper.class)) {
            PersistenceManagerFactory pmf = mock(PersistenceManagerFactory.class);
            jdoHelper.when(() -> JDOHelper.getPersistenceManagerFactory("datanucleus.properties")).thenReturn(pmf);
            
            when(pmf.getPersistenceManager()).thenReturn(persistenceManager);
            when(persistenceManager.currentTransaction()).thenReturn(transaction);

            // instantiate tested object with mock dependencies
            resource = new Resource();
        }
	}
	
	@Test
	public void TestUser() {
		user = new User("Nombre","Ab123456789");
		assertEquals("Nombre", user.getLogin());
		assertEquals(true, user.isPasswordCorrect("Ab123456789"));
		user.addFriend("paco");
		user.save();
		//assertEquals(user.getFriendsList().size(), 1);
		assertEquals(user.equals(user), true);
		user.delete();
	}
	
}
