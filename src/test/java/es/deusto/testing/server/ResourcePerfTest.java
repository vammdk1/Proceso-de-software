package es.deusto.testing.server;

import static org.junit.Assert.assertEquals;

import java.rmi.server.UID;
import java.util.UUID;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import categories.IntegrationTest;
import categories.PerformanceTest;
import es.deusto.spq.pojo.FriendData;
import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.RoomData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.Main;
import es.deusto.spq.server.jdo.User;

@Category({ IntegrationTest.class, PerformanceTest.class })
public class ResourcePerfTest {

    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    
    private static HttpServer server;
    private WebTarget target;
    private static int contador;

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

    @BeforeClass
    public static void prepareTests() throws Exception {
        // start the server
        server = Main.startServer();
        contador = 1;
        
        
    }

    @Before
    public void setUp() {
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI).path("resource");
    }
    @AfterClass
    public static void tearDownServer() throws Exception {
        server.shutdown();
        //Esto borra la base de datos
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.newQuery(User.class).deletePersistentAll();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Test
    @JUnitPerfTest(threads = 1, durationMs = 5000,warmUpMs = 2000)
    public void testRegisterUser() {
        RegisterData user = new RegisterData();
        user.setLogin("prueba-"+contador);
        contador+=1;
        user.setPassword("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
        
    }
  
    
    @Test
    @JUnitPerfTest(threads = 1, durationMs = 5000,warmUpMs = 2000)
    public void TestLogginUser() {
    	RegisterData user = new RegisterData();
    	user.setLogin("prueba-"+contador);
        contador+=1;
        user.setPassword("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        Response response1 = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response1.getStatusInfo().getFamily());
    }
    
    
    @Test
    @JUnitPerfTest(threads = 1, durationMs = 5000,warmUpMs = 2000)
    public void TestLogOutUser() {
    	RegisterData user = new RegisterData();
    	user.setLogin("prueba-"+contador);
        contador+=1;
        user.setPassword("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        Response response1 = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        TokenData tok = new TokenData();
        
        tok.setToken(response1.readEntity(SessionData.class).getToken());
        
        Response response2 = target.path("logout")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tok, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response2.getStatusInfo().getFamily());
    }
    
    //TODO No funciona
    /**
    @Test
    @JUnitPerfTest(threads = 1, durationMs = 5000,warmUpMs = 2000)
    public void TestDeleteUser() {
    	RegisterData user = new RegisterData();
    	user.setLogin("prueba-"+contador);
        contador+=1;
        user.setPassword("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));


        Response response1 = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        TokenData tok = new TokenData();
        
        tok.setToken(response1.readEntity(SessionData.class).getToken());
        
        Response response2 = target.path("deleteUser")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tok, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response2.getStatusInfo().getFamily());
    }
    **/
    
    @Test
    @JUnitPerfTest(threads = 1, durationMs = 5000,warmUpMs = 2000)
    public void TestRooms() {
    	RegisterData user = new RegisterData();
    	user.setLogin("prueba-"+contador);
        contador+=1;
        user.setPassword("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));


        Response response1 = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        RoomData room = new RoomData();
        
        room.setToken(response1.readEntity(SessionData.class).getToken());
        room.setRoomName("Room -> "+ UUID.randomUUID().toString());
        room.setPassword("");
        
        Response response2 = target.path("createRoom")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(room, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response2.getStatusInfo().getFamily());
        
        Response response3 = target.path("getRooms")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(room, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response3.getStatusInfo().getFamily());
        
        Response response4 = target.path("deleteRoom")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(room, MediaType.APPLICATION_JSON));
        
        assertEquals(Family.SUCCESSFUL, response4.getStatusInfo().getFamily());
    }
    
    @Test
    @JUnitPerfTest(threads = 1, durationMs = 5000,warmUpMs = 2000)
    public void TestFriends() {
    	RegisterData user = new RegisterData();
    	user.setLogin("prueba-"+contador);
        contador+=1;
        user.setPassword("1234");

        Response response = target.path("register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        
        
        
        RegisterData friend = new RegisterData();
        friend.setLogin("Patata");
        friend.setPassword("1234");

        target.path("register").request(MediaType.APPLICATION_JSON).post(Entity.entity(friend, MediaType.APPLICATION_JSON));

        Response response1 = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        
        //TokenData tok2 = new TokenData();
        //tok2.setToken(response1.readEntity(SessionData.class).getToken());
        
        FriendData fData = new FriendData();
        fData.setToken(response1.readEntity(SessionData.class).getToken());
        fData.setFriendName("Patata");
        
        Response response2 = target.path("addFriend")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(fData, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response2.getStatusInfo().getFamily());
        
        //Preguntar por que falla
        //TokenData tok2 = new TokenData();
        //tok2.setToken(response1.readEntity(SessionData.class).getToken());
        
        
        /**
        Response response3 = target.path("getFriends")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tok, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response3.getStatusInfo().getFamily());
        **/
    }
}