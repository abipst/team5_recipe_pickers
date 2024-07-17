package org.pageobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import pages.Recipe;
import utils.ExcelReaderCode;
import utils.ExcelUtility;

public class RecipeKtoO  extends TestBase{

	List<String> LFV_EliminateItemList=new ArrayList<String>();
	List<String> LFV_AddItemList=new ArrayList<String>();
	
	public RecipeKtoO(WebDriver driver) 
	{
		PageFactory.initElements(driver,this);
	}
	
	
	public void click_AtoZ_recipes()
	{
		driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}
	
	
	
	int pageCount=0;
	public void getRecipeInfo() throws Exception {
				
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a[1]"));
		
		List<Recipe> recipeList = new ArrayList<Recipe>();
		
		//Read Elimination data from excel and store it into arraylist
		this.read_LFV_Elimination_Excel();
		
		//Read Add data from excel and store it into arraylist
		this.read_LFV_Add_Excel();
		
		int size=menuAtoZWebElements.size();
		System.out.println("There are "+size+" number of links ordered alphabetically.");
		int counter=0;
		for(int i=11; i<=12; i++) 
		{
			//String menuLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a")).getAttribute("href");
			//System.out.println(menuLink);
			WebElement AlphabetLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			String alphabet=AlphabetLink.getText();
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			AlphabetLink.click();
			List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			if(alphabet.equals("P"))
			 {
				pageCount=1;
			 }
			
			else 
			  {
				String s=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[15]")).getText();
				pageCount=Integer.parseInt(s);
				System.out.println("Toal page count is: "+pageCount);
			  }
				
			for(int pg=1; pg<3/*pageCount*/; pg++) 
			 {
				try 
				 {
					WebElement current_pg=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='"+pg+"']"));
					current_pg.click();
					System.out.println("******  Alphabet is "+alphabet+"  **** Current page is: "+pg+"  *********");
				 }
				
				catch(StaleElementReferenceException e) 
				{	
					
				}
				
								
				List<WebElement> recipes_url=driver.findElements(By.className("rcc_recipename"));
				int no_of_cards=recipes_url.size();
				ArrayList<String> links=new ArrayList<>(30);
				for(WebElement url: recipes_url) 
				 {
					String recipesLink=url.findElement(By.tagName("a")).getAttribute("href");
					links.add(recipesLink);
			     }
					
					
					for(Object eachRecipe:links)
					  {
						//System.out.println("Counter: "+counter);
						
						//if(counter <= 10)
						//{							
							//counter= counter + 1;
						try 
						 {
							Document doc=Jsoup.connect((String)eachRecipe).timeout(1000*100).get();
							String stringurl=eachRecipe.toString();
							//System.out.println("Recipe URL : "+stringurl);
							String id=stringurl.substring(stringurl.lastIndexOf("-")+1);
							String recipe_id=id.substring(0,id.length()-1);
							
							//String recipe_name = 
							//System.out.println("Recipe_id: "+recipe_id);
							// fetching recipe prep time, cook time and servings
							//prep time
							Elements preptimeEle=doc.selectXpath("//time[@itemprop='prepTime']");
							String prepTime=preptimeEle.text();
							//cooking time
							Elements cookTimeEle=doc.selectXpath("//time[@itemprop='cookTime']");
							String cookTime = cookTimeEle.text();
							//No of servings
							Elements noOfServingsEle=doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblServes']");
							String Servings = noOfServingsEle.text();
							String noOfServings=Servings.substring(1, Servings.length());
							
							// fetching recipe tags
							Elements tagEle=doc.selectXpath("//div[@id='recipe_tags']/a");
							String tags=tagEle.text();
							
							// fetching Cuisine category
							Elements cuiCatEle = doc.selectXpath("//div[@id='show_breadcrumb']/div/span[5]/a");
							String category=cuiCatEle.text();
							
							// fetching Recipe Description
							Elements recDesEle = doc.selectXpath("//div[@id='recipe_details_left']/section/p/span");
							String desc=recDesEle.text();
							
							// fetching Preparation method
							Elements preMehodEle = doc.selectXpath("//div[@id='recipe_small_steps']/span");
							String method=preMehodEle.text();
							
						   // fetching Nutrient values
							Elements nutValueEle = doc.selectXpath("//table[@id='rcpnutrients']/tbody/tr");
							String nutritionValue="";
							for (Element row : nutValueEle) 
							 {								 
							   Elements cols = row.select("td");
							   nutritionValue = nutritionValue + cols.text();
					         } 
							
							// fetching Ingredients 
							Elements ingredientsEle = doc.selectXpath("//div[@id='rcpinglist']/div//a");							
							String ingredientsValue = ingredientsEle.text();
							
							/*
							  // fetching Recipe URL
							Elements repurl = doc.selectXpath("//div[@id=");
							String url=repurl.text();
							*/							
								
							
							
							
							boolean validRecipe = true;
							//Retrieve data from Elimination arraylist using for loop, 
							for(String eliminatedItem: LFV_EliminateItemList) 
							{								
								//Then compare each value with Ingredients.
								if(ingredientsValue.contains(eliminatedItem))
								{
									//System.out.println("Item invalid: " +eliminatedItem);
									validRecipe = false;
									break;
								}									
							}
							
							
							if(validRecipe) 
							{
								//Retrieve data from Add arraylist using for loop, 
								for(String addItem: LFV_AddItemList) 
								{								
									//Then compare each value with Ingredients.
									if(ingredientsValue.contains(addItem))
										{
										System.out.println("Item valid: " +addItem);
										validRecipe = true;
										break;
										}	
									else
										{
										validRecipe = false;
									
										}
								}
							}
							
							if(validRecipe)
							{
								//Store Recipe data into Recipe object
								Recipe recipeInfo = new Recipe();
								recipeInfo.setRecipe_id(recipe_id);
								recipeInfo.setRecipe_name(recipe_id);
								recipeInfo.setRecipe_Description(desc);
								recipeInfo.setPrep_time(prepTime);
								recipeInfo.setIngredients(ingredientsValue);
								//recipeInfo.setRecipe_Description(desc);
								//recipeInfo.setRecipe_Description(desc);
								
								counter= counter + 1;
								//if(counter == 0)
								//{
									System.out.println("*Write to excel ");
									ExcelUtility.storeRecipeInfo(recipeInfo, counter, 0);
								//}
								
								System.out.println("*valid Recipes--Recipe URL : "+stringurl);
								//System.out.println("Recipe URL : "+stringurl);
								//System.out.println("Recipe_id: "+recipe_id);
								//System.out.println("prep time is:  "+prepTime);
								//System.out.println("cook time is : "+cookTime);
								//System.out.println("servings : "+noOfServings);
								//System.out.println("Tags : "+tags);
								//System.out.println("Cuisine category : "+category);
								//System.out.println("Recipe Description : "+desc);
								//System.out.println("Preparation method : "+method);
								//System.out.println("Nutrient values : "+nutritionValue);
								//System.out.println("Ingredients : "+ingredientsValue);
								
							}	
							
						 } 
						
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//To click on each recipe
					//}//End If 
						
				}//End for				
			 }//End pagination
			System.out.println("Total Valid Recipe = " +counter);
			
		}
	}

	public void read_LFV_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 76; i++) {
			String testData = reader.getCellData("Final list for LFV Elimination ", 0, i);
			LFV_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_LFV_Add_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 90; i++) {
			String testData = reader.getCellData("Final list for LFV Elimination ", 1, i);
			LFV_AddItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	

}
						


						
					



