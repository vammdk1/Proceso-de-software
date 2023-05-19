package es.deusto.testing.window;

public class windowLoginTest {
	/**
	Resource resource;
	
	UserData user ;
	
	@SuppressWarnings("unchecked")
	private static <T> T readEntity(Response realResponse, Class<T> t) {
		return (T)realResponse.getEntity();
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T readEntity(Response realResponse, GenericType<T> t) {
		return (T)realResponse.getEntity();
	}
	
	public static Response simulateInbound(Response asOutbound) {
		Response toReturn = spy(asOutbound);//redirecciones
		doAnswer(AdditionalAnswers.answer((Class<?> type) -> readEntity(toReturn, type))).when(toReturn).readEntity(ArgumentMatchers.<Class<?>>any());
		doAnswer(AdditionalAnswers.answer((GenericType<?> type) -> readEntity(toReturn, type))).when(toReturn).readEntity(ArgumentMatchers.<GenericType<?>>any());
		return toReturn;
	}
	
	@Mock
	PictochatntClient client;
	
	@Before
	public void setUp() {
		resource = new Resource();
		MockitoAnnotations.openMocks(this);
		PictochatntClient.instace = client;
	}
	
	
	@Test
	public void testVentanaLogin() {
		VentanaLogin ventanaLogin = new VentanaLogin();
		assertTrue(ventanaLogin.isActive());
		
		Robot bot;
		try {
			bot = new Robot();
			bot.mouseMove(20,20);
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			try{Thread.sleep(250);}catch(InterruptedException e){}
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ventanaLogin.dispose();
		
		
	}
	
	
	
	@Test
	public void testVentanaMenu() {
		
		ArrayList<GetRoomData> roomData = new ArrayList<>();
		GenericEntity<ArrayList<GetRoomData>> entity = new GenericEntity<ArrayList<GetRoomData>>(roomData) {};
		Response response = simulateInbound(Response.ok().entity(entity).build());
		when(client.post(eq("/getRooms"))).thenReturn(response);
		//assertEquals(roomData.size(), PictochatntClient.getActiveRooms().size());
		
		VentanaMenu ventanaMenu = new VentanaMenu();
		assertTrue(ventanaMenu.isActive());
		ventanaMenu.bCrear.doClick();
		assertTrue(ventanaMenu.datos.isVisible());
		
		ventanaMenu.bConectar.doClick();
		
		ventanaMenu.bRefrescar.doClick();
		
		ventanaMenu.Aceptar.doClick();
		
		ventanaMenu.Cancelar.doClick();
		
		assertFalse(ventanaMenu.datos.isVisible());
		
		//ventanaMenu.bSalir.getActionListeners()
		ventanaMenu.dispose();
	}
	**/
}
