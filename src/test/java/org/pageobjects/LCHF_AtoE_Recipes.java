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

import base.TestBase;
import utils.ExcelReaderCode;

public class LCHF_AtoE_Recipes extends TestBase {
	int pageCount;
	List<String> LFV_EliminateItemList = new ArrayList<String>();
	List<String> LFV_AddItemList=new ArrayList<String>();
	List<String> cuisineDataList = new ArrayList<String>();
	String ingredient_List = "";
	String food_Category;
	String rec_Category;
		
	public LCHF_AtoE_Recipes(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	public void read_LCHF_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/Ingredients  -ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 92; i++) {
			String testData = reader.getCellData("Final list for LCHFElimination ", 0, i);
			LFV_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_LCHF_Add_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/Ingredients  -ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 34; i++) {
			String testData = reader.getCellData("Final list for LCHFElimination ", 1, i);
			LFV_AddItemList.add(testData.toLowerCase());
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
	
	public void click_AtoZ_recipes()
	{
		driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}
	
	public void getRecipeInfo() throws Exception {
		int AddColumn=1;
		int column=1;
		File src = new File("./src/test/resources/Scrapped_Recipes/ScrappedLCHF_Recipes_AtoE.xls");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet LCHFsheet = workbook.createSheet("LCHF_Elimination_recipes");
		FileOutputStream fos = new FileOutputStream(src);
		LCHFsheet.createRow(0);
		LCHFsheet.getRow(0).createCell(0).setCellValue("Recipe ID");
		LCHFsheet.createRow(1);
        LCHFsheet.getRow(1).createCell(0).setCellValue("Recipe Name");
        LCHFsheet.createRow(2);
        LCHFsheet.getRow(2).createCell(0).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
        LCHFsheet.createRow(3);
        LCHFsheet.getRow(3).createCell(0).setCellValue("Food Category");
        LCHFsheet.createRow(4);
        LCHFsheet.getRow(4).createCell(0).setCellValue("Ingredients");
        LCHFsheet.createRow(5);
        LCHFsheet.getRow(5).createCell(0).setCellValue("Preparation Time");
        LCHFsheet.createRow(6);
        LCHFsheet.getRow(6).createCell(0).setCellValue("Cooking Time");
        LCHFsheet.createRow(7);
        LCHFsheet.getRow(7).createCell(0).setCellValue("Tag");
        LCHFsheet.createRow(8);
        LCHFsheet.getRow(8).createCell(0).setCellValue("No of servings");
        LCHFsheet.createRow(9);
        LCHFsheet.getRow(9).createCell(0).setCellValue("Cuisine category");
        LCHFsheet.createRow(10);
        LCHFsheet.getRow(10).createCell(0).setCellValue("Recipe Description");
        LCHFsheet.createRow(11);
        LCHFsheet.getRow(11).createCell(0).setCellValue("Preparation method");
        LCHFsheet.createRow(12);
        LCHFsheet.getRow(12).createCell(0).setCellValue("Nutrient values");
        LCHFsheet.createRow(13);
        LCHFsheet.getRow(13).createCell(0).setCellValue("Recipe URL");
        
        //Creating a new sheet to write Add item list
        XSSFSheet LCHFAddsheet = workbook.createSheet("LCHF_To_Add_recipes");
        LCHFAddsheet.createRow(0);
        LCHFAddsheet.getRow(0).createCell(0).setCellValue("Recipe ID");
        LCHFAddsheet.createRow(1);
        LCHFAddsheet.getRow(1).createCell(0).setCellValue("Recipe Name");
        LCHFAddsheet.createRow(2);
        LCHFAddsheet.getRow(2).createCell(0).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
        LCHFAddsheet.createRow(3);
        LCHFAddsheet.getRow(3).createCell(0).setCellValue("Food Category");
        LCHFAddsheet.createRow(4);
        LCHFAddsheet.getRow(4).createCell(0).setCellValue("Ingredients");
        LCHFAddsheet.createRow(5);
        LCHFAddsheet.getRow(5).createCell(0).setCellValue("Preparation Time");
        LCHFAddsheet.createRow(6);
        LCHFAddsheet.getRow(6).createCell(0).setCellValue("Cooking Time");
        LCHFAddsheet.createRow(7);
        LCHFAddsheet.getRow(7).createCell(0).setCellValue("Tag");
        LCHFAddsheet.createRow(8);
        LCHFAddsheet.getRow(8).createCell(0).setCellValue("No of servings");
        LCHFAddsheet.createRow(9);
        LCHFAddsheet.getRow(9).createCell(0).setCellValue("Cuisine category");
        LCHFAddsheet.createRow(10);
        LCHFAddsheet.getRow(10).createCell(0).setCellValue("Recipe Description");
        LCHFAddsheet.createRow(11);
        LCHFAddsheet.getRow(11).createCell(0).setCellValue("Preparation method");
        LCHFAddsheet.createRow(12);
        LCHFAddsheet.getRow(12).createCell(0).setCellValue("Nutrient values");
        LCHFAddsheet.createRow(13);
        LCHFAddsheet.getRow(13).createCell(0).setCellValue("Recipe URL");
        
        
      //Read Elimination data from excel and store it into arraylist
      		this.read_LCHF_Elimination_Excel();
      		
      		//Read Add data from excel and store it into arraylist
      		this.read_LCHF_Add_Excel();
		 //Getting all the alphabets list
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a[1]"));
		
		int size=menuAtoZWebElements.size();
		System.out.println("There are "+size+" number of links ordered alphabetically.");
		for(int i=1; i<6; i++) {
			
			WebElement AlphabetLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			String alphabet=AlphabetLink.getText();
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			AlphabetLink.click();
			List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			if(alphabet.equals("E")) {
				pageCount=9;
			}else {
			String s=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[15]")).getText();
			pageCount=Integer.parseInt(s);  
			System.out.println("Toal page count is: "+pageCount);
			}
			
			
			
			for(int pg=1; pg<=pageCount; pg++) {
				try {
				WebElement current_pg=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='"+pg+"']"));
				current_pg.click();
				System.out.println("******  Alphabet is "+alphabet+"  **** Current page is: "+pg+"  *********");
				}
				catch(StaleElementReferenceException e) {				
				}
				
				List<WebElement> recipes_url=driver.findElements(By.className("rcc_recipename"));
				int no_of_cards=recipes_url.size();
				System.out.println("No of urls : "+no_of_cards);
				ArrayList<String> links=new ArrayList<>(30);
				for(WebElement url: recipes_url) 
				 {
					String recipesLink=url.findElement(By.tagName("a")).getAttribute("href");
					links.add(recipesLink);
			     }
					
					
					for(Object eachRecipe:links)
					  {		
						try {
							Document doc=Jsoup.connect((String)eachRecipe).timeout(1000*100).get();
							// fetching Ingredients 
							Elements ingredientsEle = doc.selectXpath("//div[@id='rcpinglist']/div//a");							
							String ingredientsValue = ingredientsEle.text();
							System.out.println("Ingredients : "+ingredientsValue);
							if(ingredientsEle.size()==0) {
								continue;
							}						
							
							//fetching recipeURL
							String stringurl=eachRecipe.toString();
							System.out.println("Recipe URL : "+stringurl);
							
							//Fetching Recipe ID
							String id=stringurl.substring(stringurl.lastIndexOf("-")+1);
							String recipe_id=id.substring(0,id.length()-1);
							System.out.println("Recipe_id: "+recipe_id);
							
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
								food_Category = "Vegetarian";
							

							
							// fetching Cuisine category
							String cuisineCategory = "";
							for(String cuisine : cuisineDataList) {		
							if(tags.contains(cuisine)) {
								cuisineCategory = cuisine;
									break;
									
								} else {
									cuisineCategory = "Indian";
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
							
							
								//Creating arraylist which contains all the details of the recipe
								 String[] recipe_details = new String[] {recipe_id, recipe_name,rec_Category,food_Category,ingredientsValue,
											prepTime,cookTime, tags, noOfServings, cuisineCategory, desc,method, nutritionValue,stringurl };
							
							
							//If recipe is valid then fetch all the recipe details
								 if(validRecipe)
									{
										System.out.println("------ Valid Recipes of LCHF after elimination --------");									
										 LCHFsheet.getRow(0).createCell(column).setCellValue(recipe_id);
						                 LCHFsheet.getRow(1).createCell(column).setCellValue(recipe_name);
						                 LCHFsheet.getRow(2).createCell(column).setCellValue(rec_Category);
						                 LCHFsheet.getRow(3).createCell(column).setCellValue(food_Category);
						                 LCHFsheet.getRow(4).createCell(column).setCellValue(ingredientsValue);
						                 LCHFsheet.getRow(5).createCell(column).setCellValue(prepTime);
						                 LCHFsheet.getRow(6).createCell(column).setCellValue(cookTime);
						                 LCHFsheet.getRow(7).createCell(column).setCellValue(tags);
						                 LCHFsheet.getRow(8).createCell(column).setCellValue(noOfServings);
						                 LCHFsheet.getRow(9).createCell(column).setCellValue(cuisineCategory);
						                 LCHFsheet.getRow(10).createCell(column).setCellValue(desc);
						                 LCHFsheet.getRow(11).createCell(column).setCellValue(method);
						                 LCHFsheet.getRow(12).createCell(column).setCellValue(nutritionValue);
						                 LCHFsheet.getRow(13).createCell(column).setCellValue(stringurl);
						                 column++;
									}
								 
								 boolean addValidRecipe=false;
								 if(validRecipe) 
									{
										//Retrieve data from Add arraylist using for loop, 
										for(String addItem: LFV_AddItemList) 
										{								
											//Then compare each value with Ingredients.
											if(ingredientsValue.contains(addItem))
												{
												System.out.println("Item valid: " +addItem);
												addValidRecipe = true;
												break;
												}	
											else
												{
												addValidRecipe = false;
											
												}
										}
									}//end of add item if statement
								 
								 if(addValidRecipe) {
									 System.out.println("------ Valid Recipes of LCHF Add Item list --------");									
									 LCHFAddsheet.getRow(0).createCell(AddColumn).setCellValue(recipe_id);
					                 LCHFAddsheet.getRow(1).createCell(AddColumn).setCellValue(recipe_name);
					                 LCHFAddsheet.getRow(2).createCell(AddColumn).setCellValue(rec_Category);
					                 LCHFAddsheet.getRow(3).createCell(AddColumn).setCellValue(food_Category);
					                 LCHFAddsheet.getRow(4).createCell(AddColumn).setCellValue(ingredientsValue);
					                 LCHFAddsheet.getRow(5).createCell(AddColumn).setCellValue(prepTime);
					                 LCHFAddsheet.getRow(6).createCell(AddColumn).setCellValue(cookTime);
					                 LCHFAddsheet.getRow(7).createCell(AddColumn).setCellValue(tags);
					                 LCHFAddsheet.getRow(8).createCell(AddColumn).setCellValue(noOfServings);
					                 LCHFAddsheet.getRow(9).createCell(AddColumn).setCellValue(cuisineCategory);
					                 LCHFAddsheet.getRow(10).createCell(AddColumn).setCellValue(desc);
					                 LCHFAddsheet.getRow(11).createCell(AddColumn).setCellValue(method);
					                 LCHFAddsheet.getRow(12).createCell(AddColumn).setCellValue(nutritionValue);
					                 LCHFAddsheet.getRow(13).createCell(AddColumn).setCellValue(stringurl);
					                 AddColumn++;
					                 
								 }
									 
									 
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();}
			    }//end of recipe for loop To click on each recipe
			
		}//end of pagination
		}//alphabet loop
		workbook.write(fos);
		fos.close();
		workbook.close();
	}//end of getRecipeInfo method
	
}//end of class

