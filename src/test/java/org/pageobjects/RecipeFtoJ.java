package org.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;
import pages.HomePage;
import utils.ExcelReaderCode;

public class RecipeFtoJ extends TestBase{

	List<String> LFV_EliminateItemList = new ArrayList<String>();
	
	List<String> LFV_AddItemList = new ArrayList<String>();
	
	List<String> LCHF_EliminateItemList = new ArrayList<String>();
	
	List<String> LCHF_AddItemList = new ArrayList<String>();
	
	List<String> cuisineDataList = new ArrayList<String>();

	
	public RecipeFtoJ (WebDriver driver) {
	  
	  PageFactory.initElements(driver,this); 
	  
	  }
	
	public void read_Excel() {
		
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/CriteriaFiles/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		
		Boolean lfvSheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		
		System.out.println("Is the LFV Elimination Datasheet exist? -  " + lfvSheetCheck);
		
			for (int i = 3; i <= 76; i++) {
				
			String testData = reader.getCellData("Final list for LFV Elimination ", 0, i);
			
			LFV_EliminateItemList.add(testData.toLowerCase());
			
			}
			
			for (int i = 3; i <= 90; i++) {
				
				String testData = reader.getCellData("Final list for LFV Elimination ", 1, i);
				
				LFV_AddItemList.add(testData.toLowerCase());
			}
			
		Boolean lchfSheetCheck = reader.isSheetExist("Final list for LCHFElimination ");
			
		System.out.println("Is the LCHF Elimination Datasheet exist? -  " + lchfSheetCheck);
			
			for (int i = 3; i <= 92; i++) {
					
				String testData = reader.getCellData("Final list for LCHFElimination ", 0, i);
				
				LCHF_EliminateItemList.add(testData.toLowerCase());
				
			}
		
			for (int i = 3; i <= 34; i++) {
				
				String data = reader.getCellData("Final list for LCHFElimination ", 1, i);
				
				LCHF_AddItemList.add(data.toLowerCase());
				
			}
	}
	
	public void read_CuisineCategoryData_Excel() {
		
		ExcelReaderCode FoodCategoryreader = new ExcelReaderCode("./src/test/resources/CriteriaFiles/Recipe-filters-ScrapperHackathon.xlsx");
		
		Boolean sheetCheck1 = FoodCategoryreader.isSheetExist("Food Category");
		
		System.out.println("Is Food Category Datasheet exist? -  " + sheetCheck1);
		
			for (int f = 2; f <= 32; f++) {
				
			String cuisineData = FoodCategoryreader.getCellData("Food Category", 1, f);
			
			cuisineDataList.add(cuisineData);
			
			}
	}
	
