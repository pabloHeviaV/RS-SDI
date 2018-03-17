package com.uniovi.tests;

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

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

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
	static String PathFirefox = "C:\\Users\\Usuario\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
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
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "7@uniovi.es", "Pablo", "Menendez", "123456", "123456");
		PO_LoginView.checkKey(driver, "users.message", PO_Properties.getSPANISH());
	}

	// 1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de
	// contraseña invalida).
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "8@uniovi.es", "Sara", "Grimaldos", "123456", "123453");
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// 2.1 [InVal] Inicio de sesión con datos válidos.
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		PO_LoginView.checkKey(driver, "users.message", PO_Properties.getSPANISH());
	}

	// 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en
	// la aplicación).
	@Test
	public void PR04() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "9@uniovi.es", "123456");
		PO_LoginView.checkKey(driver, "Error.login", PO_Properties.getSPANISH());
	}

	// 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void PR05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "1@uniovi.es", "123456");
		PO_LoginView.checkKey(driver, "users.message", PO_Properties.getSPANISH());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		PO_LoginView.checkKey(driver, "list.users.description", PO_Properties.getSPANISH());
	}

	// [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado al
	// listado de usuarios desde un usuario en sesión.
	// Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void PR06() {
		driver.navigate().to("http://localhost:8090/user/list");
		PO_LoginView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

}
