package tests;

import org.pageobjects.RecipeKtoO;
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
	public void RecipeKtoOTest() throws Exception {
		RecipeKtoO recipe = new RecipeKtoO(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}

	

}