	public void write_Excel(Map<String, Object[]> recipeData, String sheetName) throws IOException {
		
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/Scrapped_Recipes/Scrapped_Recipes_FtoJ.xlsx");
		
		Boolean sheetCheck = reader.isSheetExist(sheetName);
		
		System.out.println("Is the  test Datasheet exist? -  " + sheetCheck);
		
		String path = System.getProperty("user.dir")+"/src/test/resources/Scrapped_Recipes/Scrapped_Recipes_FtoJ.xlsx";
		
		File Excelfile = new File(path);
		
		FileInputStream Fis = new FileInputStream(Excelfile);
		
		XSSFWorkbook workbook = new XSSFWorkbook(Fis);
		
		XSSFSheet worksheet = workbook.getSheet(sheetName);
		
	    Set<String> keyid = recipeData.keySet(); 
	       
	        // writing the data into the mentioned worksheet
	  
	        int cellid = 1;
	        
	        for (String key : keyid) { 
	  		  
	        	int rowid =0;
	        	
	            Object[] objectArr = recipeData.get(key); 
	           
	            for (Object obj : objectArr) { 
	            	
	            	XSSFCell cell = worksheet.getRow(rowid++).createCell(cellid);
	            	
	                cell.setCellValue((String)obj); 
	            } 
	            cellid++;
	        } 
	  
		FileOutputStream Fos=null;
		
		try {
			 Fos = new FileOutputStream(Excelfile);
			 
			 workbook.write(Fos);
			 
			 workbook.close();
			 
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		finally {
			
			Fos.close();
		}
	}
	 
	public void click_AtoZ_recipes() {
		
		WebElement recipeAtoZOption = driver.findElement(By.linkText("Recipe A To Z"));
		
		recipeAtoZOption.click();
		
		System.out.println("A to Z is clicked..");
	}
	
	public void getRecipeInfo() throws InterruptedException, IOException {
		
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']//a"));
	
		int size = menuAtoZWebElements.size();
		
		System.out.println("There are "+size+" number of links ordered alphabetically.");
		
		Map<String, Object[]> recipes_LCHF_Elimination = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LFV_Elimination = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LCHF_Add = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LFV_Add = new TreeMap<String, Object[]>();
		
		String count = "1";
		
		String counter = "1";
		
		// fetching recipes from alphabet F to J
		
		for( int i = 6; i <= 10; i++ ) {
			
			WebElement alphabetLink = driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']//td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			
			String alphabet = alphabetLink.getText();
			
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			
			alphabetLink.click();
			
			// pagination logic
				
			List<WebElement> pageNumbers = driver.findElements(By.xpath("//div[contains(text(),'Goto Page:')][1]//a"));
			
			String pagecount = pageNumbers.getLast().getText();
			
			int totalPageCount = Integer.parseInt(pagecount);  
			
			System.out.println("Total page count is: "+totalPageCount);
			
			for(int pageNumber = 1; pageNumber <= totalPageCount; pageNumber++) {
						
				try {
							
					WebElement currentPage = driver.findElement(By.xpath("//div[contains(text(),'Goto Page:')][1]//a[text()='"+pageNumber+"']"));
					
					currentPage.click();
					
					System.out.println("******  Alphabet is "+alphabet+"  **** Current page is: "+pageNumber+"  *********");
							
				} catch (StaleElementReferenceException e) {
					
					e.printStackTrace();
				}
		
				// fetching all recipe urls and saving them in a list
				
				List<WebElement> recipes_url = driver.findElements(By.className("rcc_recipename"));
				
				ArrayList<String> links = new ArrayList<>();
				
				for(WebElement url: recipes_url) {
					
					String recipesLink = url.findElement(By.tagName("a")).getAttribute("href");
					
					if(recipesLink.isBlank())
						
						continue;
					else
					
						links.add(recipesLink);
				}
				
				// using jsoup for web scraping
					
					for (Object eachRecipe:links) {
						
						try {
							
							Document doc = Jsoup.connect((String)eachRecipe).timeout(1000*100).get();
							
							// fetching recipe name
							
							Elements name = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
							
							String recipeName = name.text();											
							
							// fetching recipe url

							String recipeURL = eachRecipe.toString();
							
							// fetching recipe id

							String id = recipeURL.substring(recipeURL.lastIndexOf("-")+1);

							String recipe_id = id.substring(0,id.length()-1);

							// fetching prep time
							
							Elements preptimeEle = doc.selectXpath("//time[@itemprop='prepTime']");
							
							String prepTime = preptimeEle.text();
			
							//fetching cooking time
							
							Elements cookTimeEle = doc.selectXpath("//time[@itemprop='cookTime']");
							
							String cookTime = cookTimeEle.text();

							// fetching No of servings
							
							Elements noOfServingsEle = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblServes']");
							
							String servings = noOfServingsEle.text();
							
							String noOfServings = servings.substring(1, servings.length());
							
							// fetching recipe tags
							
							Elements tagEle = doc.selectXpath("//div[@id='recipe_tags']/a");
							
							String tags = tagEle.text();
							
							// fetching Recipe category
							
							String tags_lowerCase = tags.toLowerCase();
							
							String recipe_Category = "";
							
								if (tags_lowerCase.contains("breakfast")) {
									
									recipe_Category = "Breakfast";
									break;
									
								} else if (tags_lowerCase.contains("dinner")) {
									
									recipe_Category = "Dinner";
									break;
									
								} else if (tags_lowerCase.contains("snack")) {
									
									recipe_Category = "Snacks";
									break;
									
								} else if (tags_lowerCase.contains("lunch")) {
									
									recipe_Category = "Lunch";
									break;
								
								}else {
									recipe_Category = "Not mentioned";
								}
							
							// fetching Food Category
							String food_Category = "";
							
								if ((tags_lowerCase.contains("veg")) && (!tags_lowerCase.contains("non veg")||!tags_lowerCase.contains("non-veg"))) {
									
									food_Category = "Vegetarian";
									break;
									
								} else if (tags_lowerCase.contains("non veg")||tags_lowerCase.contains("non-veg")) {
									
									food_Category = "Non-Vegetarian";
									break;
									
								} else if (tags_lowerCase.contains("egg")) {
									
									food_Category = "Eggitarian";
									break;
									
								} else if (tags_lowerCase.contains("jain")) {
									
									food_Category = "Jain";
									break;
									
								}else if (tags_lowerCase.contains("vegan")) {
									
									food_Category = "Vegan";
									break;
									
								}	
								else {
									food_Category = "Not mentioned";
								}
								
							String cuisineCategory = "";
							
							for(String cuisine : cuisineDataList) {		
								
								if(tags.contains(cuisine)) {
									
									cuisineCategory = cuisine;
									
									break;
									
								} else {
									
									cuisineCategory = "Not mentioned";
									
								}									
							}

							// fetching Recipe Description
							 
							Elements recDesEle = doc.selectXpath("//p[@id='recipe_description']/span");
							 
							String desc = recDesEle.text();
							 
							// fetching Preparation method
							 
							Elements preMethodEle = doc.selectXpath("//div[@id='recipe_small_steps']/span");
							 
							String method=preMethodEle.text();
							 
							Elements nutValueEle = doc.selectXpath("//table[@id='rcpnutrients']/tbody/tr");
							 
							String nutritionValue="";
							 
							for (Element row : nutValueEle) {
								 
								 Elements cols = row.select("td");
								 
								 nutritionValue = nutritionValue + cols.text();
							}
							
								if(nutritionValue.isEmpty())
								 
									nutritionValue = "Not mentioned";
							 
							// fetching Ingredients
							 
							Elements ingredientsEle = doc.selectXpath("//div[@id='rcpinglist']/div//a");
							 
							String ingredientsValue = ingredientsEle.text();
							
							String ingredient_List="";
							
							 	for(Element ingredient: ingredientsEle) {
								 
							 		ingredient_List = ingredient_List+","+ingredient.text().toLowerCase();
							  
							 	}
							 
							 	if(ingredient_List.isEmpty())
								 
							 		ingredient_List = "Not mentioned";
							 
							 	else
							 		ingredient_List = ingredient_List.substring(1);
							 
							boolean validLFVRecipe = true;
							 
							boolean validLCHFRecipe = true;
							
							// Iterate LFV Elimination array list using for loop and compare each value with Ingredients to filter recipes
						
							for(String eliminatedItem : LFV_EliminateItemList) {
							  
							  if(ingredientsValue.contains(eliminatedItem)) {
							  
								  validLFVRecipe = false;
							  
								  break; 
								  
								  } 
							  }
							  
							  if(validLFVRecipe) {
								  
								  // storing recipe data for LFV after elimination
							  
								  recipes_LFV_Elimination.put( counter , new Object[] { recipe_id, recipeName,
										  recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
										  noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
								  
								  for(String addItem : LFV_AddItemList) {
									  
									  if(ingredientsValue.contains(addItem)) {
										  
										// storing recipe data for LFV after checking "add" ingredients
								  
										  	recipes_LFV_Add.put( counter , new Object[] { recipe_id, recipeName,
										  			recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
										  			noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
								  
										  	break; 
									  } 
								  }
							  
								  counter = counter + 1;
							  
							  }
							
							// Iterate LCHF Elimination array list using for loop and compare each value with Ingredients to filter recipes
							
							  for(String eliminatedItem : LCHF_EliminateItemList) {
							  
								  if(ingredientsValue.contains(eliminatedItem)) {
							  
									  validLCHFRecipe = false;
							  
									  break; 
									  } 
								  }
							  
							  	if(validLCHFRecipe) {
							  		
							  	// storing recipe data for LCHF after elimination
							  
							  		recipes_LCHF_Elimination.put( count , new Object[] { recipe_id, recipeName,
							  				recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
							  				noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
							  
							  		for(String addItem : LCHF_AddItemList) {
							  
							  			if(ingredientsValue.contains(addItem)) {
							  				
							  			// storing recipe data for LCHF after checking "add" ingredients
							  
							  				recipes_LCHF_Add.put( count , new Object[] { recipe_id, recipeName,
							  						recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
							  						noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
							  
							  				break; 
							  				} 
							  			}
							  
							  		count = count + 1; 
							  }
						
						} catch (IOException e) {
							
							e.printStackTrace();
							
						}//To click on each recipe
				}
			}
			
			write_Excel(recipes_LFV_Elimination, "LFV_Elimination_FtoJ");
			
			write_Excel(recipes_LFV_Add, "LFV_Add_FtoJ");
			
			write_Excel(recipes_LCHF_Elimination, "LCHF_Elimination_FtoJ");
			
			write_Excel(recipes_LCHF_Add, "LCHF_Add_FtoJ");
			
			
		}
	}
}
