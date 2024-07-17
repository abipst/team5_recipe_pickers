package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pages.Recipe;

public class ExcelUtility {

	public static void storeRecipeInfo(Recipe recipeInfo, int colNumber, int rowNumber) throws Exception {
		try {
			//String path = "./src/test/resources/Recipe-filters-ScrapperHackathon.xlsx";
			String path = "./src/test/resources/Recipe_Scrapping_Data.xlsx";
			//File src = new File("./src/test/resources/Recipe-filters-ScrapperHackathon.xlsx");

			FileInputStream fs = new FileInputStream(path);
			Workbook wb = new XSSFWorkbook(fs);
			Sheet sheet = wb.getSheetAt(0);
			
			int index = wb.getSheetIndex("LFV");
			 if (index != -1)
		        {
			        System.out.println("Sheet found");
			         //sheet = workbook.getSheetAt(index);
	
					Row row = null;
			        Cell cell = null;
			        
			        //Recipe ID
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			          
			        cell.setCellValue(recipeInfo.getRecipe_id());
	
			        //Recipe Name
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getRecipe_name());
			 
			      //Recipe Category
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue("Category Test");
			        
			      //Food Category
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue("Test");
			        
			      //Recipe Ingredients
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getIngredients());
			        
			      //Preparation Time
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getPrep_time());
			        
			      //Cooking Time
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getCook_time());
			        
			      //Tag
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getTags());
			        
			      //No of servings
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getServings());
			        
			      //Cuisine category
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getCuisine_category());
			       
			       //Recipe Description
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getRecipe_Description());
			        
			        //Preparation method
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getPreparation_method());
			        
			        //Nutrient values
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getNutrient_values());
			        
			        //Recipe URL
			        rowNumber++;
			        row = sheet.getRow(rowNumber);
			        if(row == null)
			            row = sheet.createRow(rowNumber);
			 
			        cell = row.getCell(colNumber);
			        if(cell == null)
			            cell = row.createCell(colNumber);
			        cell.setCellValue(recipeInfo.getUrl());
			        
			        System.out.println("Filestream ended");		        
			  }

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
		
	}
}
