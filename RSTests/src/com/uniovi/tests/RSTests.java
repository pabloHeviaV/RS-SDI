package com.uniovi.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_FriendRequestListView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_PublicationAddView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_UserListView;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del m�todo
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RSTests {

	// Antes de cada prueba se navega al URL home de la aplicaci�nn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Despu�s de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la �ltima prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		 driver.quit();
	}

	// En Windows (Debe ser la versi�n 46.0 y desactivar las actualizacioens
	// autom�ticas)):
	static String PathFirefox = 
			"C:\\Users\\yo\\Desktop\\SDI\\entorno-sdi\\entorno-sdi\\firefox\\FirefoxPortable.exe";
		 // "C:\\Users\\Usuario\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versi�n 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// 1.1 [RegVal] Registro de Usuario con datos válidos.
	@Test
	public void PR1_1() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "7@uniovi.es", "Pablo", "Menendez", "123456", "123456");
		PO_LoginView.checkKey(driver, "users.message", PO_Properties.getSPANISH());
	}

	// 1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de
	// contraseña invalida).
	@Test
	public void PR1_2() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "8@uniovi.es", "Sara", "Grimaldos", "123456", "123453");
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// 2.1 [InVal] Inicio de sesión con datos válidos.
	@Test
	public void PR2_1() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		PO_LoginView.checkKey(driver, "users.message", PO_Properties.getSPANISH());
	}

	// 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en
	// la aplicación).
	@Test
	public void PR2_2() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "9@uniovi.es", "123456");
		PO_LoginView.checkKey(driver, "Error.login", PO_Properties.getSPANISH());
	}

	// 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void PR3_1() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		PO_LoginView.checkKey(driver, "users.message", PO_Properties.getSPANISH());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		PO_LoginView.checkKey(driver, "list.users.description", PO_Properties.getSPANISH());
	}

	// 3.2 [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado
	// al
	// listado de usuarios desde un usuario en sesión.
	// Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void PR3_2() {
		driver.navigate().to("http://localhost:8090/user/list");
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	// 4.1 [BusUsrVal] Realizar una búsqueda válida en el listado de
	// usuarios desde un usuario en sesión.
	@Test
	public void PR4_1() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		PO_UserListView.search(driver, "marta");
		PO_View.checkElement(driver, "text", "Marta");
	}

	// 4.2 [BusUsrInVal] Intento de acceso con URL a la búsqueda de usuarios desde
	// un usuario no identificado. Debe producirse un acceso no permitido a vistas
	// privadas.
	@Test
	public void PR4_2() {
		// Intentamos acceder a una url privada sin identificarnos
		driver.navigate().to("http://localhost:8090/user/list?searchText=marta");
		// Nos devuelve a la página de login
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	// 5.1 [InvVal] Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void PR5_1() {
		// Nos logueamos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		// Enviamos una solicitud al usuario 2
		PO_UserListView.sendFriendRequest(driver, 2);
		// Nos deslogueamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

		// Nos logueamos como el usuario 2
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "2@uniovi.es", "123456");
		// Nos deslogueamos

		// Vamos a la lista de solicitudes
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'fr-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friendRequest/list')]");
		elementos.get(0).click();

		// Comprobamos que hay una solicitud del usuario 1
		PO_View.checkElement(driver, "text", "1@uniovi.es");

	}

	// 5.2 [InvInVal] Enviar una invitación de amistad a un usuario al que ya le
	// habíamos invitado la invitación previamente. No debería dejarnos enviar la
	// invitación, se podría ocultar el botón de enviar invitación o notificar que
	// ya había sido enviada previamente.
	@Test
	public void PR5_2() {
		// Nos logueamos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		// Enviamos una solicitud al usuario 5
		PO_UserListView.sendFriendRequest(driver, 5);
		//Comprobamos que el botón está deshabilitado
		By boton = By.id("fRButton5"); 
		PO_View.checkElement(driver, "id", "fRButton5");
	    assertFalse(driver.findElement(boton).isEnabled());
				
	}

	// 6.1 [LisInvVal] Listar las invitaciones recibidas por un usuario, realizar la
	// comprobación con una lista que al menos tenga una invitación recibida.
	@Test
	public void PR6_1() {
		// Nos logueamos con el usuario 1 que ya tiene 3 peticiones de amistad
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		
		// Vamos a la lista de solicitudes
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'fr-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friendRequest/list')]");
		elementos.get(0).click();
		
		// Comprobamos que entramos en la página de las solicitudes de amistad 
		PO_View.checkElement(driver, "text", "solicitudes de amistad");
		// Contamos el número de filas de solicitudes
		List<WebElement> filas = PO_View.checkElement(driver, "free", "//tbody/tr");
		assertTrue(filas.size() == 3);
		
	}
	
	// 7.1 [AcepInvVal] Aceptar una invitación recibida.
	@Test
	public void PR7_1() {
		// Nos logueamos con el usuario 1 que ya tiene 3 peticiones de amistad
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		
		// Vamos a la lista de solicitudes
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'fr-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friendRequest/list')]");
		elementos.get(0).click();
		
		// Comprobamos que entramos en la página de las solicitudes de amistad 
		PO_View.checkElement(driver, "text", "solicitudes de amistad");
		
		// Aceptamos la petición de amistad con id 1 (la del usuario 2)
		PO_FriendRequestListView.acceptFriendRequest(driver, 1);
		
		// Vamos a la lista de amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		
		//Comprobamos que está el usuario 2
		PO_View.checkElement(driver, "text", "2@uniovi.es");
	}
	
	// 8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobación con una lista que al menos tenga un amigo.
	@Test
	public void PR8_1() {
		// Nos logueamos con el usuario 1 que ya tiene 2 peticiones de amistad y un amigo
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		
		// Vamos a la lista de solicitudes
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'fr-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friendRequest/list')]");
		elementos.get(0).click();
		
		// Comprobamos que entramos en la página de las solicitudes de amistad 
		PO_View.checkElement(driver, "text", "solicitudes de amistad");
		
		// Aceptamos todas las peticiones de amistad 
		PO_FriendRequestListView.acceptFriendRequest(driver, 2);
		// Esperamos a que se recargue la página
		PO_View.checkElement(driver, "id", "fRButton3");
		PO_FriendRequestListView.acceptFriendRequest(driver, 3);
		
		// Vamos a la lista de amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		
		// Contamos que tiene 3 amigos
		List<WebElement> filas = PO_View.checkElement(driver, "free", "//tbody/tr");
		assertTrue(filas.size() == 3);
	}
	
	//9.1 [PubVal] Crear una publicación con datos válidos.
	@Test
	public void PR9_1() {
		// Nos logueamos con el usuario 1
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		
		// Vamos a la vista de crear publicación
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publication-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/publication/add')]");
		elementos.get(0).click();
		
		// Comprobamos que estamos en la página correcta
		PO_View.checkElement(driver, "text", "Crea una nueva publicación");
		
		// Creamos una publicación 
		PO_PublicationAddView.createPublication(driver, "Título de la publicación", "Cuerpo de prueba");
		
	}
}
