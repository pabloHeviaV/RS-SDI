package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PO_FriendRequestListView extends PO_NavView {

	/**
	 * Acepta la petición de amistad del usuario con id pasado.
	 * @param driver
	 * @param id
	 */
	static public void acceptFriendRequest(WebDriver driver, int id) {
		By boton = By.id("fRButton" + id);
		driver.findElement(boton).click();
	}
}
