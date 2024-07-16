package tests;

import org.pageobjects.RecipeAtoE;
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
	public void RecipeAtoETest() throws Exception {
		//RecipeAtoE recipe = new RecipeAtoE(TestBase.getDriver());
		RecipeKtoO recipe = new RecipeKtoO(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}

		/*
	
	@Test(priority=2)
	public void hypothyroidAddOnRec() throws InterruptedException, IOException {
		HypothyroidismAddOnRec scrappingHypoThyroidAddOnRec = new HypothyroidismAddOnRec(TestBase.getDriver());
		scrappingHypoThyroidAddOnRec.readExcel();
		scrappingHypoThyroidAddOnRec.clickRecipesMenu();
		scrappingHypoThyroidAddOnRec.gettingRecipes();
	}

	@Test(priority=3)
	public void hypothyroidAftrElimnateAllrgies() throws InterruptedException, IOException {
		HypothyroidismAfterElimnatngAllergies HypothyroidNoAllergis = new HypothyroidismAfterElimnatngAllergies(
				TestBase.getDriver());
		HypothyroidNoAllergis.readExcel();
		HypothyroidNoAllergis.clickRecipesMenu();
		HypothyroidNoAllergis.gettingRecipes();
	}

	@Test(priority = 4)
	public void diabeticRecipesTest() throws InterruptedException, IOException  {
		DiabeticRecipes diabeticRepObj=new DiabeticRecipes(TestBase.getDriver());
		diabeticRepObj.readExcel();
		diabeticRepObj.clickRecipesMenu();
		diabeticRepObj.getDiabeticRecipes();
	}

	@Test(priority = 5)
	public void getDiabetesRecipesToAdd() throws InterruptedException, IOException  {
		DiabetesRecipesToAdd diabeticRepToAddObj=new DiabetesRecipesToAdd(TestBase.getDriver());
		diabeticRepToAddObj.readExcel();
		diabeticRepToAddObj.clickRecipesMenu();
		diabeticRepToAddObj.getDiabeticRecipes();
	}

	@Test(priority = 6)
	public void getDiabetesRecipesAfterAllergyCheck() throws InterruptedException, IOException  {
		DiabetesAfterElimnatngAllergies diabeticRepAllergyChk=new DiabetesAfterElimnatngAllergies(TestBase.getDriver());
		diabeticRepAllergyChk.readExcel();
		diabeticRepAllergyChk.clickRecipesMenu();
		diabeticRepAllergyChk.getDiabeticRecipes();
	}

	@Test (priority = 8)
	public void PCOSAfterEliminationReceipes() throws InterruptedException, IOException {
		PCOSRecAfterElimination pcosrecafterelimination = new PCOSRecAfterElimination(TestBase.getDriver());
		pcosrecafterelimination.readExcel();
		pcosrecafterelimination.clickRecipesMenu();
		pcosrecafterelimination.gettingPCOSRec();

	}

	@Test (priority = 9)
	public void PCOSAllergyFiltered() throws InterruptedException, IOException {
		PcosRecipesAfterEliminatngAllergies pcosrecipesafterelimonatingallergies = new PcosRecipesAfterEliminatngAllergies(TestBase.getDriver());

		pcosrecipesafterelimonatingallergies.clickRecipesMenu();
		pcosrecipesafterelimonatingallergies.readExcel();
		pcosrecipesafterelimonatingallergies.gettingRecipes();

	}

	@Test(priority = 10)
	public void validateUserLandingHomePageTest() throws InterruptedException {

		HighBloodPressure highBloodPressure = new HighBloodPressure();
		driver.get(prop.getProperty("HBP_url"));
		LoggerLoad.info("Executing test to validate Home Page title...");
		String HomePageTitle = driver.getTitle();

		LoggerLoad.info("Title of the home page is: " + HomePageTitle);
		Assert.assertEquals(HomePageTitle, "High Blood Pressure Recipes, Low Salt Recipes, Veg Low Sodium");

		highBloodPressure.readExcel();

		Assert.assertTrue(highBloodPressure.mouseoverRecipes());
		highBloodPressure.setHighBloodPressure();

		highBloodPressure.scrapHBPRecipes();
	}

*/

}
