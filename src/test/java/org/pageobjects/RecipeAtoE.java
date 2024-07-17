package org.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import pages.Recipe;
import utils.ExcelUtility;

public class RecipeAtoE  extends TestBase{

	public RecipeAtoE(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	public void click_AtoZ_recipes()
	{
		//driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}
	
	public void getRecipeInfo() throws Exception {
		Recipe recipeInfo = new Recipe();
		recipeInfo.setRecipe_id("577");
		recipeInfo.setRecipe_name("Kaachi Keri No Sambhaar");
		recipeInfo.setRecipe_Description("Kaachi Keri No Sambhaar Description");
		recipeInfo.setPrep_time("5 mins");
		recipeInfo.setIngredients("raw mango oil sambhar");
		//recipeInfo.setRecipe_Description(desc);
		//recipeInfo.setRecipe_Description(desc);
		
			
		System.out.println("*Write to excel ");
		ExcelUtility.storeRecipeInfo(recipeInfo, 2, 2);		
	}
	

}
