package org.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class RecipeAtoE  extends TestBase{

	public RecipeAtoE(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	public void click_AtoZ_recipes()
	{
		driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}
	
	public void getRecipeInfo() {
		//driver.findElements(By.xpath("\"//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a")).getText();
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a"));
		//List<String> menuAtoZ=menuAtoZWebElements.get
		int size=menuAtoZWebElements.size();
		System.out.println("The size of menu is: "+size);
		System.out.println("The menu is: ");
		for(WebElement eachMenu:menuAtoZWebElements) {
			System.out.println(eachMenu.getText()+"  ");
			//eachMenu.click();
			if(eachMenu.getText().equals("0-9")||eachMenu.getText().equals("Misc")) {
				continue;
			}
			List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			String s=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[15]")).getText();
			//int pageCount=pages.size();
			int pageCount=Integer.parseInt(s);  
			System.out.println("Page count is: "+pageCount);
			
				
			
			
			for(int pg=1; pg<pageCount; pg++) {
				try {
				WebElement current_pg=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='"+pg+"']"));
				current_pg.click();
			}
				catch(StaleElementReferenceException e) {
//					WebElement current_pg=driver.findElement(By.cssSelector("#maincontent > div:nth-child(1) > div:nth-child(2) > a.rescurrpg"));
//					current_pg.click();
				}
				List<WebElement> recipe_names_links=driver.findElements(By.xpath("//div[@class='rcc_recipecard']//div[3]/span/a"));
				int no_of_recipes=recipe_names_links.size();
				for(WebElement recipesLink: recipe_names_links) {
					String recipe_name=recipesLink.getText();
					System.out.println(recipe_name);
				}
				
//				for(int i=0; i<no_of_recipes; i++) {
//					
//					
//				}
			}
			
			
		}
	}
	

}
