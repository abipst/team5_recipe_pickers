package org.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.support.PageFactory;


import base.TestBase;
import utils.ExcelReaderCode;

public class RecipeAtoE  extends TestBase{
	
	List<String> LFV_EliminateItemList=new ArrayList<String>();
	List<String> LCH_EliminateItemList=new ArrayList<String>();
	 
	public RecipeAtoE(WebDriver driver) {
		System.out.println("Hi");
		PageFactory.initElements(driver,this);
	}
	
	public void click_AtoZ_recipes()	{
		driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}
	
	public void read_LFV_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		Boolean sheetCheck2 = reader.isSheetExist("Final list for LCHFElimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
		System.out.println("Is the Datasheet exist 2? -  " + sheetCheck2);
		
		for (int i = 3; i <= 76; i++) {
			String testData = reader.getCellData("Final list for LFV Elimination ", 0, i);
			LFV_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
		for (int i = 3; i <= 92; i++) {
				String testData = reader.getCellData("Final list for LCHFElimination ", 0, i);
				LCH_EliminateItemList.add(testData.toLowerCase());
				//System.out.println(testData);
			}
	}

	public void jgetRecipieInfo() throws Exception {
		
		int exp=0;
		int v=1;
		int vh=1;
		int pageCount;
		
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a[1]"));
		int size=menuAtoZWebElements.size();
		//Read Elimination data from excel and store it into arraylist
		this.read_LFV_Elimination_Excel();
	

		System.out.println("There are "+size+" number of links ordered alphabetically.");
		File src = new File("C:\\Users\\Alice\\git\\team5_recipe_pickers\\src\\test\\resources\\Excel Data\\List of recipies.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet1 = workbook.createSheet("LFV");
		FileOutputStream fos = new FileOutputStream(src);
		sheet1.createRow(0);
        sheet1.getRow(0).createCell(0).setCellValue("RecipeId");
        sheet1.createRow(1);
        sheet1.getRow(1).createCell(0).setCellValue("Recipe Name");
        sheet1.createRow(2);
        sheet1.getRow(2).createCell(0).setCellValue("Cooking time");
        sheet1.createRow(3);
        sheet1.getRow(3).createCell(0).setCellValue("Servings");
        
        XSSFSheet LCH = workbook.createSheet("LCH");
		LCH.createRow(0);
        LCH.getRow(0).createCell(0).setCellValue("RecipeId");
        LCH.createRow(1);
        LCH.getRow(1).createCell(0).setCellValue("Recipe Name");
        LCH.createRow(2);
        LCH.getRow(2).createCell(0).setCellValue("Cooking time");
        LCH.createRow(3);
        LCH.getRow(3).createCell(0).setCellValue("Servings");

		for(int i=21; i<28; i++) 
		{
			//String menuLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a")).getAttribute("href");
			//System.out.println(menuLink);
			WebElement AlphabetLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			String alphabet=AlphabetLink.getText();
			
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			AlphabetLink.click();
			if(alphabet.equals("X")==false)
			 {
		
				try {
				List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
				driver.findElement(By.xpath("//div[@id='maincontent']/div/div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
				}
				catch (Exception E) {
					System.out.println("%%%%% Exception Alphabet %%%%%% " +exp++);
				}
			 }
			if(alphabet.equals("X")) {
				pageCount=0;
			}
		
			else 
			  {
				WebElement divElement = driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;']"));
				List<WebElement> anchorTags = divElement.findElements(By.tagName("a"));
				pageCount = anchorTags.size();
				System.out.println("Toal page count is: "+pageCount);
			  }
				
			for(int pg=1; pg<=pageCount; pg++) 
			 {
				try 
				 {
					WebElement current_pg=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='"+pg+"']"));
					current_pg.click();
					System.out.println("******  Alphabet is "+alphabet+"  **** Current page is: "+pg+"  *********");
				 }
				
				catch(Exception e) 
				{	
					System.out.println("%%%%% Exception Pages %%%%%% " +exp++);
//					workbook.write(fos);
//					fos.close();
//					workbook.close();
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

						try 
						 {
							Document doc=Jsoup.connect((String)eachRecipe).timeout(1000*100).get();
							
							//Fetching URL
							String stringurl=eachRecipe.toString();
							String id=stringurl.substring(stringurl.lastIndexOf("-")+1);
							String recipe_id=id.substring(0,id.length()-1);							
							//Fetching Name
							Elements nameEle=doc.selectXpath("//span[@id = 'ctl00_cntrightpanel_lblRecipeName' and @itemprop='name']");
							String name=nameEle.text();
							//Prep time
							Elements preptimeEle=doc.selectXpath("//time[@itemprop='prepTime']");
							String prepTime=preptimeEle.text();
							//cooking time
							Elements cookTimeEle=doc.selectXpath("//time[@itemprop='cookTime']");
							String cookTime = cookTimeEle.text();
							//No of servings
							Elements noOfServingsEle=doc.selectXpath("//span[@itemprop='recipeYield']");
							String Servings = noOfServingsEle.text();
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
							
							boolean validRecipeLCH = true;
							//Retrieve data from Elimination arraylist using for loop, 
							for(String eliminatedItem: LCH_EliminateItemList) 
							{								
								//Then compare each value with Ingredients.
								if(ingredientsValue.contains(eliminatedItem))
								{
									//System.out.println("Item invalid: " +eliminatedItem);
									validRecipeLCH = false;
									break;
								}									
							}
							

							if(validRecipe)
							{
								System.out.println("------ valid Recipes LFV "+v+"--------");
								System.out.println("Recipe URL : "+stringurl);
								System.out.println("Recipe_id: "+recipe_id);
								System.out.println("prep time is:  "+prepTime);
								System.out.println("cook time is : "+cookTime);
								System.out.println("servings : "+Servings);
								System.out.println("Tags : "+tags);
								System.out.println("Cuisine category : "+category);
								System.out.println("Recipe Description : "+desc);
								System.out.println("Preparation method : "+method);
								System.out.println("Nutrient values : "+nutritionValue);
								System.out.println("Ingredients : "+ingredientsValue);
			                    sheet1.getRow(0).createCell(v).setCellValue(recipe_id);
			                    sheet1.getRow(1).createCell(v).setCellValue(name);
			                    sheet1.getRow(2).createCell(v).setCellValue(cookTime);
			                    sheet1.getRow(3).createCell(v).setCellValue(ingredientsValue);
			                    v++;
							}
							
							if(validRecipeLCH)
							{
								System.out.println("------ valid Recipes LCH "+vh+"--------");
								System.out.println("Recipe URL : "+stringurl);
								System.out.println("Recipe_id: "+recipe_id);
								System.out.println("prep time is:  "+prepTime);
								System.out.println("cook time is : "+cookTime);
								System.out.println("servings : "+Servings);
								System.out.println("Tags : "+tags);
								System.out.println("Cuisine category : "+category);
								System.out.println("Recipe Description : "+desc);
								System.out.println("Preparation method : "+method);
								System.out.println("Nutrient values : "+nutritionValue);
								System.out.println("Ingredients : "+ingredientsValue);
			                    LCH.getRow(0).createCell(vh).setCellValue(recipe_id);
			                    LCH.getRow(1).createCell(vh).setCellValue(name);
			                    LCH.getRow(2).createCell(vh).setCellValue(cookTime);
			                    LCH.getRow(3).createCell(vh).setCellValue(ingredientsValue);
			                    vh++;
							}
			
						 } 
						
						catch (Exception e) {
							System.out.println("%%%%% Exception Recipie Fetch %%%%%% " +exp++);
						
						}//To click on each recipe
						
				}//End for	loop
					
			 }//End pagination
	
			
		}
		workbook.write(fos);
		fos.close();
		workbook.close();
		
		
	}//method ends here
	
   
	
	
}
