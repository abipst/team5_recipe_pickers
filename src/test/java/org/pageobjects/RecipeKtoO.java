package org.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
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

import base.TestBase;
import utils.ExcelReaderCode;

public class RecipeKtoO  extends TestBase{

	List<String> LFV_EliminateItemList=new ArrayList<String>();
	List<String> LFV_AddItemList=new ArrayList<String>();
	
	List<String> LCHF_EliminateItemList = new ArrayList<String>();	
	List<String> LCHF_AddItemList = new ArrayList<String>();
	
	List<String> cuisineDataList = new ArrayList<String>();
	String rec_Category;
	String food_Category;
	String ingredient_List = "";
	
	
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
						
		//Read Elimination data from excel and store it into arraylist
		this.read_LFV_Elimination_Excel();
		this.read_LFV_Add_Excel();
		
		this.read_LCHF_Elimination_Excel();
		this.read_LCHF_Add_Excel();
		
		this.read_CuisineCategoryData_Excel();
		
		
		
		Map<String, Object[]> recipes_LCHF_Elimination = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LFV_Elimination = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LCHF_Add = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LFV_Add = new TreeMap<String, Object[]>();
		
		int size=menuAtoZWebElements.size();
		
		int LCHFCounter = 1;
		
		int LFVCounter = 1;
		
		System.out.println("There are "+size+" number of links ordered alphabetically.");
		
