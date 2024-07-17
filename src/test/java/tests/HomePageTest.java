package tests;

import java.io.IOException;
import org.pageobjects.RecipeAtoE;
import org.pageobjects.Recipes_FtoJ;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;
import pages.*;
import utils.LoggerLoad;

public class HomePageTest extends TestBase {

	HomePage homePage;

	public HomePageTest() {
		super();

	}

	@BeforeClass
	public void setup() {

		TestBase.initialization();
		homePage = new HomePage();
	}


	@Test(priority=1)
	public void RecipeAtoETest() throws InterruptedException, IOException {
		RecipeAtoE recipe = new RecipeAtoE(TestBase.getDriver());
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
	
	@AfterClass
	public void teardown() {

		getDriver().quit();
	}


}
