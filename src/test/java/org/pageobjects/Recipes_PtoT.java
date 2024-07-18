package org.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import utils.ExcelReaderCode;

public class Recipes_PtoT extends TestBase {

	List<String> LFV_EliminateItemList = new ArrayList<String>();
	List<String> LFV_AddItemList = new ArrayList<String>();
	List<String> LCHF_EliminateItemList = new ArrayList<String>();
	List<String> LCHF_AddItemList = new ArrayList<String>();
	List<String> cuisineDataList = new ArrayList<String>();


	public Recipes_PtoT(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void click_AtoZ_recipes() {
		driver.findElement(By.xpath("//a[@title='Recipea A to Z']")).click();
		System.out.println("A to Z is clicked...");
	}

	public void read_EliminationList_Excel() {

		ExcelReaderCode reader = new ExcelReaderCode(
				"./src/test/resources/CriteriaFiles/Ingredients-ScrapperHackathon.xlsx");

		Boolean lfvSheetCheck = reader.isSheetExist("Final list for LFV Elimination ");

		System.out.println("Is the LFV Elimination Datasheet exist? -  " + lfvSheetCheck);

		for (int i = 3; i <= 76; i++) {

			String testData = reader.getCellData("Final list for LFV Elimination ", 0, i);

			LFV_EliminateItemList.add(testData.toLowerCase());

			// System.out.println(testData);
		}

		for (int i = 3; i <= 90; i++) {

			String testData = reader.getCellData("Final list for LFV Elimination ", 1, i);

			LFV_AddItemList.add(testData.toLowerCase());

			// System.out.println(testData);
		}

		Boolean lchfSheetCheck = reader.isSheetExist("Final list for LCHFElimination ");

		System.out.println("Is the LCHF Elimination Datasheet exist? -  " + lchfSheetCheck);

		for (int i = 3; i <= 92; i++) {

			String testData = reader.getCellData("Final list for LCHFElimination ", 0, i);

			LCHF_EliminateItemList.add(testData.toLowerCase());

			// System.out.println(testData);

		}

		for (int i = 3; i <= 34; i++) {

			String data = reader.getCellData("Final list for LCHFElimination ", 1, i);

			LCHF_AddItemList.add(data.toLowerCase());

			// System.out.println(data);
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
		
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/Scrapped_Recipes/Scrapped_Recipes_PtoT.xlsx");
		
		Boolean sheetCheck = reader.isSheetExist(sheetName);
		
		System.out.println("Is the  test Datasheet exist? -  " + sheetCheck);
		
		String path = System.getProperty("user.dir")+"/src/test/resources/Scrapped_Recipes/Scrapped_Recipes_PtoT.xlsx";
		
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
	           
//	            for (Object obj : objectArr) { 
//	            	
//	            	XSSFCell cell = worksheet.getRow(rowid++).createCell(cellid);
//	            	
//	                cell.setCellValue((String)obj); 
//	            } 
	            for (Object obj : objectArr) {
	                
	                XSSFRow row = worksheet.getRow(rowid);
	                if (row == null) {
	                    row = worksheet.createRow(rowid);
	                }
	                
	                XSSFCell cell = row.createCell(cellid);
	                
	                if (obj instanceof StringBuilder) {
	                    cell.setCellValue(obj.toString());
	                } else {
	                    cell.setCellValue((String) obj);
	                }
	                rowid++;
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
	public void getRecipeInfo() throws IOException {
		Map<String, Object[]> recipes_LCHF_Elimination = new TreeMap<String, Object[]>();

		Map<String, Object[]> recipes_LFV_Elimination = new TreeMap<String, Object[]>();

		Map<String, Object[]> recipes_LCHF_Add = new TreeMap<String, Object[]>();

		Map<String, Object[]> recipes_LFV_Add = new TreeMap<String, Object[]>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Assume countanchors and alphabet are initialized
		// int countanchors = 2; // Example value
		// String alphabet = "P"; // Example value

		String count = "1";
		
		String counter = "1";
		int pagecounter = 0;
		int recipecount = 0;
		// alphabets for loop p to t
		for (int i = 16; i < 17; i++) {
			WebElement alphabetLink = driver.findElement(
					By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']//td[@id='ctl00_cntleftpanel_mnuAlphabetsn"
							+ i + "']"));
			String alphabet = alphabetLink.getText();
			System.out.println("Starts with alphabets" + alphabet);
			alphabetLink.click();

			// Locate the first div element with the specified style attribute
			WebElement divElement = driver
					.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;']"));

			// Find all anchor tags within the first div element
			List<WebElement> anchorTags = divElement.findElements(By.tagName("a"));

			// Get the count of anchor tags
			int countanchors = anchorTags.size();

			// Print the count
			System.out.println("Number of anchor tags within the first div: " + countanchors);
			for (int pg = 1; pg <= countanchors; pg++) {
				try {
					WebElement current_pg = wait.until(ExpectedConditions.elementToBeClickable(By
							.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='" + pg + "']")));
					current_pg.click();
					System.out.println(
							"******  Alphabet is " + alphabet + "  **** Current page is: " + pg + "  *********");
				} catch (StaleElementReferenceException e) {
					System.err.println("Stale element reference: " + e.getMessage());
					continue;
				}

				// Wait for the elements to be loaded
				List<WebElement> recipes_url = wait
						.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rcc_recipename")));
				System.out.println("Total recipe URLs on page " + pg + " is: " + recipes_url.size());
				ArrayList<String> links = new ArrayList<>();
				for (WebElement url : recipes_url) {
					String recipesLink = url.findElement(By.tagName("a")).getAttribute("href");
					if (recipesLink.isBlank())
						continue;
					links.add(recipesLink);
				}
				System.out.println("Links size for page " + pg + " is: " + links.size());

				// Using Jsoup for web scraping
				for (String eachRecipe : links) {
					try {
						Document doc = Jsoup.connect(eachRecipe).timeout(10000).get();

						// Recipe URL
						String stringurl = eachRecipe;
						String recipeURL=eachRecipe;
						System.out.println("Recipe URL: " + recipeURL);

						// Recipe ID
						String id = stringurl.substring(stringurl.lastIndexOf("-") + 1);
						String recipe_id = id.substring(0, id.length() - 1);
						System.out.println("Recipe ID: " + recipe_id);

						// Recipe name
						String recipeName = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']").text();
						System.out.println("Recipe name: " + recipeName);

						// Preparation time
						String prepTime = doc.selectXpath("//time[@itemprop='prepTime']").text();
						System.out.println("Preparation time: " + prepTime);

						// Cooking time
						String cookTime = doc.selectXpath("//time[@itemprop='cookTime']").text();
						System.out.println("Cook time: " + cookTime);

						// Number of servings
						String servings = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblServes']").text();
						String noOfServings = servings.substring(1, servings.length());
						System.out.println("Number of servings: " + noOfServings);

						// Recipe tags
						String tags = doc.selectXpath("//div[@id='recipe_tags']/a").text().toLowerCase();
						System.out.println("Recipe tags: " + tags);

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
						System.out.println("Recipe category"+recipe_Category);
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
							System.out.println("Food category"+food_Category);
						
							String cuisineCategory = "";
						
						//ArrayList<String> cuisineList = new ArrayList<String>(Arrays.asList("Gujarati", "Punjabi", "Rajasthani", "Maharashtrian", "South Indian", "Chinese", "Italian"));
						
						for(String cuisine : cuisineDataList) {		
							
							if(tags.contains(cuisine)) {
								
								cuisineCategory = cuisine;
								
								break;
								
							} else {
								
								cuisineCategory = "Not mentioned";
								
							}									
						}
						System.out.println("Cuisine category: " + cuisineCategory);

						// Recipe description
						String desc = doc.selectXpath("//div[@id='recipe_details_left']/section/p/span").text();
						System.out.println("Recipe description: " + desc);

						// Preparation method
						String method = doc.selectXpath("//div[@id='recipe_small_steps']/span").text();
						System.out.println("Recipe preparation method: " + method);

						// Nutrition value
						Elements nutValueEle = doc.selectXpath("//table[@id='rcpnutrients']/tbody/tr");
						StringBuilder nutritionValue = new StringBuilder();
						for (Element row : nutValueEle) {
							nutritionValue.append(row.select("td").text()).append(" ");
						}
						System.out.println("Nutrition value: " + nutritionValue.toString().trim());

						// Ingredients
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

						System.out.println("Ingredients: " + ingredientsValue);
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
						  
							  recipes_LFV_Elimination.put( counter , new Object[] { recipe_id, recipeName,
									  recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
									  noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
							  
							  for(String addItem : LFV_AddItemList) {
								  
								  if(ingredientsValue.contains(addItem)) {
							  
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
						  
						  		recipes_LCHF_Elimination.put( count , new Object[] { recipe_id, recipeName,
						  				recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
						  				noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
						  
						  		for(String addItem : LCHF_AddItemList) {
						  
						  			if(ingredientsValue.contains(addItem)) {
						  
						  				recipes_LCHF_Add.put( count , new Object[] { recipe_id, recipeName,
						  						recipe_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
						  						noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
						  
						  				break; 
						  				} 
						  			}
						  
						  		count = count + 1; 
						  }
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
					recipecount++;
					System.out.println("no of recipes in page" + pg + "is :" + recipecount);
				} // for loop for each recipe link
				pagecounter++;
				System.out.println("Total counter for page " + pg + " is = " + counter);
			} // pagination loop
			write_Excel(recipes_LFV_Elimination, "LFV_Elimination_PtoT");
			
			write_Excel(recipes_LFV_Add, "LFV_Add_PtoT");
			
			write_Excel(recipes_LCHF_Elimination, "LCHF_Elimination_PtoT");
			
			write_Excel(recipes_LCHF_Add, "LCHF_Add_PtoT");
			
		} // For P to T
	}// method

}
