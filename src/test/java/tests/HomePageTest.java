package tests;

import java.io.IOException;

import org.pageobjects.Recipes_PtoT;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import recipe_scrapping.Recipes_AtoE;
import recipe_scrapping.Recipes_FtoJ;
import recipe_scrapping.Recipes_KtoO;
import recipe_scrapping.Recipes_UtoZ;

public class HomePageTest extends TestBase {

	//HomePage homePage;

	public HomePageTest() {
		super();

	}

	@BeforeClass
	public void setup() {

		TestBase.initialization();
	}


	@Test(priority=1)
	public void recipes_AtoE_Test() throws InterruptedException, IOException {
		
		Recipes_AtoE recipe = new Recipes_AtoE(TestBase.getDriver());
		
		recipe.read_EliminationList_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
	}

	@Test(priority=2)
	public void recipes_FtoJ_Test() throws InterruptedException, IOException {
		
		Recipes_FtoJ recipe = new Recipes_FtoJ(getDriver());
		
		recipe.read_EliminationList_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
	}
	
	@Test(priority=3)
	public void recipes_KtoO_Test() throws InterruptedException, IOException {
		
		Recipes_KtoO recipe = new Recipes_KtoO(getDriver());
		
		recipe.read_EliminationList_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
	}
	
	@Test(priority=4)
	public void recipes_PtoT_Test() throws InterruptedException, IOException {
		
		Recipes_PtoT recipe = new Recipes_PtoT(getDriver());
		
		recipe.read_EliminationList_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
	}
	
	@Test(priority=5)
	public void recipes_UtoZ_Test() throws InterruptedException, IOException {
		
		Recipes_UtoZ recipe = new Recipes_UtoZ(getDriver());
		
		recipe.read_EliminationList_Excel();
		
		recipe.read_CuisineCategoryData_Excel();
		
		recipe.click_AtoZ_recipes();
		
		recipe.getRecipeInfo();
	}
	
	@AfterClass
	public void teardown() {

		getDriver().quit();
	}


}
