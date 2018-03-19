package com.uniovi.tests.pageobjects; 
 
import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement; 
 
public class PO_UserListView extends PO_NavView{ 
   
  static public void search(WebDriver driver, String searchTerm) { 
    WebElement searchBox = driver.findElement(By.name("searchText")); 
    searchBox.click(); 
    searchBox.clear(); 
    searchBox.sendKeys(searchTerm); 
    // Pulsar el boton de Buscar. 
    By boton = By.id("searchButton"); 
    driver.findElement(boton).click(); 
  } 
   
  static public void sendFriendRequest(WebDriver driver, int id) { 
    //Buscamos el botón del usuario con el id indicado y lo pulsamos 
    By boton = By.id("fRButton" + id); 
    driver.findElement(boton).click(); 
  } 
} 