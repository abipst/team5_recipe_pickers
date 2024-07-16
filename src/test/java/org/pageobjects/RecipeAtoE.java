package org.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import javax.print.Doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import utils.ExcelReaderCode;

public class RecipeAtoE  extends TestBase{
	int pageCount;
	List<String> LFV_EliminateItemList = new ArrayList<String>();
	List<String> cuisineDataList = new ArrayList<String>();
	String ingredient_List = "";
	String food_Category;
	String rec_Category;
	int rowcount;
	
	public RecipeAtoE(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	public void read_LFV_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/Ingredients  -ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 76; i++) {
			String testData = reader.getCellData("Final list for LFV Elimination ", 0, i);
			LFV_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_CuisineCategoryData_Excel() {
		
		ExcelReaderCode FoodCategoryreader = new ExcelReaderCode("./src/test/resources/Recipe-filters-ScrapperHackathon.xlsx");
		Boolean sheetCheck1 = FoodCategoryreader.isSheetExist("Food Category");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck1);
			for (int f = 2; f <= 32; f++) {
			String cuisineData = FoodCategoryreader.getCellData("Food Category", 1, f);
			cuisineDataList.add(cuisineData);
			//System.out.println(cuisineData);
			}
	}
	
	public void read_output_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./target/Recipes-A-to-E.xlsx");
		rowcount=reader.getRowCount("LFV_Elimination_recipes");
		
	}
	
	public void click_AtoZ_recipes()
	{
		driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}
	
	public void getRecipeInfo() throws IOException {
		
		
		 XSSFWorkbook workbook = new XSSFWorkbook();
		 XSSFSheet sheet = workbook.createSheet("LFV_Elimination_recipes"); // Create a new sheet
		 String[] headers = new String[] { "Recipe ID", "Recipe Name", "Recipe Category(Breakfast/lunch/snack/dinner)","Ingredients","Preparation Time",
				 							"Cooking Time","Tag","No of servings","Cuisine category","Recipe Description","Preparation method",
				 							"Nutrient values","Recipe URL"};
 
		 for (int rn=0; rn<headers.length; rn++) {
			 XSSFRow r = sheet.createRow(rn);
		    r.createCell(0).setCellValue(headers[rn]);
		 }
		 int noOfColumns = 0;//sheet.getRow(0).getLastCellNum();
		 
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a[1]"));
		
		int size=menuAtoZWebElements.size();
		System.out.println("There are "+size+" number of links ordered alphabetically.");
		for(int i=1; i<2; i++) {
			//String menuLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a")).getAttribute("href");
			//System.out.println(menuLink);
			WebElement AlphabetLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			String alphabet=AlphabetLink.getText();
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			AlphabetLink.click();
			/*
			// Locate the first div element with the specified style attribute
            WebElement divElement = driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;']"));
            // Find all anchor tags within the first div element
            List<WebElement> anchorTags = divElement.findElements(By.tagName("a"));
            // Get the count of anchor tags
            int pageCount = anchorTags.size();
            // Print the count
            System.out.println("Number of anchor tags within the first div: " + pageCount);
			
			*/
			List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			if(alphabet.equals("E")) {
				pageCount=9;
			}else {
			String s=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[15]")).getText();
			pageCount=Integer.parseInt(s);  
			System.out.println("Toal page count is: "+pageCount);
			}
			
			
			
			for(int pg=1; pg<=1/*pageCount*/; pg++) {
				try {
				WebElement current_pg=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='"+pg+"']"));
				current_pg.click();
				System.out.println("******  Alphabet is "+alphabet+"  **** Current page is: "+pg+"  *********");
				}
				catch(StaleElementReferenceException e) {				
				}
				
								
				List<WebElement> recipes_url=driver.findElements(By.className("rcc_recipename"));
				int no_of_cards=recipes_url.size();
				ArrayList<String> links=new ArrayList<>(30);
				for(WebElement url: recipes_url) {
					String recipesLink=url.findElement(By.tagName("a")).getAttribute("href");
					if(recipesLink.isBlank())
						continue;
					else
					links.add(recipesLink);
				}
					
					int counter=1;
					for(Object eachRecipe:links)
					{
						System.out.println(" ");
						//System.out.println("----------------------------Recipe Counter: "+counter+"-------------------------------");
						
						
						try {
							Document doc=Jsoup.connect((String)eachRecipe).timeout(1000*100).get();
							String stringurl=eachRecipe.toString();
							//System.out.println("Recipe URL : "+stringurl);
							String id=stringurl.substring(stringurl.lastIndexOf("-")+1);
							String recipe_id=id.substring(0,id.length()-1);
							//System.out.println("Recipe_id: "+recipe_id);
							
							//recipe name
							String recipe_name=doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']").text();
							
							// fetching recipe details
							//preparation time
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
							String tags = tagEle.text().toLowerCase();
							
							//Recipe category
								rec_Category = "";
								if (tags.contains("breakfast")) {
									rec_Category = "Breakfast";
									break;
								} else if (tags.contains("dinner")) {
									rec_Category = "Dinner";
									break;
								} else if (tags.contains("snack")) {
									rec_Category = "Snacks";
									break;
								}
								else if (tags.contains("lunch")) {
									rec_Category = "Lunch";
									break;
								}
								else
								rec_Category = "Not mentioned";
							
							
							//Food Category
								food_Category = "";
								if ((tags.contains("veg")) && (!tags.contains("non veg")||!tags.contains("non-veg"))) {
									food_Category = "Vegetarian";
									break;
								} else if (tags.contains("non veg")||tags.contains("non-veg")) {
									food_Category = "Non-Vegetarian";
									break;
								} else if (tags.contains("egg")) {
									food_Category = "Eggitarian";
									break;
								} else if (tags.contains("jain")) {
									food_Category = "Jain";
									break;
								}else if (tags.contains("vegan")) {
									food_Category = "Vegan";
									break;
								}	
								else
								food_Category = "Not mentioned";
							

							
							// fetching Cuisine category
							
							String cuisineCategory = "";
							for(String cuisine : cuisineDataList) {		
							if(tags.contains(cuisine)) {
								cuisineCategory = cuisine;
									break;
									
								} else {
									cuisineCategory = "not available";
								}									
							}

							 // fetching Recipe Description
							 Elements recDesEle = doc.selectXpath("//div[@id='recipe_details_left']/section/p/span");
							 String desc=recDesEle.text();
							 // fetching Preparation method
							 Elements preMehodEle = doc.selectXpath("//div[@id='recipe_small_steps']/span");
							 String method=preMehodEle.text();
							 
							// fetching nutrition values
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
							ingredient_List="";
							 for(Element ingredient: ingredientsEle) {
							  ingredient_List = ingredient_List+","+ingredient.text().toLowerCase();
							 }
							 if(ingredient_List.isEmpty())
								 ingredient_List=ingredient_List;
							 else
								 ingredient_List=ingredient_List.substring(1);
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
							String[] recipe_details = new String[] {recipe_id, recipe_name,rec_Category,food_Category,ingredient_List,
									prepTime,cookTime, tags, noOfServings, cuisineCategory, desc,method, nutritionValue,stringurl };

							
							if(validRecipe)
							{
								noOfColumns++;
								 for (int rn=0; rn<recipe_details.length; rn++) {
									 XSSFRow r = sheet.createRow(rn);
								    r.createCell(noOfColumns).setCellValue(recipe_details[rn]);
								 }
								    /*r.createCell(noOfColumns).setCellValue(recipe_name);
								    r.createCell(noOfColumns).setCellValue(rec_Category);
								    r.createCell(noOfColumns).setCellValue(food_Category);
								    r.createCell(noOfColumns).setCellValue(ingredient_List);
								    r.createCell(noOfColumns).setCellValue(prepTime);
								    r.createCell(noOfColumns).setCellValue(cookTime);
								    r.createCell(noOfColumns).setCellValue(tags);
								    r.createCell(noOfColumns).setCellValue(noOfServings);
								    r.createCell(noOfColumns).setCellValue(cuisineCategory);
								    r.createCell(noOfColumns).setCellValue(desc);
								    r.createCell(noOfColumns).setCellValue(method);
								    r.createCell(noOfColumns).setCellValue(nutritionValue);
								    r.createCell(noOfColumns).setCellValue(stringurl);*/
								    
							
						        /*
								System.out.println("------ valid Recipes--------");
								System.out.println("Recipe URL : "+stringurl);
								System.out.println("Recipe_id: "+recipe_id);
								System.out.println("Recipe category : "+rec_Category);
								System.out.println("Food category : "+food_Category);
								System.out.println("prep time is:  "+prepTime);
								System.out.println("cook time is : "+cookTime);
								System.out.println("servings : "+noOfServings);
								System.out.println("Tags : "+recipeTags);
								System.out.println("Cuisine category : "+cuisineCategory);
								System.out.println("Recipe Description : "+desc);
								System.out.println("Preparation method : "+method);
								System.out.println("Nutrient values : "+nutritionValue);
								 if(ingredient_List.isEmpty())
									 System.out.println("Ingredients : "+ingredient_List);
								 else
								 System.out.println("Ingredients : "+ingredient_List.substring(1));
								 */
								 FileOutputStream excelFile = new FileOutputStream(new File("./target/Recipes-A-to-E.xlsx"));
								 workbook.write(excelFile);
								 counter= counter + 1;
							}	
							/*
							 ingredient_List="";
							 for(Element ingredient: ingredientsEle) {
							  ingredient_List = ingredient_List+","+ingredient.text().toLowerCase();
							 }
							
							System.out.println("prep time is:  "+prepTime);
							System.out.println("cook time is : "+cookTime);
							System.out.println("servings : "+noOfServings);
							System.out.println("Tags are "+recipeTags);
							System.out.println("Cuisine category : "+category);
							 System.out.println("Recipe Description : "+desc);
							 System.out.println("Preparation method : "+method);
							 System.out.println("Nutrient values : "+nutritionValue);
							 if(ingredient_List.isEmpty())
								 System.out.println("Ingredients : "+ingredient_List);
							 else
							 System.out.println("Ingredients : "+ingredient_List.substring(1));
							 */
							
									//counter++;
							
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//To click on each recipe
						
						
						
						
					}
					
				
				
//-------------------------------------------------------------------------------------------------------------------------------------------
				/*
				HashMap<String,String> recipeDetails=new HashMap<String, String>();
				List<WebElement> recipeCards = driver.findElements(By.className("rcc_recipecard"));
				
				for (WebElement recipe : recipeCards) {
					
					// fetching recipe name and url
			
					WebElement recipeLink = recipe.findElement(By.xpath("//div[@class='rcc_rcpcore']/span/a"));
					String recipe_name=recipeLink.getText();
					System.out.println("Recipe name is "+ recipe_name);
					
					String recipe_link=recipeLink.getAttribute("href");
					System.out.println("Recipe url is "+ recipe_link);
					
					// fetching recipe id
					
					String id = recipe.getAttribute("id");
					
					String recipeID = id.substring(3);
					
					System.out.println("Recipe id is "+recipeID);
					
					recipeDetails.put(recipe_name, id);
					
					//recipe_names_links.get(j).click();
					
					//String recipe_Id=driver.getCurrentUrl();
					
				//	System.out.println("Recipe Id: "+recipe_Id.substring(recipe_Id.lastIndexOf("-")+1));
				//	driver.navigate().back();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				*/
//-------------------------------------------------------------------------------------------------------------------------------------
				
				
			}//pagination loop
		}//alphabet menu loop
		
		workbook.close();
		
	}//method ends here
	
	

}//end of class
