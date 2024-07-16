package utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pages.Recipe;

public class ExcelUtility {

	public static void storeRecipeInfo(Recipe recipeInfo, int colNumber, int rowNumber) throws Exception {
		
		File src = new File("./src/test/resources/Recipe-filters-ScrapperHackathon.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(src);
		XSSFSheet sheet;
		int index = workbook.getSheetIndex("Recipe-Data (Expected output fo");
        if (index != -1)
        {
        	System.out.println("Sheet found");
         sheet = workbook.getSheetAt(index);

		
		XSSFRow row = null;
        XSSFCell cell = null;
        
        //Recipe ID
        row = sheet.getRow(rowNumber);
        if(row == null)
            row = sheet.createRow(rowNumber);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
          
        cell.setCellValue(recipeInfo.getRecipe_id());
        
        /*//Recipe Name
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getRecipe_name());
 
      //Recipe Category
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue("Category Test");
        
      //Food Category
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue("Test");
        
      //Recipe Ingredients
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getIngredients());
        
      //Preparation Time
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getPrep_time());
        
      //Cooking Time
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getCook_time());
        
      //Tag
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getTags());
        
      //No of servings
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getServings());
        
      //Cuisine category
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getCuisine_category());
       
       //Recipe Description
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getRecipe_Description());
        
        //Preparation method
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getPreparation_method());
        
        //Nutrient values
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getNutrient_values());
        
        //Recipe URL
        row = sheet.getRow(rowNumber++);
        if(row == null)
            row = sheet.createRow(rowNumber++);
 
        cell = row.getCell(colNumber);
        if(cell == null)
            cell = row.createCell(colNumber);
        cell.setCellValue(recipeInfo.getUrl());
        */
        System.out.println("Filestream");
        FileOutputStream fos = new FileOutputStream(src);
        workbook.write(fos);
        fos.close();
        workbook.close();
	  }
	}
}