		//Recipes from K to O
		for(int i=12; i<13; i++) 
		{
			WebElement AlphabetLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			String alphabet=AlphabetLink.getText();
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			AlphabetLink.click();
			List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			if(alphabet.equals("O"))
			 {
				pageCount=14;
			 }
			else if(alphabet.equals("N"))
			 {
				pageCount=9;
			 }
			
			else 
			  {
				try {
					String s=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[15]")).getText();
					pageCount=Integer.parseInt(s);
					System.out.println("Toal page count is: "+pageCount);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			  }
				
			for(int pg=1; pg<pageCount; pg++) 
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
						
						try 
						 {
							Document doc=Jsoup.connect((String)eachRecipe).timeout(1000*100).get();
							String recipeURL = eachRecipe.toString();
							//System.out.println("Recipe URL : "+stringurl);
							String id = recipeURL.substring(recipeURL.lastIndexOf("-")+1);
							
							String recipe_id = id.substring(0,id.length()-1);
							
							//recipe name
							String recipeName = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']").text();
							
							// fetching recipe details
							//preparation time
							Elements preptimeEle = doc.selectXpath("//time[@itemprop='prepTime']");
							String prepTime = preptimeEle.text();
							
							//cooking time
							Elements cookTimeEle = doc.selectXpath("//time[@itemprop='cookTime']");
							String cookTime = cookTimeEle.text();
							
							//No of servings
							Elements noOfServingsEle = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblServes']");
							String Servings = noOfServingsEle.text();
							String noOfServings = Servings.substring(1, Servings.length());
							
							// fetching recipe tags
							Elements tagEle=doc.selectXpath("//div[@id='recipe_tags']/a");
							String tags=tagEle.text();
							
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
							
							ingredient_List="";
							 for(Element ingredient: ingredientsEle) {
							  ingredient_List = ingredient_List+","+ingredient.text().toLowerCase();
							 }
							 if(ingredient_List.isEmpty())
								 ingredient_List=ingredient_List;
							 else
								 ingredient_List=ingredient_List.substring(1);				
								
							
							
							
							boolean validLFVRecipe = true;
							
							
							//Retrieve data from Elimination arraylist using for loop, 
							for(String eliminatedItem: LFV_EliminateItemList) 
							{								
								//Then compare each value with Ingredients.
								if(ingredientsValue.contains(eliminatedItem))
								{
									//System.out.println("Item invalid: " +eliminatedItem);
									validLFVRecipe = false;
									break;
								}									
							}
							
							
							if(validLFVRecipe) 
							{
								  recipes_LFV_Elimination.put( Integer.toString(LFVCounter) , new Object[] { recipe_id, recipeName,
										  rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
										  noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });

								  
								//Retrieve data from Add arraylist using for loop, 
								for(String addItem: LFV_AddItemList) 
								{								
									//Then compare each value with Ingredients.
									if(ingredientsValue.contains(addItem))
									{
											System.out.println("LFV Add Item valid: " +addItem);
											
											recipes_LFV_Add.put( Integer.toString(LFVCounter) , new Object[] { recipe_id, recipeName,
													  rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
													  noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
											break;
									}										
								}
																							
								LFVCounter = LFVCounter + 1;
							}
							
							// Iterate LCHF Elimination array list using for loop and compare each value with Ingredients to filter recipes
							boolean validLCHFRecipe = true;
							  for(String eliminatedItem : LCHF_EliminateItemList) {
							  
								  if(ingredientsValue.contains(eliminatedItem)) {
							  
									  validLCHFRecipe = false;
							  
									  break; 
									  } 
								  }
							  
							  	if(validLCHFRecipe) {
							  
							  		recipes_LCHF_Elimination.put( Integer.toString(LCHFCounter) , new Object[] { recipe_id, recipeName,
							  				rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
							  				noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
							  
							  		for(String addItem : LCHF_AddItemList) {
							  			
							  			if(ingredientsValue.contains(addItem)) {
							  				System.out.println("LCHF Add Item valid: " +addItem);
							  				recipes_LCHF_Add.put( Integer.toString(LCHFCounter) , new Object[] { recipe_id, recipeName,
							  						rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
							  						noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL });
							  
							  				break; 
							  				} 
							  			}
							  
							  		LCHFCounter  = LCHFCounter  + 1; 
							  }
							
						 } 
						
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//To click on each recipe
					//}//End If 
						
				}//End for				
			 }//End pagination
			System.out.println("Total Valid LFV Recipe(Elimination Check) = " + recipes_LFV_Elimination.size());
			System.out.println("Total Valid LFV Recipe(Add Check) = " + recipes_LFV_Add.size());
			
			System.out.println("Total Valid LCHF Recipe(Elimination Check) = " + recipes_LCHF_Elimination.size());
			System.out.println("Total Valid LCHF Recipe(Add Check) = " + recipes_LCHF_Add.size());
			
			write_Excel(recipes_LFV_Elimination, "LFV_Elimination_KtoO");
			
			write_Excel(recipes_LFV_Add, "LFV_Add_KtoO");
			
			write_Excel(recipes_LCHF_Elimination, "LCHF_Elimination_KtoO");
			
			write_Excel(recipes_LCHF_Add, "LCHF_Add_KtoO");
			
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
	
	public void read_LCHF_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LCHFElimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 92; i++) {
			String testData = reader.getCellData("Final list for LCHFElimination ", 0, i);
			LCHF_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_LCHF_Add_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LCHFElimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 34; i++) {
			String testData = reader.getCellData("Final list for LCHFElimination ", 1, i);
			LCHF_AddItemList.add(testData.toLowerCase());
			
		}
	}
	
	public void read_CuisineCategoryData_Excel() {
		
		ExcelReaderCode FoodCategoryreader = new ExcelReaderCode("./src/test/resources/Recipe-filters-ScrapperHackathon.xlsx");
		Boolean sheetCheck1 = FoodCategoryreader.isSheetExist("Food Category");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck1);
			for (int f = 2; f <= 32; f++) {
			String cuisineData = FoodCategoryreader.getCellData("Food Category", 1, f);
			cuisineDataList.add(cuisineData);
			
			}
	}
	
	public void write_Excel(Map<String, Object[]> recipeData, String sheetName) throws IOException {
		
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/Scrapped_Recipes_KtoO.xlsx");
		
		Boolean sheetCheck = reader.isSheetExist(sheetName);
		
		System.out.println("Is the  test Datasheet exist? -  " + sheetCheck);
		
		String path = System.getProperty("user.dir")+"/src/test/resources/Scrapped_Recipes_KtoO.xlsx";
		
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
	

}
						


						
					



