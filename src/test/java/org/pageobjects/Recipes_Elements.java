package org.pageobjects;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class Recipes_Elements {
	
	//By Locators
	private WebDriver driver;
	private By parentLink = By.xpath("//span[@class='rcc_recipename']");
	String rm,rp;
	WebElement el=null;
	
	//Constructor
	public Recipes_Elements(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
		
	}
	
	
	//Actions
	public void selectRecepie() {
		
/*		int e=1;
//		driver.navigate().refresh();
		try {
			
			el=driver.findElement(By.xpath("//a[text() = '"+rm+"']"));
			el.click();
			driver.navigate().back();
					
		}
		catch(Exception E){
			
			System.out.println("Exception:" +e++);
		}
    	//Actions actions = new Actions(driver);
 	    //actions.moveToElement(el).click().perform();
		//driver.navigate().back();
//		driver.navigate().refresh();
		System.out.println(rm);*/
		
		
		
    }
	
	

}
