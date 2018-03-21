package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PublicationAddView extends PO_NavView {

	public static void createPublication(WebDriver driver, String titlep, String bodyp) {
		WebElement title = driver.findElement(By.name("title"));
		title.click();
		title.clear();
		title.sendKeys(titlep);
		WebElement body = driver.findElement(By.name("body"));
		body.click();
		body.clear();
		body.sendKeys(bodyp);
		
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
