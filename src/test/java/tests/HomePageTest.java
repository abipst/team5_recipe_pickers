package tests;

import java.io.IOException;

import org.pageobjects.Recipes_PtoT;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import pages.HomePage;

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

	@Test(priority = 1)
	public void recipes_PtoT_Test() throws InterruptedException, IOException {

		Recipes_PtoT recipe = new Recipes_PtoT(getDriver());

		recipe.read_EliminationList_Excel();

		recipe.read_CuisineCategoryData_Excel();

		recipe.click_AtoZ_recipes();

		recipe.getRecipeInfo();
	}

}
